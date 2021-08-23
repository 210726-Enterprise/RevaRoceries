# Tomcat > webapps, lib
FROM tomcat:9.0-jre8-alpine

ADD target/RevaRoceries-0.1.war /usr/local/tomcat/webapps/RevaRoceries.war

EXPOSE 8080
CMD ["catalina.sh", "run"]