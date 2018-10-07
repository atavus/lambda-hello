package org.atavus.demo.aws.lambda;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class TestMapper {

    @Test
    public void testMapper() {

        APIRequest apirequest = new APIRequest();
        {
            Request request = new Request();
            request.setWho("joe");
            apirequest.setBody(new Mapper().writeValueAsString(request));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Request request = apirequest.getBody(Request.class);
        Response response = new Response().withMessage("Hello " + request.getWho() + "!");
        APIResponse<Response> result = new APIResponse<Response>().withBody(response).withStatusCode(200);
        new Mapper().writeValue(out, result);
        assertEquals("{\"statusCode\":200,\"body\":\"{\\\"message\\\":\\\"Hello joe!\\\"}\",\"base64Encoded\":false}",
                        new String(out.toByteArray()));

    }

}
