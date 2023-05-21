# Next-Generation Apps with Spring Boot 3

The recent release of Spring Boot 3 and Spring Framework 6 laid the foundation for the next
generation of modern Java applications. This session will highlight what’s new, and demonstrate
patterns and techniques for cloud-native development.

Support for generating native images with GraalVM is now part of the core framework, making it
straightforward to build efficient applications with instant startup time and reduced memory consumption.
The new Java 17 baseline and support for Jakarta EE 10 unlocks many new features and integrations.
Observability is now a core tenet of the Spring ecosystem, with monitoring and tracing built into each
Spring module and powered by Micrometer and OpenTelemetry. Web applications can now rely on new features
to handle errors based on the Problem Details for HTTP APIs specification. Finally, HTTP clients can now
be powered by convenient interfaces for which the framework generates the implementation.

## Usage

Run the Grafana observability stack:

```bash
docker-compose up -d grafana
```

Run Book Service:

```bash
./gradlew bootTestRun
```

Run Order Service:

```bash
./gradlew bootRun
```

## Observability Stack

Both Spring Boot applications are observable, as any cloud native application should. Prometheus metrics are backed by Spring Boot Actuator and Micrometer Metrics. Distributed tracing is backed by OpenTelemetry and Micrometer Tracing.

**Grafana** lets you query and visualize logs, metrics, and traces from your applications. After running the Docker Compose
configuration as explained in the previous section, you can access Grafana on port 3000. It provides already dashboards
to visualize metrics from Spring Boot, Spring Cloud Gateway, and Spring Cloud Circuit Breaker. In the "Explore" panel,
you can query logs from Loki, metrics from Prometheus, and traces from Tempo.

**Promtail** collects logs from all containers and forwards them to Loki.

**Loki** is a log aggregation system part of the Grafana observability stack. "It's like Prometheus, but for logs."
Logs are available for inspecting from Grafana.

**Tempo** is a distributed tracing backend part of the Grafana observability stack. Spring Boot applications sends traces to Tempo,
which made them available for inspecting from Grafana. The traces follows the OpenTelemetry format and protocol.

**Prometheus** is a monitoring system part of the Grafana observability stack. It parses the metrics endpoints exposed by Spring Boot
applications (`/actuator/prometheus`). Metrics are available for inspecting and dashboarding from Grafana.
