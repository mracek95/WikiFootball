## Prerequisites

Java 8 installed

## Building

Use gradle wrapper to build the application.

```bash
./gradlew shadowJar
```

Builded jar file is located in:

```bash
./build/libs/WikiFootball-1.0-SNAPSHOT-all.jar
```

## Run the application

```bash
java -jar build/libs/WikiFootball-1.0-SNAPSHOT-all.jar "Liverpool"
```

## Run tests

```bash
./gradlew test
```
