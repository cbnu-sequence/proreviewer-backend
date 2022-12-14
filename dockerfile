FROM gradle:7.5.1-jdk18 AS builder

WORKDIR /

COPY build.gradle .
COPY gradlew .
COPY settings.gradle .

RUN chmod +x ./gradlew
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true

COPY . .

RUN gradle clean build --no-daemon -x test


FROM openjdk:20-jdk-slim

COPY --from=builder /build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]