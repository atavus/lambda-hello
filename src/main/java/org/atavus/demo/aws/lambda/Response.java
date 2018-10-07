package org.atavus.demo.aws.lambda;

import lombok.Data;

@Data
public class Response {
    private String message;

    public Response withMessage(String message) {
        this.message = message;
        return this;
    }

}
