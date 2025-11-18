FROM tomcat:9.0-jdk17

# Cloud Run sets PORT at runtime (default is 8080)
ENV PORT=8080

# Remove default app
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy WAR
COPY SIMEL_2_0-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/SIMEL_2_0-1.0-SNAPSHOT.war

# Force Tomcat's port to match Cloud Run's PORT variable
RUN sed -i "s/8080/${PORT}/" /usr/local/tomcat/conf/server.xml

EXPOSE 8080

CMD ["catalina.sh", "run"]