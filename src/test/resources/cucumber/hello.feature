
Feature: Hello
  As a user
  I want to use a lambda function
  So that I don't need to host infrastructure
  
Background:
  Given A lambda function is installed

Scenario Outline: Check Java lambda function
  Given I have a java lambda function
  When I send <who>
  Then the result should be <result>
    
  Examples:
  | who   | result     |
  | joe   | Hello joe! |

Scenario Outline: Check Python lambda function
  Given I have a python lambda function
  When I send <who>
  Then the result should be <result>
    
  Examples:
  | who   | result     |
  | joe   | Hello joe! |

Scenario Outline: Check Node lambda function
  Given I have a node lambda function
  When I send <who>
  Then the result should be <result>
    
  Examples:
  | who   | result     |
  | joe   | Hello joe! |
  