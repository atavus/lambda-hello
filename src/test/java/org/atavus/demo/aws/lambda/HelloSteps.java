package org.atavus.demo.aws.lambda;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Hello Steps is the cucumber step definition for the hello feature
 * 
 * @author David Johnston
 */
public class HelloSteps {

    private String urlPrefix;
    private URL url;
    private HttpURLConnection con;

    @Before
    public void setUp() {
        // we prefer to use background to set up state that the steps require
    }

    @Given("^A lambda function is installed$")
    public void a_lambda_function_is_installed() throws Throwable {
        urlPrefix = new String(Files.readAllBytes(Paths.get("api.url")));
    }

    @Given("^I have a java lambda function$")
    public void i_have_a_java_lambda_function() throws Throwable {
        url = new URL(urlPrefix + "/hello/java");
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
    }

    @Given("^I have a python lambda function$")
    public void i_have_a_python_lambda_function() throws Throwable {
        url = new URL(urlPrefix + "/hello/python");
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
    }

    @Given("^I have a node lambda function$")
    public void i_have_a_node_lambda_function() throws Throwable {
        url = new URL(urlPrefix + "/hello/node");
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
    }

    @When("^I send (.*)$")
    public void i_send(String who) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        Request request = new Request();
        request.setWho(who);
        String json = mapper.writeValueAsString(request);
        byte[] out = json.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        con.setFixedLengthStreamingMode(length);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.connect();
        try (OutputStream os = con.getOutputStream()) {
            os.write(out);
        }
    }

    @Then("^the result should be (.*)$")
    public void the_result_should_be(String result) throws Throwable {
        InputStream is = con.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        Response response = mapper.readValue(is, Response.class);
        assertEquals(response.getMessage(), result);
    }

}
