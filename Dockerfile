From eclipse-temurin:17-jdk-jammy
WORKDIR /
VOLUME /tmp
ADD target/SimpleUserManagementSystem-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]