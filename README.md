# Build Cafe Microservices With Springboot - Cafe Royale
## ETS_PPLBO_38
### Caturiani Pratidina Bintari 3B-D4TI

Cafe Royale - Cafe Microservices with springboot untuk pengumpulan ETS PPLBO
Teknologi yang digunakan :
* Java
* Springboot

Pattern yang diterapkan : Message Patern Style

## Service
* API Gateway
* Product Service
* Order Service
* Inventory Service
* Delivery Service
* Payment Service

## Rest API
Endpoint dan Method pada setiap service:
| Service | Method | endpoint |
| ------- | ------ | -------- |
| products | GET | http://localhost:8080/api/products |
| products | GET | http://localhost:8080/api/products/{id} |
| products | POST | http://localhost:8080/api/products |
| products | POST | http://localhost:8080/api/products/update/{id} |
| products | DELETE | http://localhost:8080/api/products/{ID} |
| orders | GET | http://localhost:8080/api/orders |
| orders | GET | http://localhost:8080/api/orders/{id} |
| orders | POST | http://localhost:8080/api/orders |
| orders | POST | http://localhost:8080/api/orders/update/{id} |
| orders | DELETE | http://localhost:8080/api/orders/{id} |
| payment | GET | http://localhost:8080/api/payment |
| payment | GET | http://localhost:8080/api/payment/{id} |
| payment | POST | http://localhost:8080/api/payment |
| payment | POST | http://localhost:8080/api/payment/update/{id} |
| delivery | GET | http://localhost:8080/api/sales-report |
| delivery | GET | http://localhost:8080/api/sales-report/{id} |

## Installation

Requires IntelliJ IDE to run
Install the dependencies and start the server.

Urutan menjalankan server :
1. API Gateway
2. Product Service
3. Inventory Service
4. Order Service
5. Payment Service
6. Delivery Service


Link youtube : https://youtu.be/_5GGhxJ983o
