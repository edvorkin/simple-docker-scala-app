# maintainer: Eugene Dvorkin (@edvorkin)
FROM centos 
#RUN sudo yum update
RUN yum install -y java-1.8.0-openjdk.x86_64
#the fat jar assembly aready build outside of container
ADD ./target/scala-2.11/simple-docker-scala-app-assembly-0.0.2.jar /tmp/simple-docker-scala-app-assembly-0.0.2.jar
#start scala application on port 8080
EXPOSE 8080
