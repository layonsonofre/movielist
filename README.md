# Movie List

## JDK Version
```shell
java 17.0.1 2021-10-19 LTS
```

## Install

```shell
mvn clean -DskipTests=true && mvn clean install -DskipTests
```

## Running

```shell
mvn spring-boot:run
```

The app should be responding on the port **8000** (check file `src/main/resources/application.yml`).

There are two endpoints:

* `GET /api/awards`: Return the producers that match the required conditions
* `GET /api/title?title=X`: Return a list of movies that match the title

## Tests

```shell
mvn test
```

