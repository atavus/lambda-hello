package org.atavus.demo.aws.lambda;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * RunHelloTest is simply a facade to enable the running of the cucumber tests for the hello feature with jUnit
 * 
 * @author David Johnston
 */
@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, format = { "pretty", "html:target/cucumber" }, features = { "classpath:cucumber/hello.feature" })
public class ITRunHello {

}
