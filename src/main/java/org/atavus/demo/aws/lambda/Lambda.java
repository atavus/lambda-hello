package org.atavus.demo.aws.lambda;

import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

/**
 * AWS Lambda Java Hello World
 */
public class Lambda implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream in, OutputStream out, Context context) {
        LambdaLogger log = context.getLogger();
        APIRequest apirequest = new Mapper().readValue(in, APIRequest.class);
        log.log("Received " + apirequest);
        try {
            Request request = apirequest.getBody(Request.class);
            Response response = new Response().withMessage("Hello " + request.getWho() + "!");
            APIResponse<Response> result = new APIResponse<Response>().withBody(response).withStatusCode(200);
            new Mapper().writeValue(out, result);
        } catch (Exception e) {
            APIResponse<Response> result = new APIResponse<Response>().withStatusCode(400).withException(e);
            new Mapper().writeValue(out, result);
        }
    }
}
