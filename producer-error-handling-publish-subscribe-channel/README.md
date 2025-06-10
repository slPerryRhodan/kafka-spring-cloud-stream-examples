# How To Use

## Handle error with global "errorChannel"
1. Start docker kafka infrastructure [docker-compose-infra.yml](docker-compose-infra.yml)
2. Run spring boot application [ProducerErrorHandlingPublishSubscribeChannelApplication.java](src/main/java/org/mykafka/producererrorhandlingpublishsubscribechannel/ProducerErrorHandlingPublishSubscribeChannelApplication.java)
3. Call REST endpoint via http://localhost:8080/v1/orderevent with Postman or with [post-orderevent.http](request-collection/post-orderevent.http)
   1. Should be successful with a log from [ServiceActivatorSuccessHandler.java](src/main/java/org/mykafka/producererrorhandlingpublishsubscribechannel/event/successhandler/ServiceActivatorSuccessHandler.java)
4. Stop the kafka container (only the kafka container and not zookeeper) created in the first step.
5. Execute sept 3 again
   1. Should log an error in [PublishSubscribeChannelErrorHandler.java](src/main/java/org/mykafka/producererrorhandlingpublishsubscribechannel/event/errorhandler/PublishSubscribeChannelErrorHandler.java) via the global `errorChannel` with the `myErrorHandler` method.

