FROM openjdk:11
MAINTAINER Andrei Dodu
COPY target/horoscope.jar horoscope.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/horoscope.jar"]
