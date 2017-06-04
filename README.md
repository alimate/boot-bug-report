# Netty throws NPE when stopping the application
As I was experimenting with Spring Boot `2.0.0.M1`, I've encountered with a strange problem. When I fail to inject a
dependency to my controller, obviously Spring Boot can't run properly and tries to stop the Netty server. There are 
the following two problems in this situation:
 1. Throwing a `NullPointerException` when stopping the Netty
 2. Discarding the actual problem details (Not satisfying the dependency) and just showing the `NullPointerException`


## Project Structure
I have a very simple Spring Boot `2.0.0.M1` project with `webflux` starter package:
```
me.alidg
   +
   |
   +- rest
   |   +
   |   |
   |   +- BugsResource.java // Inject the BugService
   |
   +- service
   |     +
   |     |
   |     + BugService.java
   |     |
   |     + BugServiceImpl.java // No streotype annotation 
   |
   + Application.java
```
I forget to annotate the `BugService` implementation with an appropriate stereotype annotation and then inject the 
`BugService` in `BugsResource`. Obviously Spring Boot can't not run properly with this configuration but It just show
the following stacktrace instead of reporting the actual problem:
```java
java.lang.IllegalStateException: java.lang.NullPointerException
	at org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext.stopAndReleaseReactiveWebServer(ReactiveWebServerApplicationContext.java:152) ~[spring-boot-2.0.0.M1.jar:2.0.0.M1]
	at org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext.refresh(ReactiveWebServerApplicationContext.java:52) ~[spring-boot-2.0.0.M1.jar:2.0.0.M1]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:795) [spring-boot-2.0.0.M1.jar:2.0.0.M1]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:397) [spring-boot-2.0.0.M1.jar:2.0.0.M1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:338) [spring-boot-2.0.0.M1.jar:2.0.0.M1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1290) [spring-boot-2.0.0.M1.jar:2.0.0.M1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1278) [spring-boot-2.0.0.M1.jar:2.0.0.M1]
	at me.alidg.BugReportApplication.main(BugReportApplication.java:12) [main/:na]
Caused by: java.lang.NullPointerException: null
	at org.springframework.boot.web.embedded.netty.NettyWebServer.stop(NettyWebServer.java:113) ~[spring-boot-2.0.0.M1.jar:2.0.0.M1]
	at org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext.stopAndReleaseReactiveWebServer(ReactiveWebServerApplicationContext.java:148) ~[spring-boot-2.0.0.M1.jar:2.0.0.M1]
	... 7 common frames omitted
```

## How to Reproduce?
Just run the following:
```bash
./gradlew bootRun
```