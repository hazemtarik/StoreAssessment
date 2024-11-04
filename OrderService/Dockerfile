FROM openjdk:21
LABEL authors="Hazem Tarek"
WORKDIR ./order
COPY /target/OrderService.jar .
EXPOSE 8002:8002
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "OrderService.jar"]