# maintainer: Eugene Dvorkin (@edvorkin)
FROM ubuntu:14.04
RUN sudo apt-get install java
#the fat jar assembly aready build outside of container
ADD ./target/scala-2.11/spray-slick-swagger-assembly-0.0.2.jar /tmp/spray-slick-swagger-assembly-0.0.2.jar
#start scala application on port 8080
EXPOSE 8080
#CMD ["java", "-jar", "/tmp/spray-slick-swagger-assembly-0.0.2.jar","Boot"]
ENV SERVICE_8080_NAME=service_spray \
    SERVICE_8080_TAGS=discoverable
