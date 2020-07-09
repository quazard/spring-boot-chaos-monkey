# Spring Boot Chaos Monkey Demo

Demo project to learn about the features of Spring Boot Chaos Monkey.

## How to run

Run the following command to compile and generate Docker images for each of the projects:

```sh
$ docker-compose up -d
```

```sh
# This will rebuild a docker container
$ docker-compose up -d --build gateway-service
```

## Basics

Visit [Spring Chaos Monkey](https://codecentric.github.io/chaos-monkey-spring-boot/) website for more information. This project contains a small Postman collection to test the different endpoints.