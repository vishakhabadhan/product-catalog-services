FROM openjdk:8
MAINTAINER product-catalog-services
VOLUME /tmp
ARG JAR_FILE=target/product-catalog-services.jar
ADD ${JAR_FILE} product-catalog-services.jar
ENTRYPOINT ["java","-jar","/product-catalog-services.jar"]