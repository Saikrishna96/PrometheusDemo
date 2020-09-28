# PrometheusDemo
Demo of how prometheus works

Run this project using : mvn spring-boot:run


We have added spring actuator for publishing metrics. 
All the metrics will be published at endpoint : http://localhost:8080/actuator

Prometheus server can scrap the metrics data from this endpoint : http://localhost:8080/actuator/prometheus

After this, prometheus server can publish the metrics to any graphic dashboard like Grafana




