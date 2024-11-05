FROM openjdk:21
LABEL authors="Hazem Tarek"
WORKDIR ./inventory
COPY /target/InventoryService.jar .
EXPOSE 8001:8001
CMD ["java", "-jar","InventoryService.jar"]