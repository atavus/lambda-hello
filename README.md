## AWS API Gateway + AWS Lambda - Hello World Example in Java/Python/Node ##

### Overview ###

This example uses Cloudformation to deploy three separate AWS Lambda functions each of which is exposed via AWS API Gateway.
There is a unit test using jUnit, and BDD integration tests using Cucumber.
The three AWS lambda functions do the same thing. One is written in Node 6.1, one in Python 3.6, and one in Java 8.
The build process uses Maven. This example does not set up a build pipeline.

The performance results clearly show that it is much better to use Node or Python for AWS Lambda.
All measures for Java are excessive (Duration, Memory, and package size) and would result in significant additional running costs.

### Dependencies ###

The development environment must have Node, Python3, and Java8 installed. 
Maven must also be installed. 
Boto3 must be installed.
Lombok must be installed for java and the Java IDE (e.g. eclipse).

### Assumptions ###

The file bucket.name contains the name of an S3 bucket to create. It is assumed that the bucket does not exist.
The file profile.name contains the name of an AWS profile. It is assumed that this profile has already been configured.

### Performance ###

Metrics were measured by inspecting CloudWatch Logs after the integration tests had been run three times in a row.

| Invocation | Language | Duration  | Memory Used | Size     |
| 1          | Node     | 58.69ms   | 19MB        | 346bytes |
| 2          | Node     | 0.58ms    | 19MB        | 346bytes |
| 3          | Node     | 0.48ms    | 19MB        | 346bytes |

| Invocation | Language | Duration  | Memory Used | Size     |
| 1          | Python   | 11.70ms   | 21MB        | 396bytes |
| 2          | Python   | 0.65ms    | 21MB        | 396bytes |
| 3          | Python   | 0.59ms    | 21MB        | 396bytes |

| Invocation | Language | Duration  | Memory Used | Size     |
| 1          | Java     | 7037.13ms | 53MB        | 10MB     |
| 2          | Java     | 170.63ms  | 54MB        | 10MB     |
| 3          | Java     | 133.24ms  | 54MB        | 10MB     |


### Install ###

$ python3 install.py

### Un-Install ###

$ bash uninstall.sh

### Run unit tests ###

$ mvn test

### Run integration tests ###

$ mvn verify

