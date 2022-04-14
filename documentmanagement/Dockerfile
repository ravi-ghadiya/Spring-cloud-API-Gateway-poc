FROM openjdk:11
WORKDIR /usr/src/myapp
COPY target/documentmanagement-*.war /documentmanagement.war
EXPOSE 8189
CMD ["java", "-jar", "/documentmanagement.war"]