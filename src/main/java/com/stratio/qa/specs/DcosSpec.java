/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stratio.qa.specs;

import com.auth0.jwt.JWTSigner;
import com.jayway.jsonpath.JsonPath;
import com.ning.http.client.Response;
import com.stratio.qa.utils.GosecSSOUtils;
import com.stratio.qa.utils.RemoteSSHConnection;
import com.stratio.qa.utils.ThreadProperty;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.stratio.qa.assertions.Assertions.assertThat;

/**
 * Generic DC/OS Specs.
 */
public class DcosSpec extends BaseGSpec {

    String descriptorPath = "/stratio_volume/descriptor.json";

    String vaultResponsePath = "/stratio_volume/vault_response";

    /**
     * Generic constructor.
     *
     * @param spec object
     */
    public DcosSpec(CommonG spec) {
        this.commonspec = spec;
    }

    /**
     * Authenticate in a DCOS cluster
     *
     * @param remoteHost remote host
     * @param email      email for JWT singing
     * @param user       remote user
     * @param password   (required if pemFile null)
     * @throws Exception exception
     */
    @Given("^I authenticate to DCOS cluster '(.+?)' using email '(.+?)' with user '(.+?)' and password '(.+?)'( over SSH port '(.+?)')?$")
    public void authenticateDCOSpassword(String remoteHost, String email, String user, String password, String remotePort) throws Exception {
        authenticateDCOS(remoteHost, email, user, password, null, remotePort);
    }

    /**
     * Authenticate in a DCOS cluster
     *
     * @param remoteHost remote host
     * @param email      email for JWT singing
     * @param user       remote user
     * @param pemFile    (required if password null)
     * @throws Exception exception
     */
    @Given("^I authenticate to DCOS cluster '(.+?)' using email '(.+?)' with user '(.+?)' and pem file '(.+?)'( over SSH port '(.+?)')?$")
    public void authenticateDCOSpem(String remoteHost, String email, String user, String pemFile, String remotePort) throws Exception {
        authenticateDCOS(remoteHost, email, user, null, pemFile, remotePort);
    }

    /**
     * Authenticate in a DCOS cluster
     *
     * @param remoteHost remote host
     * @param email      email for JWT singing
     * @param user       remote user
     * @param password   (required if pemFile null)
     * @param pemFile    (required if password null)
     * @throws Exception exception
     */
    private void authenticateDCOS(String remoteHost, String email, String user, String password, String pemFile, String remotePort) throws Exception {
        commonspec.setRemoteSSHConnection(new RemoteSSHConnection(user, password, remoteHost, remotePort, pemFile));
        commonspec.getRemoteSSHConnection().runCommand("sudo cat /var/lib/dcos/dcos-oauth/auth-token-secret");
        String DCOSsecret = commonspec.getRemoteSSHConnection().getResult().trim();
        setDCOSCookie(DCOSsecret, email);
    }

    public void setDCOSCookie(String DCOSsecret, String email) throws Exception {
        final JWTSigner signer = new JWTSigner(DCOSsecret);
        final HashMap<String, Object> claims = new HashMap();
        claims.put("uid", email);
        final String jwt = signer.sign(claims);
        com.ning.http.client.cookie.Cookie cookie = new com.ning.http.client.cookie.Cookie("dcos-acs-auth-cookie", jwt, false, "", "", 99999, false, false);
        List<com.ning.http.client.cookie.Cookie> cookieList = new ArrayList<com.ning.http.client.cookie.Cookie>();
        cookieList.add(cookie);
        commonspec.setCookies(cookieList);
        ThreadProperty.set("dcosAuthCookie", jwt);
    }

    /**
     * Generate token to authenticate in gosec SSO
     *
     * @param ssoHost  current sso host
     * @param userName username
     * @param passWord password
     * @throws Exception exception
     */
    @Given("^I( do not)? set sso token using host '(.+?)' with user '(.+?)' and password '(.+?)'( and tenant '(.+?)')?$")
    public void setGoSecSSOCookie(String set, String ssoHost, String userName, String passWord, String tenant) throws Exception {
        if (set == null) {
            HashMap<String, String> ssoCookies = new GosecSSOUtils(ssoHost, userName, passWord, tenant).ssoTokenGenerator();
            String[] tokenList = {"user", "dcos-acs-auth-cookie"};
            List<com.ning.http.client.cookie.Cookie> cookiesAtributes = addSsoToken(ssoCookies, tokenList);

            commonspec.setCookies(cookiesAtributes);
        }
    }

    public List<com.ning.http.client.cookie.Cookie> addSsoToken(HashMap<String, String> ssoCookies, String[] tokenList) {
        List<com.ning.http.client.cookie.Cookie> cookiesAttributes = new ArrayList<>();

        for (String tokenKey : tokenList) {
            cookiesAttributes.add(new com.ning.http.client.cookie.Cookie(tokenKey, ssoCookies.get(tokenKey),
                    false, null,
                    null, 999999, false, false));
        }
        return cookiesAttributes;
    }

    /**
     * Checks if there are any unused nodes in the cluster and returns the IP of one of them.
     * REQUIRES A PREVIOUSLY-ESTABLISHED SSH CONNECTION TO DCOS-CLI TO WORK
     *
     * @param hosts:  list of IPs that will be investigated
     * @param envVar: environment variable name
     * @throws Exception
     */
    @Given("^I save the IP of an unused node in hosts '(.+?)' in the in environment variable '(.+?)'?$")
    public void getUnusedNode(String hosts, String envVar) throws Exception {
        Set<String> hostList = new HashSet(Arrays.asList(hosts.split(",")));

        //Get the list of currently used hosts
        commonspec.executeCommand("dcos task | awk '{print $2}'", 0, null);
        String results = commonspec.getRemoteSSHConnection().getResult();
        Set<String> usedHosts = new HashSet(Arrays.asList(results.replaceAll("\r", "").split("\n")));

        //We get the nodes not being used
        hostList.removeAll(usedHosts);

        if (hostList.size() == 0) {
            throw new IllegalStateException("No unused nodes in the cluster.");
        } else {
            //Pick the first available node
            ThreadProperty.set(envVar, hostList.iterator().next());
        }
    }


    /**
     * Check if all task of a service are correctly distributed in all datacenters of the cluster
     *
     * @param serviceList all task deployed in the cluster separated by a semicolumn.
     * @throws Exception
     */
    @Given("^services '(.*?)' are splitted correctly in datacenters$")
    public void checkServicesDistributionMultiDataCenter(String serviceList) throws Exception {
        commonspec.executeCommand("dcos node --json >> aux.txt", 0, null);
        commonspec.executeCommand("cat aux.txt", 0, null);
        checkDataCentersDistribution(serviceList.split(","), obtainsDataCenters(commonspec.getRemoteSSHConnection().getResult()).split(";"));
        commonspec.executeCommand("rm -rf aux.txt", 0, null);
    }

    /**
     * Check if all task of a service are correctly distributed in all datacenters of the cluster
     *
     * @param serviceList    all task deployed in the cluster separated by a semicolumn.
     * @param dataCentersIps all ips of the datacenters to be checked
     *                       Example: ip_1_dc1, ip_2_dc1;ip_3_dc2,ip_4_dc2
     * @throws Exception
     */
    @Given("^services '(.+?)' are splitted correctly in datacenters '(.+?)'$")
    public void checkServicesDistributionMultiDataCenterPram(String serviceList, String dataCentersIps) throws Exception {
        checkDataCentersDistribution(serviceList.split(","), dataCentersIps.split(";"));
    }

    public void checkDataCentersDistribution(String[] serviceListArray, String[] dataCentersIpsArray) throws Exception {
        int[] results = new int[dataCentersIpsArray.length];
        int div = serviceListArray.length / dataCentersIpsArray.length;
        int resto = serviceListArray.length % dataCentersIpsArray.length;

        for (int i = 0; i < serviceListArray.length; i++) {
            commonspec.executeCommand("dcos task | grep " + serviceListArray[i] + " | awk '{print $2}'", 0, null);
            String service_ip = commonspec.getRemoteSSHConnection().getResult();
            for (int x = 0; x < dataCentersIpsArray.length; x++) {
                if (dataCentersIpsArray[x].toLowerCase().contains(service_ip.toLowerCase())) {
                    results[x] = results[x] + 1;
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < results.length; i++) {
            if (resto > 0) {
                assertThat(results[i]).as("Services in datacenter should be: " + div + " or " + (div + 1)).isBetween(div - 1, div + 2);
            } else {
                assertThat(results[i]).as("Services in datacenter should be: " + div + " and it is: " + results[i]).isEqualTo(div);
            }

            sum = sum + results[i];
        }

        assertThat(sum).as("There are less services: " + sum + " than expected: " + serviceListArray.length).isEqualTo(serviceListArray.length);
    }

    public String obtainsDataCenters(String jsonString) {
        Map<String, String> datacentersDistribution = new HashMap<String, String>();
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String ip = object.getString("hostname");
            String datacenter = ((JSONObject) object.get("attributes")).getString("dc");
            String existValue = datacentersDistribution.get(datacenter);
            if (existValue == null) {
                datacentersDistribution.put(datacenter, ip);
            } else {
                datacentersDistribution.put(datacenter, datacentersDistribution.get(datacenter) + "," + ip);
            }
        }
        String result = "";
        for (String ips : datacentersDistribution.keySet()) {
            String key = ips.toString();
            String value = datacentersDistribution.get(key).toString();
            result = result + ";" + value;
        }
        return result.substring(1, result.length());
    }

    /**
     * Get info about secrets according input parameter
     *
     * @param type        what type of info (cert, key, ca, principal or keytab)
     * @param path        path where get info
     * @param value       value inside path
     * @param token       vault value
     * @param isUnsecure  vault by http instead of https
     * @param host        gosec machine IP
     * @param sExitStatus command exit status
     * @param envVar:     environment variable name
     * @throws Exception exception     *
     */
    @Given("^I get '(.+?)' from path '(.+?)' for value '(.+?)' with token '(.+?)',( unsecure)? vault host '(.+?)'( with exit status '(\\d+?)')? and save the value in environment variable '(.+?)'$")
    public void getSecretInfo(String type, String path, String value, String token, String isUnsecure, String host, String sExitStatus, String envVar) throws Exception {
        Integer exitStatus = sExitStatus != null ? Integer.parseInt(sExitStatus) : null;
        if (exitStatus == null) {
            exitStatus = 0;
        }

        String httpProtocol;
        if (isUnsecure != null) {
            httpProtocol = "http://";
        } else {
            httpProtocol = "https://";
        }

        String command;
        switch (type) {
            case "crt":
                command = "curl -X GET -fskL --tlsv1.2 -H \"X-Vault-Token:" + token + "\" \"" + httpProtocol + host + ":8200/v1" + path + "\" | jq -r '.data.\"" + value + "_" + type + "\"' | sed 's/-----BEGIN CERTIFICATE-----/-----BEGIN CERTIFICATE-----#####/g' | sed 's/-----END CERTIFICATE-----/#####-----END CERTIFICATE-----/g' | sed 's/-----END CERTIFICATE----------BEGIN CERTIFICATE-----/-----END CERTIFICATE-----#####-----BEGIN CERTIFICATE-----/g' > " + value + ".pem";
                commonspec.runLocalCommand(command);
                commonspec.setCommandResult(commonspec.getCommandResult().replace("#####", "\n"));
                command = "ls $PWD/" + value + ".pem";
                commonspec.runLocalCommand(command);
                commonspec.runCommandLoggerAndEnvVar(exitStatus, envVar, Boolean.TRUE);
                break;
            case "key":
                command = "curl -X GET -fskL --tlsv1.2 -H \"X-Vault-Token:" + token + "\" \"" + httpProtocol + host + ":8200/v1" + path + "\" | jq -r '.data.\"" + value + "_" + type + "\"' | sed 's/-----BEGIN RSA PRIVATE KEY-----/-----BEGIN RSA PRIVATE KEY-----#####/g' | sed 's/-----END RSA PRIVATE KEY-----/#####-----END RSA PRIVATE KEY-----/g' > " + value + ".key";
                commonspec.runLocalCommand(command);
                commonspec.setCommandResult(commonspec.getCommandResult().replace("#####", "\n"));
                command = "ls $PWD/" + value + ".key";
                commonspec.runLocalCommand(command);
                commonspec.runCommandLoggerAndEnvVar(exitStatus, envVar, Boolean.TRUE);
                break;
            case "ca":
                command = "curl -X GET -fskL --tlsv1.2 -H \"X-Vault-Token:" + token + "\" \"" + httpProtocol + host + ":8200/v1" + path + "\" | jq -r '.data.\"" + value + "_crt\"' | sed 's/-----BEGIN CERTIFICATE-----/-----BEGIN CERTIFICATE-----#####/g' | sed 's/-----END CERTIFICATE-----/#####-----END CERTIFICATE-----/g' > " + value + ".crt";
                commonspec.runLocalCommand(command);
                commonspec.setCommandResult(commonspec.getCommandResult().replace("#####", "\n"));
                command = "ls $PWD/" + value + ".crt";
                commonspec.runLocalCommand(command);
                commonspec.runCommandLoggerAndEnvVar(exitStatus, envVar, Boolean.TRUE);
                break;
            case "keytab":
                command = "curl -X GET -fskL --tlsv1.2 -H \"X-Vault-Token:" + token + "\" \"" + httpProtocol + host + ":8200/v1" + path + "\" | jq -r '.data.\"" + value + "_" + type + "\"' | base64 -d > " + value + ".keytab";
                commonspec.runLocalCommand(command);
                command = "ls $PWD/" + value + ".keytab";
                commonspec.runLocalCommand(command);
                commonspec.runCommandLoggerAndEnvVar(exitStatus, envVar, Boolean.TRUE);
                break;
            case "principal":
                command = "curl -X GET -fskL --tlsv1.2 -H \"X-Vault-Token:" + token + "\" \"" + httpProtocol + host + ":8200/v1" + path + "\" | jq -r '.data.\"" + value + "_" + type + "\"'";
                commonspec.runLocalCommand(command);
                commonspec.runCommandLoggerAndEnvVar(exitStatus, envVar, Boolean.TRUE);
                break;
            default:
                break;
        }
    }

    /**
     * Convert jsonSchema to json
     *
     * @param jsonSchema jsonSchema to be converted to json
     * @param envVar     environment variable where to store json
     * @throws Exception exception     *
     */
    @Given("^I convert jsonSchema '(.+?)' to json and save it in variable '(.+?)'")
    public void convertJSONSchemaToJSON(String jsonSchema, String envVar) throws Exception {
        String json = commonspec.parseJSONSchema(new JSONObject(jsonSchema)).toString();
        ThreadProperty.set(envVar, json);
    }

    /**
     * Check if json is validated against a schema
     *
     * @param json   json to be validated against schema
     * @param schema schema to be validated against
     * @throws Exception exception     *
     */
    @Given("^json (.+?) matches schema (.+?)$")
    public void jsonMatchesSchema(String json, String schema) throws Exception {
        JSONObject jsonschema = new JSONObject(schema);
        JSONObject jsondeploy = new JSONObject(json);

        commonspec.matchJsonToSchema(jsonschema, jsondeploy);
    }

    /**
     * Get service status
     *
     * @param service name of the service to be checked
     * @param cluster URI of the cluster
     * @param envVar  environment variable where to store result
     * @throws Exception exception     *
     */
    @Given("^I get service '(.+?)' status in cluster '(.+?)' and save it in variable '(.+?)'")
    public void getServiceStatus(String service, String cluster, String envVar) throws Exception {
        String status = commonspec.retrieveServiceStatus(service, cluster);

        ThreadProperty.set(envVar, status);
    }

    /**
     * Get service health status
     *
     * @param service name of the service to be checked
     * @param cluster URI of the cluster
     * @param envVar  environment variable where to store result
     * @throws Exception exception     *
     */
    @Given("^I get service '(.+?)' health status in cluster '(.+?)' and save it in variable '(.+?)'")
    public void getServiceHealthStatus(String service, String cluster, String envVar) throws Exception {
        String health = commonspec.retrieveHealthServiceStatus(service, cluster);

        ThreadProperty.set(envVar, health);
    }

    /**
     * Destroy specified service
     *
     * @param service name of the service to be destroyed
     * @param cluster URI of the cluster
     * @throws Exception exception     *
     */
    @Given("^I destroy service '(.+?)' in cluster '(.+?)'")
    public void destroyService(String service, String cluster) throws Exception {
        String endPoint = "/service/deploy-api/deploy/uninstall?app=" + service;
        Future response;

        this.commonspec.setRestProtocol("https://");
        this.commonspec.setRestHost(cluster);
        this.commonspec.setRestPort(":443");

        response = this.commonspec.generateRequest("DELETE", true, null, null, endPoint, null, "json");

        this.commonspec.setResponse("DELETE", (Response) response.get());
        assertThat(this.commonspec.getResponse().getStatusCode()).as("It hasn't been possible to destroy service: " + service).isIn(Arrays.asList(200, 202));
    }

    /**
     * Check if resources are released after uninstall and framework doesn't appear as inactive on mesos
     *
     * @param service service
     * @throws Exception exception
     */
    @When("^All resources from service '(.+?)' have been freed$")
    public void checkResources(String service) throws Exception {
        Future<Response> response = commonspec.generateRequest("GET", true, null, null, "/mesos/state-summary", null, null);

        String json = "[" + response.get().getResponseBody() + "]";
        String parsedElement = "$..frameworks[?(@.active==false)].name";
        String value = commonspec.getJSONPathString(json, parsedElement, null);

        Assertions.assertThat(value).as("Inactive services").doesNotContain(service);
    }

    /**
     * A PUT request over the body value.
     *
     * @param key
     * @param value
     * @param service
     * @throws Exception
     */
    @Then("^I add a new DCOS label with key '(.+?)' and value '(.+?)' to the service '(.+?)'?$")
    public void sendAppendRequest(String key, String value, String service) throws Exception {
        String[] serviceArray = service.split("/");
        String serviceName = serviceArray[serviceArray.length - 1];

        commonspec.runCommandAndGetResult("touch /dcos/" + serviceName + ".json && dcos marathon app show " + service + " > /dcos/" + serviceName + ".json");
        commonspec.runCommandAndGetResult("cat /dcos/" + serviceName + ".json");

        String configFile = commonspec.getRemoteSSHConnection().getResult();
        String myValue = commonspec.getJSONPathString(configFile, ".labels", "0");
        String myJson = commonspec.updateMarathonJson(commonspec.removeJSONPathElement(configFile, "$.labels"));

        String newValue = myValue.replaceFirst("\\{", "{\"" + key + "\": \"" + value + "\", ");
        newValue = "\"labels\":" + newValue;
        String myFinalJson = myJson.replaceFirst("\\{", "{" + newValue.replace("\\n", "\\\\n") + ",");
        if (myFinalJson.contains("uris")) {
            String test = myFinalJson.replaceAll("\"uris\"", "\"none\"");
            commonspec.runCommandAndGetResult("echo '" + test + "' > /dcos/final" + serviceName + ".json");
        } else {
            commonspec.runCommandAndGetResult("echo '" + myFinalJson + "' > /dcos/final" + serviceName + ".json");
        }
        commonspec.runCommandAndGetResult("dcos marathon app update " + service + " < /dcos/final" + serviceName + ".json");

        commonspec.setCommandExitStatus(commonspec.getRemoteSSHConnection().getExitStatus());
    }

    /**
     * Set a environment variable in marathon and deploy again.
     *
     * @param key
     * @param value
     * @param service
     * @throws Exception
     */
    @Then("^I modify marathon environment variable '(.+?)' with value '(.+?)' for service '(.+?)'?$")
    public void setMarathonProperty(String key, String value, String service) throws Exception {
        commonspec.runCommandAndGetResult("touch " + service + "-env.json && dcos marathon app show " + service + " > /dcos/" + service + "-env.json");
        commonspec.runCommandAndGetResult("cat /dcos/" + service + "-env.json");

        String configFile = commonspec.getRemoteSSHConnection().getResult();
        String myJson1 = commonspec.replaceJSONPathElement(configFile, key, value);
        String myJson4 = commonspec.updateMarathonJson(myJson1);
        String myJson = myJson4.replaceAll("\"uris\"", "\"none\"");

        commonspec.runCommandAndGetResult("echo '" + myJson + "' > /dcos/final" + service + "-env.json");
        commonspec.runCommandAndGetResult("dcos marathon app update " + service + " < /dcos/final" + service + "-env.json");
        commonspec.setCommandExitStatus(commonspec.getRemoteSSHConnection().getExitStatus());
    }

    /**
     * Check service status has value specified
     *
     * @param service name of the service to be checked
     * @param cluster URI of the cluster
     * @param status  status expected
     * @throws Exception exception     *
     */
    @Then("^service '(.+?)' status in cluster '(.+?)' is '(suspended|running|deploying)'( in less than '(\\d+)')?( seconds checking every '(\\d+)' seconds)?")
    public void serviceStatusCheck(String service, String cluster, String status, String sTotalWait, String sInterval) throws Exception {
        Integer totalWait = sTotalWait != null ? Integer.parseInt(sTotalWait) : null;
        Integer interval = sInterval != null ? Integer.parseInt(sInterval) : null;
        String response;
        Integer i = 0;
        boolean matched;

        response = commonspec.retrieveServiceStatus(service, cluster);

        if (totalWait != null && interval != null) {
            matched = status.matches(response);
            while (!matched && i < totalWait) {
                this.commonspec.getLogger().info("Service status not found yet after " + i + " seconds");
                i = i + interval;
                response = commonspec.retrieveServiceStatus(service, cluster);
                matched = status.matches(response);
            }
        }

        assertThat(status).as("Expected status: " + status + " doesn't match obtained one: " + response).matches(response);
    }

    /**
     * Check service health status has value specified
     *
     * @param service name of the service to be checked
     * @param cluster URI of the cluster
     * @param status  health status expected
     * @throws Exception exception     *
     */
    @Then("^service '(.+?)' health status in cluster '(.+?)' is '(unhealthy|healthy|unknown)'( in less than '(\\d+)')?( seconds checking every '(\\d+)' seconds)?")
    public void serviceHealthStatusCheck(String service, String cluster, String status, String sTotalWait, String sInterval) throws Exception {
        Integer totalWait = sTotalWait != null ? Integer.parseInt(sTotalWait) : null;
        Integer interval = sInterval != null ? Integer.parseInt(sInterval) : null;
        String response;
        Integer i = 0;
        boolean matched;

        response = commonspec.retrieveHealthServiceStatus(service, cluster);

        if (totalWait != null && interval != null) {
            matched = status.matches(response);
            while (!matched && i < totalWait) {
                this.commonspec.getLogger().info("Service health status not found yet after " + i + " seconds");
                i = i + interval;
                response = commonspec.retrieveHealthServiceStatus(service, cluster);
                matched = status.matches(response);
            }
        }

        assertThat(status).as("Expected status: " + status + " doesn't match obtained one: " + response).matches(response);
    }

    @Then("^I obtain metabase id for user '(.+?)' and password '(.+?)' in endpoint '(.+?)' and save in context cookies$")
    public void saveMetabaseCookie(String user, String password, String url) throws Exception {
        String command = "curl -X POST -k -H \"Content-Type: application/json\" -d '{\"username\": \"" + user + "\", \"password\": \"" + password + "\"}' " + url;
        commonspec.runLocalCommand(command);
        commonspec.runCommandLoggerAndEnvVar(0, null, Boolean.TRUE);

        Assertions.assertThat(commonspec.getCommandExitStatus()).isEqualTo(0);
        String result = JsonPath.parse(commonspec.getCommandResult().trim()).read("$.id");

        com.ning.http.client.cookie.Cookie cookie = new com.ning.http.client.cookie.Cookie("metabase.SESSION_ID", result, false, "", "", 99999L, false, false);
        ArrayList cookieList = new ArrayList();
        cookieList.add(cookie);
        this.commonspec.setCookies(cookieList);
    }


    /**
     * Check if a role of a service complies the established constraints
     *
     * @param role        name of role of a service
     * @param service     name of service of exhibitor
     * @param instance    name of instance of a service
     * @param constraints all stablished contraints separated by a semicolumn.
     *                    Example: constraint1,constraint2,...
     * @throws Exception
     */
    @Then("^The role '(.+?)' of the service '(.+?)' with instance '(.+?)' complies the constraints '(.+?)'$")
    public void checkComponentConstraints(String role, String service, String instance, String constraints) throws Exception {
        checkComponentConstraint(role, service, instance, constraints.split(","));
    }

    public void checkComponentConstraint(String role, String service, String instance, String[] constraints) throws Exception {
        for (int i = 0; i < constraints.length; i++) {
            String[] elements = constraints[i].split(":");
            Assertions.assertThat(elements.length).overridingErrorMessage("Error while parsing constraints. The constraint's format is ATRIBUTE:CONSTRAINT:VALOR or ATRIBUTE:CONSTRAINT").isIn(2, 3);
            Assertions.assertThat(elements[1]).overridingErrorMessage("Error while parsing constraints. Constraints should be CLUSTER, UNIQUE, LIKE, UNLIKE, GROUP_BY, MAX_PER or IS").isIn("UNIQUE", "CLUSTER", "GROUP_BY", "LIKE", "UNLIKE", "MAX_PER", "IS");
            if (elements.length == 2) {
                Assertions.assertThat(elements[1]).overridingErrorMessage("Error while parsing constraints. The constraint's format " + elements[1] + " is ATRIBUTE:CONSTRAINT:VALOR").isIn("UNIQUE", "CLUSTER", "GROUP_BY");
                if (!elements[1].equals("GROUP_BY")) {
                    checkConstraint(role, service, instance, elements[0], elements[1], null);
                }
            } else {
                Assertions.assertThat(elements[1]).overridingErrorMessage("Error while parsing constraints. The constraint's format " + elements[1] + " is ATRIBUTE:CONSTRAINT").isNotEqualTo("UNIQUE");
                checkConstraint(role, service, instance, elements[0], elements[1], elements[2]);
            }
        }
    }

    public void checkConstraint(String role, String service, String instance, String tag, String constraint, String value) throws Exception {
        RestSpec restspec = new RestSpec(commonspec);
        restspec.sendRequestTimeout(100, 5, "GET", "/exhibitor/exhibitor/v1/explorer/node-data?key=%2Fdatastore%2F" + service + "%2F" + instance + "%2Fplan-v2-json&_=", null, "str");
        MiscSpec miscspec = new MiscSpec(commonspec);
        miscspec.saveElementEnvironment(null, "$.str", "exhibitor_answer");
        Assertions.assertThat(ThreadProperty.get("exhibitor_answer")).overridingErrorMessage("Error while parsing constraints. The instance " + instance + " of the service " + service + " isn't deployed").isNotEmpty();
        CommandExecutionSpec commandexecutionspec = new CommandExecutionSpec(commonspec);
        if (tag.equals("hostname")) {
            selectElements(role, service, "agent_hostname");
            String[] hostnames = ThreadProperty.get("elementsConstraint").split("\n");
            checkConstraintType(role, instance, tag, constraint, value, hostnames);
        } else {
            selectElements(role, service, "slaveid");
            String[] slavesid = ThreadProperty.get("elementsConstraint").split("\n");
            String[] valor = new String[slavesid.length];
            for (int i = 0; i < slavesid.length; i++) {
                restspec.sendRequestTimeout(100, 5, "GET", "/mesos/slaves?slave_id=" + slavesid[i], null, "slaves");
                miscspec.saveElementEnvironment(null, "$", "mesos_answer");
                commandexecutionspec.executeLocalCommand("echo '" + ThreadProperty.get("mesos_answer") + "' | jq '.slaves[0].attributes." + tag + "' | sed 's/^.\\|.$//g'", "0", "valortag");
                valor[i] = ThreadProperty.get("valortag");
            }
            checkConstraintType(role, instance, tag, constraint, value, valor);
        }
    }

    /**
     * @param role   name of role of a service
     * @param IP     Ip of the machine from which you want to save the nodes
     * @param envVar environment variable where store nodes
     * @throws Exception
     */
    @When("^I save nodes '(.+?)' that are in machine '(.+?)' in environment variable '(.+?)'$")
    public void saveNodes(String role, String IP, String envVar) throws Exception {
        selectElements(role, "pbd", "agent_hostname", IP, envVar);
    }

    /**
     * @param role        name of role of a service
     * @param envVar      environment variable where before you save nodes
     * @param envVar2     environment variable when you want to check slave nodes
     * @param sTimeout    Same RestSpec.sendRequest
     * @param sWait       Same RestSpec.sendRequest
     * @param requestType Same RestSpec.sendRequest
     * @param endPoint    Same RestSpec.sendRequest
     * @param status      Same RestSpec.sendRequest
     * @throws Exception
     */
    @Then("^I check status of nodes '(.+?)' using environment variable '(.+?)'(,'(.+?)')? in less than '(\\d+?)' seconds, checking each '(\\d+?)' seconds, I send a '(.+?)' request to '(.+?)' checking status '(.+?)' of nodes$")
    public void checkProxyNodesStatus(String role, String envVar, String envVar2, String sTimeout, String sWait, String requestType, String endPoint, String status) throws Exception {
        Integer timeout = Integer.parseInt(sTimeout);
        Integer wait = Integer.parseInt(sWait);
        String estadoNodo;

        RestSpec restspec = new RestSpec(commonspec);

        //datanodes
        selectElements(role, "pbd", "name");
        String[] dataNodes = ThreadProperty.get("elementsConstraint").split("\n");

        //Si tenemos algún DataNode caído, chequeamos los datanodeSlave de ese dataNode
        if (role.contains("datanode_slave") && envVar2 != null) {

            for (int i = 0; i < dataNodes.length; i++) {
                if (dataNodes[i].split("_")[1].contains(ThreadProperty.get(envVar2).split("_")[1])) {
                    estadoNodo = status;
                } else {
                    estadoNodo = "RUNNING";
                }
                restspec.sendRequestTimeout(timeout, wait, requestType, endPoint, null, dataNodes[i] + "\",\"role\":\"" + role + "\",\"status\":\"" + estadoNodo);
            }

        } else {
            for (int i = 0; i < dataNodes.length; i++) {
                if (dataNodes[i].contains(ThreadProperty.get(envVar)) && !ThreadProperty.get(envVar).isEmpty()) {
                    estadoNodo = status;
                } else {
                    estadoNodo = "RUNNING";
                }
                restspec.sendRequestTimeout(timeout, wait, requestType, endPoint, null, dataNodes[i] + "\",\"role\":\"" + role + "\",\"status\":\"" + estadoNodo);
            }
        }
    }

    public void selectElements(String role, String service, String element, String elementValue, String envValue) throws Exception {
        CommandExecutionSpec commandexecutionspec = new CommandExecutionSpec(commonspec);
        Assertions.assertThat(service).overridingErrorMessage("Error while parsing arguments. The service must be one of them: [community, pbd, zookeeper, ignite, kubernetes, etcd, arangodb]").isIn("community", "zookeeper", "pbd", "ignite", "kubernetes", "etcd", "arangodb");
        int pos = selectExhibitorRole(role, service);
        Assertions.assertThat(pos).overridingErrorMessage("Error while parsing arguments. The role " + role + " of the service " + service + " doesn't exist").isNotEqualTo(-1);
        commandexecutionspec.executeLocalCommand("echo '" + ThreadProperty.get("exhibitor_answer") + "' | jq '.phases[" + pos + "].\"000" + (pos + 1) + "\".steps[][] | select(.status | contains(\"RUNNING\")) | select(." + element + " | contains(\"" + elementValue + "\")).name' | sed 's/\"//g'", "0", envValue);
    }


    public void selectElements(String role, String service, String element) throws Exception {
        CommandExecutionSpec commandexecutionspec = new CommandExecutionSpec(commonspec);
        Assertions.assertThat(service).overridingErrorMessage("Error while parsing arguments. The service must be one of them: [community, pbd, zookeeper, ignite, kubernetes, etcd, arangodb]").isIn("community", "zookeeper", "pbd", "ignite", "kubernetes", "etcd", "arangodb");
        int pos = selectExhibitorRole(role, service);
        Assertions.assertThat(pos).overridingErrorMessage("Error while parsing arguments. The role " + role + " of the service " + service + " doesn't exist").isNotEqualTo(-1);
        commandexecutionspec.executeLocalCommand("echo '" + ThreadProperty.get("exhibitor_answer") + "' | jq '.phases[" + Integer.toString(pos) + "].\"000" + Integer.toString(pos + 1) + "\".steps[][] | select(.status | contains(\"RUNNING\"))." + element + "' | sed 's/\"//g'", "0", "elementsConstraint");
    }

    public void checkConstraintType(String role, String instance, String tag, String constraint, String value, String[] elements) throws Exception {
        Pattern p = value != null ? Pattern.compile(value) : null;
        Matcher m;
        switch (constraint) {
            case "UNIQUE":
                for (int i = 0; i < elements.length; i++) {
                    for (int j = i + 1; j < elements.length; j++) {
                        Assertions.assertThat(elements[i]).overridingErrorMessage("The role " + role + " of the instance " + instance + " doesn't complies the established constraint " + tag + ":" + constraint).isNotEqualTo(elements[j]);
                    }
                }
                break;
            case "CLUSTER":
                if (value == null) {
                    for (int i = 0; i < elements.length; i++) {
                        for (int j = i + 1; j < elements.length; j++) {
                            Assertions.assertThat(elements[i]).overridingErrorMessage("The role " + role + " of the instance " + instance + " doesn't complies the established constraint " + tag + ":" + constraint).isEqualTo(elements[j]);
                        }
                    }
                } else {
                    checkConstraintClusterValueIs(role, instance, tag, constraint, value, elements);
                }
                break;
            case "LIKE":
                for (int i = 0; i < elements.length; i++) {
                    m = p.matcher(elements[i]);
                    Assertions.assertThat(m.find()).overridingErrorMessage("The role " + role + " of the instance " + instance + " doesn't complies the established constraint " + tag + ":" + constraint + ":" + value).isEqualTo(true);
                }
                break;
            case "UNLIKE":
                for (int i = 0; i < elements.length; i++) {
                    m = p.matcher(elements[i]);
                    Assertions.assertThat(m.find()).overridingErrorMessage("The role " + role + " of the instance " + instance + " doesn't complies the established constraint " + tag + ":" + constraint + ":" + value).isEqualTo(false);
                }
                break;
            case "IS":
                checkConstraintClusterValueIs(role, instance, tag, constraint, value, elements);
                break;
            case "MAX_PER":
                Map<String, Integer> diferent = new HashMap<String, Integer>();
                int count;
                for (int i = 0; i < elements.length; i++) {
                    if (!diferent.containsKey(elements[i])) {
                        diferent.put(elements[i], 1);
                    } else {
                        count = diferent.get(elements[i]);
                        count = count + 1;
                        diferent.put(elements[i], count);
                    }
                }
                Iterator it = diferent.keySet().iterator();
                while (it.hasNext()) {
                    Assertions.assertThat(diferent.get(it.next())).overridingErrorMessage("The role " + role + " of the instance " + instance + " doesn't complies the established constraint " + tag + ":" + constraint + ":" + value).isLessThanOrEqualTo(Integer.parseInt(value));
                }
                break;
            case "GROUP_BY":
                ArrayList<String> dif = new ArrayList<>();
                dif.add(elements[0]);
                boolean ok;
                for (int i = 1; i < elements.length; i++) {
                    ok = false;
                    for (int j = 0; j < dif.size(); j++) {
                        if (elements[i].equals(dif.get(j))) {
                            ok = true;
                            j = dif.size();
                        }
                    }
                    if (!ok) {
                        dif.add(elements[i]);
                    }
                }
                Assertions.assertThat(dif.size()).overridingErrorMessage("The role " + role + " of the instance " + instance + " doesn't complies the established constraint " + tag + ":" + constraint + ":" + value).isLessThanOrEqualTo(Integer.parseInt(value));
                break;
            default:
                commonspec.getExceptions().add(new Exception("Error while parsing constraints. Constraints should be CLUSTER, UNIQUE, LIKE, UNLIKE, GROUP_BY, UNIQUE, LIKE, UNLIKE, GROUP_BY, MAX_PER or IS"));
        }
    }

    public void checkConstraintClusterValueIs(String role, String instance, String tag, String constraint, String value, String[] elements) throws Exception {
        for (int i = 0; i < elements.length; i++) {
            for (int j = i + 1; j < elements.length; j++) {
                Assertions.assertThat(elements[i]).overridingErrorMessage("The role " + role + " of the instance " + instance + " doesn't complies the established constraint " + tag + ":" + constraint + ":" + value).isEqualTo(elements[j]);
                Assertions.assertThat(elements[i]).overridingErrorMessage("The role " + role + " of the instance " + instance + " doesn't complies the established constraint " + tag + ":" + constraint + ":" + value).isEqualTo(value);
            }
        }
    }

    private int selectExhibitorRole(String role, String service) {
        switch (service) {
            case "community":
                switch (role) {
                    case "master":
                        return 0;
                    case "sync_slave":
                        return 1;
                    case "async_slave":
                        return 2;
                    case "agent":
                        return 3;
                    default:
                        return -1;
                }
            case "pbd":
                switch (role) {
                    case "gtm":
                        return 0;
                    case "gtm_slave":
                        return 1;
                    case "gtm_proxy":
                        return 2;
                    case "datanode":
                        return 3;
                    case "datanode_slave":
                        return 4;
                    case "coordinator":
                        return 5;
                    case "agent":
                        return 6;
                    default:
                        return -1;
                }
            case "zookeeper":
                switch (role) {
                    case "zkNode":
                        return 0;
                    default:
                        return -1;
                }
            case "ignite":
                switch (role) {
                    case "ignite":
                        return 0;
                    default:
                        return -1;
                }
            case "etcd":
                switch (role) {
                    case "etcd":
                        return 0;
                    default:
                        return -1;
                }
            case "kubernetes":
                switch (role) {
                    case "DEX":
                        return 0;
                    case "API_SERVER":
                        return 1;
                    case "CONTROLLER":
                        return 2;
                    case "SCHEDULER":
                        return 3;
                    case "CALICO_CONTROLLERS":
                        return 4;
                    case "PROXY":
                        return 5;
                    case "KUBELET":
                        return 6;
                    default:
                        return -1;
                }
            case "arangodb":
                switch (role) {
                    case "arango-agent":
                        return 0;
                    case "arango-server":
                        return 1;
                    case "arango-coordinator":
                        return 2;
                    default:
                        return -1;
                }
            default:
                return -2;
        }
    }

    /**
     * Obtains info from file passed as a parameter based on the jq expression passed as a parameter
     *
     * @param jqExpression    jq expression to retrieve required information
     * @param absolutPathFile absolut path to file to retrieve information from
     * @param envVar          environment variable where to store retrieved information
     * @throws Exception
     */
    @Given("^I obtain '(.+?)' from json file '(.+?)' and save it in environment variable '(.+?)'$")
    public void obtainInfoFromFile(String jqExpression, String absolutPathFile, String envVar) throws Exception {
        String prefix = "set -o pipefail && cat ";

        try {
            commonspec.getRemoteSSHConnection().runCommand("test -f " + absolutPathFile);
        } catch (Exception e) {
            commonspec.getLogger().debug("SSH connection not opened.");
            throw new Exception("SSH connection not opened.");
        }

        if (commonspec.getRemoteSSHConnection().getExitStatus() != 0) {
            commonspec.getLogger().debug("File passed as parameter: " + absolutPathFile + ", does not exist in remote system");
            throw new Exception("File: " + absolutPathFile + " does not exist in remote system.");
        }

        commonspec.getRemoteSSHConnection().runCommand(prefix + absolutPathFile + " | " + jqExpression);
        if (commonspec.getRemoteSSHConnection().getExitStatus() != 0) {
            commonspec.getLogger().debug("Problem with jq expression passed as parameter.");
            throw new Exception("Error obtaining info from json file: " + commonspec.getRemoteSSHConnection().getResult());
        }

        if (commonspec.getRemoteSSHConnection().getResult().equals("null")) {
            commonspec.getLogger().debug("jq expression passed as parameter returns null.");
            throw new Exception("Info obtained from json file with jq: " + jqExpression + ", is null.");
        }

        ThreadProperty.set(envVar, commonspec.getRemoteSSHConnection().getResult());
    }

    /**
     * Obtains required information from descriptor file
     *
     * @param info   information required from descriptor.json in bootstrap system
     * @param envVar environment variable where to store retrieved information
     * @throws Exception
     */
    @Given("^I obtain '(MASTERS|NODES|PRIV_NODES|PUBLIC_NODES|PUBLIC_NODE|GOSEC_NODES|ID|DNS_SEARCH|INTERNAL_DOMAIN|ARTIFACT_REPO|DOCKER_REGISTRY|EXTERNAL_DOCKER_REGISTRY|REALM|KRB_HOST|LDAP_HOST|VAULT_HOST|ADMIN_USER|TENANT|ACCESS_POINT|LDAP_URL|LDAP_PORT|LDAP_USER_DN|LDAP_GROUP_DN|LDAP_BASE|LDAP_ADMIN_GROUP)' from descriptor and save it in environment variable '(.+?)'$")
    public void obtainInfoFromDescriptor(String info, String envVar) throws Exception {
        String jqExpression = "";

        switch (info) {
            case "MASTERS":
                jqExpression = "jq -crM '.nodes[] | select(.role ?== \"master\") | .networking[0].ip' | paste -sd \",\" -";
                break;
            case "NODES":
                jqExpression = "jq -crM '.nodes[] | select(.role ?== \"agent\") | .networking[0].ip' | paste -sd \",\" -";
                break;
            case "PRIV_NODES":
                jqExpression = "jq -crM '.nodes[] | select((.role ?== \"agent\") and .public ?== false) | .networking[0].ip' | paste -sd \",\" -";
                break;
            case "PUBLIC_NODES":
                jqExpression = "jq -crM '.nodes[] | select((.role ?== \"agent\") and .public ?== true) | .networking[0].ip' | paste -sd \",\" -";
                break;
            case "PUBLIC_NODE":
                jqExpression = "jq -cM '.nodes[] | select((.role ?== \"agent\") and .public ?== true) | .networking[0].ip' | jq -crMs '.[0]'";
                break;
            case "GOSEC_NODES":
                jqExpression = "jq -crM '.nodes[] | select(.role ?== \"gosec\") | .networking[0].ip' | paste -sd \",\" -";
                break;
            case "ID":
                jqExpression = "jq -crM .id";
                break;
            case "DNS_SEARCH":
                jqExpression = "jq -crM .dnsSearch";
                break;
            case "INTERNAL_DOMAIN":
                jqExpression = "jq -crM .internalDomain";
                break;
            case "ARTIFACT_REPO":
                jqExpression = "jq -crM .artifactRepository";
                break;
            case "DOCKER_REGISTRY":
                jqExpression = "jq -crM .dockerRegistry";
                break;
            case "EXTERNAL_DOCKER_REGISTRY":
                jqExpression = "jq -crM .externalDockerRegistry";
                break;
            case "REALM":
                jqExpression = "jq -crM .security.kerberos.realm";
                break;
            case "KRB_HOST":
                jqExpression = "jq -crM .security.kerberos.kdcHost";
                break;
            case "LDAP_HOST":
                jqExpression = "jq -crM .security.ldap.url";
                break;
            case "VAULT_HOST":
                jqExpression = "jq -crM '.nodes[] | select((.role ?== \"gosec\") and .id ?== \"gosec1\") | .networking[0].ip'";
                break;
            case "ADMIN_USER":
                jqExpression = "jq -crM .security.ldap.adminUserUuid";
                break;
            case "TENANT":
                jqExpression = "jq -crM .security.tenantSSODefault";
                break;
            case "ACCESS_POINT":
                jqExpression = "jq -crM .proxyAccessPointURL | sed 's/https:\\/\\///g'";
                break;
            case "LDAP_URL":
                jqExpression = "jq -crM .security.ldap.url";
                break;
            case "LDAP_PORT":
                jqExpression = "jq -crM .security.ldap.port";
                break;
            case "LDAP_USER_DN":
                jqExpression = "jq -crM .security.ldap.userDn";
                break;
            case "LDAP_GROUP_DN":
                jqExpression = "jq -crM .security.ldap.groupDN";
                break;
            case "LDAP_BASE":
                jqExpression = "jq -crM .security.ldap.ldapBase";
                break;
            case "LDAP_ADMIN_GROUP":
                jqExpression = "jq -crM .security.ldap.adminrouterAuthorizedGroup";
                break;
            default:
                break;
        }

        obtainInfoFromFile(jqExpression, this.descriptorPath, envVar);

    }

    /**
     * Obtains basic information for tests from descriptor file:
     * EOS_CLUSTER_ID, EOS_DNS_SEARCH, EOS_INTERNAL_DOMAIN, DCOS_USER, DCOS_TENANT, VAULT_TOKEN
     * LDAP_URL, LDAP_PORT, LDAP_USER_DN, LDAP_GROUP_DN, LDAP_BASE, LDAP_ADMIN_GROUP
     *
     * @throws Exception
     */
    @Given("^I obtain basic information from bootstrap$")
    public void obtainBasicInfoFromDescriptor() throws Exception {
        String varClusterID = "EOS_CLUSTER_ID";
        String varClusterDomain = "EOS_DNS_SEARCH";
        String varInternalDomain = "EOS_INTERNAL_DOMAIN";
        String varAdminUser = "DCOS_USER";
//        String varTenant = "DCOS_TENANT";
        String varVaultHost = "EOS_VAULT_HOST";
        String varVaultToken = "VAULT_TOKEN";
        String varPublicNode = "PUBLIC_NODE";
        String varAccessPoint = "EOS_ACCESS_POINT";
        String vaultTokenJQ = "jq -cMr .root_token";

        obtainInfoFromDescriptor("ID", varClusterID);
        obtainInfoFromDescriptor("DNS_SEARCH", varClusterDomain);
        obtainInfoFromDescriptor("INTERNAL_DOMAIN", varInternalDomain);
        obtainInfoFromDescriptor("ADMIN_USER", varAdminUser);
//        obtainInfoFromDescriptor("TENANT", varTenant);
        obtainInfoFromDescriptor("VAULT_HOST", varVaultHost);
        obtainInfoFromFile(vaultTokenJQ, this.vaultResponsePath, varVaultToken);
        commonspec.getRemoteSSHConnection().runCommand("set -o pipefail && cat " + this.descriptorPath + " | " + "jq -crM '.nodes[] | select((.role ?== \"agent\") and .public ?== true)'" + " | " + "wc -l");
        if (!(commonspec.getRemoteSSHConnection().getResult().equals("0"))) {
            obtainInfoFromDescriptor("PUBLIC_NODE", varPublicNode);
        }
        obtainInfoFromDescriptor("ACCESS_POINT", varAccessPoint);
    }

    /**
     * Obtains basic LDAP information for tests from descriptor file:
     * LDAP_URL, LDAP_PORT, LDAP_USER_DN, LDAP_GROUP_DN, LDAP_BASE, LDAP_ADMIN_GROUP
     *
     * @throws Exception
     */
    @Given("^I obtain basic LDAP information from bootstrap$")
    public void obtainBasicLDAPInfoFromDescriptor() throws Exception {
        String varLDAPurl = "LDAP_URL";
        String varLDAPport = "LDAP_PORT";
        String varLDAPuserDn = "LDAP_USER_DN";
        String varLDAPgroupDn = "LDAP_GROUP_DN";
        String varLDAPbase = "LDAP_BASE";
        String varLDAPadminGroup = "LDAP_ADMIN_GROUP";

        obtainInfoFromDescriptor("LDAP_URL", varLDAPurl);
        obtainInfoFromDescriptor("LDAP_PORT", varLDAPport);
        obtainInfoFromDescriptor("LDAP_USER_DN", varLDAPuserDn);
        obtainInfoFromDescriptor("LDAP_GROUP_DN", varLDAPgroupDn);
        obtainInfoFromDescriptor("LDAP_BASE", varLDAPbase);
        obtainInfoFromDescriptor("LDAP_ADMIN_GROUP", varLDAPadminGroup);
    }
}