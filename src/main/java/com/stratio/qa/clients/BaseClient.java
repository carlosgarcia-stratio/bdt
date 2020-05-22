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
        ObjectMapper mapper = new ObjectMapper();
        T r = mapper.readValue(response.getResponseBody(), type);
        r.setHttpStatus(response.getStatusCode());
        r.setRawResponse(response.getResponseBody());
        return r;
    }

    public static <T> BaseResponseList<T> mapList(Response response, TypeReference<List<T>> typeReference) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<T> r = mapper.readValue(response.getResponseBody(), typeReference);
        BaseResponseList<T> rList = new BaseResponseList<>();
        rList.setList(r);
        rList.setHttpStatus(response.getStatusCode());
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

}
