package com.krish.PrometheusDemo;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public MeterRegistry meterRegistry(MeterFilter meterFilter) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%meterRegistry bean created&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        return new SimpleMeterRegistry();
        CompositeMeterRegistry meterRegistry = new CompositeMeterRegistry();
        meterRegistry.config().meterFilter(meterFilter);
        System.out.println("meter filter set");
        return meterRegistry;
    }

//    @Bean
//    public Timer timer(MeterRegistry registry) {
//        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Timer bean is created $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//
//        return Timer
//                .builder("api.profiles.created.time")
//                .description("Time taken to create a profile")
//                .register(registry);
//    }

    //FIXME use filter and remove tags
    @Bean
    public MeterFilter meterFilter() {
        return MeterFilter.accept(
                id -> "http.server.requests".equals(id.getName()) &&
                        id.getTags().stream().anyMatch(t ->
                                "method".equals(t.getKey())
                                || "status".equals(t.getKey())
                                || "uri".equals(t.getKey()))
        );
    }
}
