FROM openjdk:8-jre-alpine
ADD target/administracion-0.0.1-SNAPSHOT.jar administracion.jar
ENTRYPOINT ["java","-Djava.administracion.egd=file:/dev/./urandom","-jar","/administracion.jar"]
EXPOSE 18080