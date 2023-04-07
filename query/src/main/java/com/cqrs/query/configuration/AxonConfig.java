package com.cqrs.query.configuration;

import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    //Replay 성능 향상. Batch 단위로 작동할 때, Batch Size 가 Default 로 1, SingleThread 이기 때문에, 이 설정을 변경해야 함.
    @Autowired
    public void configure(EventProcessingConfigurer configurer){
        configurer.registerTrackingEventProcessor(
                "transfer", org.axonframework.config.Configuration::eventStore,
                configuration -> TrackingEventProcessorConfiguration.forParallelProcessing(3)
                        .andBatchSize(100)
        );
        configurer.registerSequencingPolicy(
                "transfer",
                configuration -> SequentialPerAggregatePolicy.instance());//단일 스레드 내부에서, 동일 Aggregate Event 가 순차적으로 실행.
    }
}
