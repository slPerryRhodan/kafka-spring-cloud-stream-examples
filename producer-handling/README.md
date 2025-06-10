[![Build & Test](https://github.com/slPerryRhodan/kafka-spring-cloud-stream-examples/actions/workflows/producer-handling.yml/badge.svg?branch=main)](https://github.com/slPerryRhodan/kafka-spring-cloud-stream-examples/actions/workflows/producer-handling.yml)
# How To Use

## Handle error with global "errorChannel"
1. Start docker kafka infrastructure [docker-compose-infra.yml](docker-compose-infra.yml)
2. Run spring boot application [ProducerHandlingApplication.java](src/main/java/org/mykafka/producerhandling/ProducerHandlingApplication.java)
3. Call REST endpoint via http://localhost:8080/v1/orderevent with Postman or with [post-orderevent.http](request-collection/post-orderevent.http)
   1. Should be successful with a log from [ServiceActivatorSuccessHandler.java](src/main/java/org/mykafka/producerhandling/event/successhandler/ServiceActivatorSuccessHandler.java)
4. Stop the kafka container (only the kafka container and not zookeeper) created in the first step.
5. Execute sept 3 again
   1. Should log an error in [ServiceActivatorErrorHandler.java](src/main/java/org/mykafka/producerhandling/event/errorhandler/ServiceActivatorErrorHandler.java) via the global ```errorChannel```

## Async and Sync behavior
### Async
Check out the tests [OrderEventSupplierEmbeddedKafkaTest.java](src/test/java/org/mykafka/producerhandling/event/OrderEventSupplierEmbeddedKafkaTest.java) and [OrderEventSupplierTest.java](src/test/java/org/mykafka/producerhandling/event/OrderEventSupplierTest.java)
for the expected behavior
### Sync
Check out the test [OrderEventSupplierSyncProducerTest.java](src/test/java/org/mykafka/producerhandling/event/OrderEventSupplierSyncProducerTest.java)
for the expected behavior