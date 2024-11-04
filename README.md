# StoreAssessment

Project contain two applications
* Inventory Service.
* Order Service.

## Steps:

* Build Inventory Service ```mvn clean install```
* Build Order Service ```mvn clean install```
* Run ```docker compose up -d```

## Inventory Service APIs

| Description                   | API                                       |
|-------------------------------|-------------------------------------------|
| fetch available item quantity | ```[POST] /8001/api/v1/inventory```       |
| update stock to place order   | ```[PUT] /8001/api/v1/inventory/update``` |

## Order Service APIs

| Description             | API                                    |
|-------------------------|----------------------------------------|
| fetch order by order id | ```[GET] /8002/api/v1/order/{id}```    |
| place order             | ```[POST] /8002/api/v1/order/create``` |

## Swagger

| Service           | URL                                           |
|-------------------|-----------------------------------------------|
| Inventory Service | ```http://{ip}:8001/swagger-ui/index.html/``` |
| Order Service     | ```http://{ip}:8001/swagger-ui/index.html/``` |
