## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
RealWorldTest is a test automation project to cover the Real World application https://github.com/gothinkster/realworld with API and UI tests.  
## Technologies
Project is created with:
* JDK v 17
* TestNG v 7.6
* Retrofit v 2.9
* Selenium v.4.3 
* Jackson v 2.10.1
* Maven v 4.0
* Hibernate validator v 7.0.5 Final


## Setup

1. Clone the git repository ```https://github.com/yuliiahonchar/realworldtest.git```
2. Create “env.properties” file in the root of the project with following configurations:

```
#Absolute URI to the API under test, for instance:
apiBaseUrl = https://api.realworld.io/api/
#Absolute URI to the application under test, for instance:
uiBaseUrl = https://demo.realworld.io/
#Default password for all your test users, for instance:
password = qwerty123
```
3. Run tests from the specific TestNG xml:  
```
mvn clean test -DsuiteXmlFile=src/test/resources/apiTestSuites/fullApiRegression.xml
```
Or run tests from the specific class:

```
mvn clean test -Dtest=SignUpTest.java
```
More details how to run tests with TestNG <a href="https://testng.org/doc/documentation-main.html#running-testng" target="_blank">here</a>
