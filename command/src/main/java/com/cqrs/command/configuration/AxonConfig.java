package com.cqrs.command.configuration;

import com.cqrs.command.aggregate.Transfer;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfig {

    private final int AGGREGATE_SNAPSHOT_THRESHOLD=5;

    //Snapshot Create
    @Bean
    public AggregateFactory<Transfer> aggregateFactory(){
        return new GenericAggregateFactory<Transfer>(Transfer.class);
    }

    @Bean
    public Snapshotter snapshotter(EventStore eventStore, TransactionManager transactionManager){
        return AggregateSnapshotter.builder()
                .eventStore(eventStore)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(EventStore eventStore, TransactionManager transactionManager){
        return new EventCountSnapshotTriggerDefinition(snapshotter(eventStore, transactionManager),this.AGGREGATE_SNAPSHOT_THRESHOLD);
    }

    @Bean
    public Repository<Transfer> transferRepository(EventStore eventStore, SnapshotTriggerDefinition definition, Cache cache){
        return CachingEventSourcingRepository
                .builder(Transfer.class)
                .eventStore(eventStore)
                .snapshotTriggerDefinition(definition)
                .cache(cache)
                .build();
    }

    //Cache
    @Bean
    public Cache cache(){
        return new WeakReferenceCache();
    }
}
