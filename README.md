# horoscope
A complete horoscope web service that I written, which will be integrated with alexa. The application provides an REST API and is ready to host a web UI. In fact my idea is to allow the user to add daily horoscopes from the web application and at the same time the application has to provide this information to alexa.

I used Spring Boot, liquibase, Hibernate and MySQL.

In order to run it make a maven clean install and then run as Spring Boot application. 

Then make a call with postman to retrieve some informations: http://localhost:8080/horoscope/sign/${YOUR_SIGN}

[For further information, see my blog](http://dodu.it/?page_id=516)

P.S. don't forget to install lombok in your eclipse
