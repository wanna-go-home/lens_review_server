## Ansible

http://wy0105.iptime.org:8081/#/login

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
docker run -d -p 8080:8080 springio/gs-spring-boot-docker
```

## Ref.
https://spring.io/guides/gs/spring-boot-docker/



