# Caching example using Redis and Spring cache

Reference: https://www.youtube.com/watch?v=CZ3wIuvmHeM

## How to build ?

Run following command from your top level directory
```shell script
gradle build
```

## How to run ?
- Start MongoDB
- Start Redis
- Start counter service
    ```shell script
    cd service
    gradle bootRun
    ```
- Run test application
    ```shell script
    cd test-application
    gradle bootRun
    ```
