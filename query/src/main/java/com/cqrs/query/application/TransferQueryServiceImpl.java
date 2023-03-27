package com.cqrs.query.application;

import com.cqrs.query.dto.TransferQuery;
import com.cqrs.query.entity.Transfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferQueryServiceImpl implements TransferQueryService{

    private final Configuration configuration;
    private final ReactorQueryGateway queryGateway;

    @Override
    public Mono<Void> reset() {//다만 Insert Query 의 경우, Replay 를 할 때 Error 가 발생하므로, 이미 있다면 Update 하게 로직 변경 필요.
        return Mono.fromCallable(() -> {
            this.configuration.eventProcessingConfiguration()
                    .eventProcessorByProcessingGroup("transfer",
                            TrackingEventProcessor.class)
                    .ifPresent(trackingEventProcessor -> {
                        trackingEventProcessor.shutDown();
                        trackingEventProcessor.resetTokens();
                        trackingEventProcessor.start();
            });
            return null;//isEmpty
        });

    }

    @Override//Point-to-Point Query. 단일 조회로 끝나는거면 Point-to-Point 로 처리.
    public Mono<Transfer> getTransferInfo(String account_id) {
        TransferQuery query = new TransferQuery(account_id);
        log.debug("transaction : {}",query);
        return queryGateway.query(query, Transfer.class);
    }

    @Override//Subscription 방법, Client 로부터 Connection 연결하면 해제하지 않고 계속 연결. Server Sent Event 방식(Event Source)으로 UI 구현 필요.
    public Flux<Transfer> getTransferSubscription(String account_id) {
        TransferQuery query = new TransferQuery(account_id);
        log.debug("transaction : {}",query);

        return queryGateway.subscriptionQuery(query, ResponseTypes.multipleInstancesOf(Transfer.class), ResponseTypes.instanceOf(Transfer.class))
                .flatMapMany(result -> result.initialResult()
                        .flatMapMany(Flux::fromIterable)
                        .concatWith(result.updates())
                        .doFinally(signal -> result.close()));
    }

}
