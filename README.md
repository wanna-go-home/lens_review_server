## How to build using gradle

```
./gradlew build
```

## Containerize

```
docker build --build-arg JAR_FILE="build/libs/*.war" -t springio/gs-spring-boot-docker .
```

## Run on docker

```
docker run -p 8080:8080 springio/gs-spring-boot-docker
```

## Ref.
https://spring.io/guides/gs/spring-boot-docker/



