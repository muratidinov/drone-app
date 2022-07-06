# Drone-App

### Description

Application that registers drones for subsequent refueling of medicines within the capabilities of
the drone and delivery to the addressee. At the moment, the application can:

- register drones
- fill them with medicines along with their images
- get a list of free drones
- receive a list of loaded medications in the drone
- get a percentage of the drone's charge

### What is already included:

> if you want to get some default drones and medications, then add 'test-data' context to liquibase contexts

> To control the application you can use the swagger at http://localhost:8081/swagger/index.hrml

# How to start:

- ### For docker:
    ```sh
    $ ../rinat-muratidinov$ mvn clean package
    $ ../rinat-muratidinov$ docker-compose up --build
    ```
  Or just run file build_and_start_in_docker.sh
- ### For manual start:
    ```sh
    $ ../rinat-muratidinov$ mvn clean spring-boot:run
    ```

# Requirements

| Java 11+
| -------

