#Service SoapUI Support

#Folder Structure

````
data/
	data.properties
	data.xls
properties/
	config-env.properties
scripts/
	frameworklibrary/
		script.groovy
		script.js
	lib/
		external.jar
soapUI-project.xml
pom.xml
````

Purpose of each file:

 * __config-env.properties__: Has the information of the environment,  it is formatted in  key=value pairs.
 * __data.properties__, __data.xls__: Files used as a Data Source for data-driving testing.
 * __script.groovy__, __script.js__: Is the most powerful TestStep in soapUI, in the sense that it allows you to do more or less whatever you might need to within the execution of your Tests. The contained script will be executed each time the TestStep is run and can do more or less anything possible with the built in API's available. This can be written in either .groovy or .js
 * __soapUI-project.xml__: This file specifies the SoapUI project, a project can contain any number of functional tests, load tests and service simulations required for your testing purposes.
 * __pom.xml__: Maven build file.