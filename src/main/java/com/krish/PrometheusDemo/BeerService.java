package com.krish.PrometheusDemo;

import com.google.common.base.Stopwatch;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Service
public class BeerService {

    private MeterRegistry meterRegistry;
    private final Counter lightOrderCounter;
    private final Counter aleOrderCounter;
    private final Queue<String> orders;
    private final Timer.Builder timerBuild;

    public BeerService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        lightOrderCounter = this.meterRegistry.counter("beer.orders.krish", "type", "light"); // 1 - create a counter
        aleOrderCounter = Counter.builder("beer.orders.sai")    // 2 - create a counter using the fluent API
                .tag("type", "ale")
                .description("The number of orders ever placed for Ale beers")
                .register(meterRegistry);

        orders = new ArrayDeque<>();
        Gauge.builder("beer.ordersInQueue", orders, Collection::size)
                .description("No of unserved beers")
                .register(meterRegistry);

//        this.timer = meterRegistry.timer("http.server.requests",
//                "method", "SQL", "status", "200", "uri", "DAO/GetPromocodeUsedCount");
//         this.timer = Timer.builder("http.server.requests")
//                .tag("method", "SQL")
//                .tag("status" , "200")
//                .tag("uri", "DAO/GetPromocodeUsedCount")
//                 .tag("exception", "None")
//                 .tag("outcome", "SUCCESS")
//                .register(meterRegistry);

         this.timerBuild = Timer.builder("http.server.requests");
    }

    void handleOrders(String queryName){
        System.out.println("Handling orders");
        long start = System.currentTimeMillis();
        long startTime = Instant.now().toEpochMilli();
        System.out.println("System.currentTimeMillis() : " + start + ", Instant.now().toEpochMilli() : " + startTime);
        fullfillOrders();
        long diff = startTime == 0 ? 0 : Instant.now().toEpochMilli() - startTime;
        System.out.println("Diff : " + (double) diff / 1000);
        Timer timer = timerBuild
                .tag("method", "SQL")
                .tag("status" , "200")
                .tag("uri", "DAO/" + queryName)
                .tag("exception", "None")
                .tag("outcome", "SUCCESS")
                .register(meterRegistry);
        timer.record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
        System.out.println("Finished handling orders");
    }


//    @Scheduled(fixedRate = 5000)
    @Timed(description = "Time spent serving orders")
    void orderBeer() {
        System.out.println("ordered beer");

//        if ("light".equals(order.type)) {
        lightOrderCounter.increment(1.0);  // 3 - increment the counter
//        } else if ("ale".equals(order.type)) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Error error");
        }
        aleOrderCounter.increment();
        aleOrderCounter.increment();
//        }
        orders.add("a1");
        orders.add("a2");
        orders.add("a3 ");

        Stopwatch stopwatch = Stopwatch.createStarted();

        // your job here
        fullfillOrders();

        // check time
        Duration duration = Duration.ofMillis(stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        long seconds = duration.getSeconds();
//        meterRegistry.timer("custom.timer", // metric name
//                "tag1", this.getClass().getSimpleName(), // class name
//                "tag2", new Exception().getStackTrace()[0].getMethodName()) // method name
//                .record(seconds, TimeUnit.SECONDS);


    }


    void fullfillOrders() {

        for (int i = 0; i < 3; i++) {
//            System.out.println("fullfilled order : " + orders.poll());
            System.out.println("fullfilling order : " + i );
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Error error");
            }
        }
    }
}