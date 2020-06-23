## How to build using gradle

```
./gradlew build && java -jar build/libs/gs-spring-boot-docker-0.1.0.war
```

## Containerize

```
docker build --build-arg JAR_FILE=build/libs/*.war -t springio/gs-spring-boot-docker .
```

## Run on docker

```
docker run -p 8080:8080 springio/gs-spring-boot-docker
```





