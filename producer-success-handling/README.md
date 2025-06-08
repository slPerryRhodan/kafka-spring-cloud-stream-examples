# How To Use

## Handle error with global "errorChannel"
1. Start docker kafka infrastructure [docker-compose-infra.yml](docker-compose-infra.yml)
2. Run spring boot application [ProducerSuccessHandlingApplication.java](src/main/java/org/mykafka/producersuccesshandling/ProducerSuccessHandlingApplication.java)
3. Call REST endpoint via http://localhost:8080/v1/orderevent with Postman or some other tool
   1. Should be successful with a log from [ServiceActivatorSuccessHandler.java](src/main/java/org/mykafka/producersuccesshandling/event/successhandler/ServiceActivatorSuccessHandler.java)
4. Stop the kafka container (only the kafka container and not zookeeper) created in the first step.
5. Execute sept 3 again
   1. Should log an error in [ServiceActivatorErrorHandler.java](src/main/java/org/mykafka/producersuccesshandling/event/errorhandler/ServiceActivatorErrorHandler.java)[ServiceActivatorErrorHandler](src%2Fmain%2Fjava%2Forg%2Fmykafka%2Fproducererrorhandling%2Fboundary%2Fout%2Fevent%2Ferrorhandler%2FServiceActivatorErrorHandler.java) via the global ```errorChannel```

