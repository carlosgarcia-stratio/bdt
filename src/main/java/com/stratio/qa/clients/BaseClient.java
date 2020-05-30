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

package com.stratio.qa.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Param;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import com.stratio.qa.models.BaseResponse;
import com.stratio.qa.models.BaseResponseList;
import com.stratio.qa.specs.CommonG;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseClient {

    protected AsyncHttpClient httpClient;

    protected List<Cookie> cookies;

    protected Logger log;

    protected ObjectMapper mapper = new ObjectMapper();

    protected BaseClient(CommonG common) {

        this.httpClient = common.getClient();
        this.cookies = common.getCookies();
        this.log = common.getLogger();
    }

    public BaseResponse map(Response response) throws Exception {
        BaseResponse r = new BaseResponse();
        r.setHttpStatus(response.getStatusCode());
        r.setRawResponse(response.getResponseBody());
        return r;
    }

    public <T extends BaseResponse> T map(Response response, Class<T> type) throws Exception {
        T r;

        try {
            r = mapper.readValue(response.getResponseBody(), type);
        } catch (Exception e) {
            log.warn(e.getMessage());
            log.warn("Error mapping response to " + type.getCanonicalName() + ". Setting empty...");
            r = type.newInstance();
        }

        r.setHttpStatus(response.getStatusCode());
        r.setRawResponse(response.getResponseBody());
        return r;
    }

    public <T> BaseResponseList<T> mapList(Response response, Class<T> type) throws Exception {
        List<T> r;
        BaseResponseList<T> rList = new BaseResponseList<>();

        try {
            r = mapper.readValue(response.getResponseBody(), new TypeReference<List<T>>() { });
        } catch (Exception e) {
            log.warn(e.getMessage());
            log.warn("Error mapping response to " + type.getCanonicalName() + ". Setting empty...");
            r = r = new ArrayList<>();
        }

        rList.setList(r);
        rList.setHttpStatus(response.getStatusCode());
        rList.setRawResponse(response.getResponseBody());
        return rList;
    }

    protected Response get(String endpoint) throws Exception {
        AsyncHttpClient.BoundRequestBuilder request = this.httpClient.prepareGet(endpoint);
        request = request.setCookies(cookies);
        Response response = request.execute().get();
        this.log.debug("GET to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response get(String endpoint, Map<String, String> queryParams) throws Exception {
        AsyncHttpClient.BoundRequestBuilder request = this.httpClient.prepareGet(endpoint);
        request = request.setCookies(cookies);
        List<Param> params = queryParams.entrySet().stream()
                .map(queryParam -> new Param(queryParam.getKey(), queryParam.getValue())).collect(Collectors.toList());
        request = request.setQueryParams(params);
        Response response = request.execute().get();
        this.log.debug("GET to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response delete(String endpoint) throws Exception {
        AsyncHttpClient.BoundRequestBuilder request = this.httpClient.prepareDelete(endpoint);
        request = request.setCookies(cookies);
        Response response = request.execute().get();
        this.log.debug("DELETE to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response put(String endpoint) throws Exception {
        AsyncHttpClient.BoundRequestBuilder request = this.httpClient.preparePut(endpoint);
        request = request.setCookies(cookies);
        Response response = request.execute().get();
        this.log.debug("PUT to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response post(String endpoint, String data) throws Exception {
        AsyncHttpClient.BoundRequestBuilder request = this.httpClient.preparePost(endpoint);
        request = request.setBody(data);
        request = request.setCookies(cookies);
        Response response = request.execute().get();
        this.log.debug("PUT to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response put(String endpoint, String data) throws Exception {
        AsyncHttpClient.BoundRequestBuilder request = this.httpClient.preparePut(endpoint);
        request = request.setBody(data);
        request = request.setCookies(cookies);
        Response response = request.execute().get();
        this.log.debug("PUT to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }
}
