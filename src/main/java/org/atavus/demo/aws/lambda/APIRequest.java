package org.atavus.demo.aws.lambda;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class APIRequest {
    private String resource;
    private String path;
    private String httpMethod;
    private Map<String, String> headers;
    private Map<String, List<String>> multiValueHeaders;
    private Map<String, String> queryStringParameters;
    private Map<String, List<String>> multiValueQueryStringParameters;
    private Map<String, String> pathParameters;
    private Map<String, String> stageVariables;
    private RequestContext requestContext;
    private String body;
    private boolean isBase64Encoded;

    public <T> T getBody(Class<T> cls) {
        return new Mapper().readValue(body, cls);
    }

    @Data
    public static class RequestContext {
        private String resourceId;
        private String resourcePath;
        private String httpMethod;
        private String extendedRequestId;
        private String requestTime;
        private String path;
        private String accountId;
        private String protocol;
        private String stage;
        private long requestTimeEpoch;
        private String requestId;
        private Identity identity;
        private String apiId;
    }

    @Data
    public static class Identity {
        private String cognitoIdentityPoolId;
        private String accountId;
        private String cognitoIdentityId;
        private String caller;
        private String sourceIp;
        private String accessKey;
        private String cognitoAuthenticationType;
        private String cognitoAuthenticationProvider;
        private String userArn;
        private String userAgent;
        private String user;
    }

}
