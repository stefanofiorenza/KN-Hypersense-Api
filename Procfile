web: java $JAVA_OPTS -Xmx256m -jar target/*.jar --spring.profiles.active=prod,heroku,api-docs,no-liquibase
release: cp -R src/main/resources/config config && ./mvnw -ntp -Pprod,api-docs,heroku,no-liquibase
