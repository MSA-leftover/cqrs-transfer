package com.cqrs.query.projection;

import com.cqrs.core.events.TransferCreationEvent;
import com.cqrs.query.dao.TransferRepository;
import com.cqrs.query.dto.TransferQuery;
import com.cqrs.query.entity.Transfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Component
@ProcessingGroup("transfer")
public class TransferProjection {

    private final TransferRepository repository;

    /**
     * EventHandler 와 QueryHandler 자체를 Reactive 하게 실행시킬 방법?
     */
    @EventHandler
    @AllowReplay // Replay 해야할 이벤트가 너무 많다면, 엄청나게 느릴 것. 반드시 성능 최적화 필요.
    protected void on(TransferCreationEvent event, @Timestamp Instant instant){
        log.debug("projecting {} , timestamp : {}", event, instant.toString());
        this.repository.save(Transfer.builder()
                .accountID(event.getAccountID())
                .userID(event.getUserID())
                .destinationAccountID(event.getDestinationAccountID())
                .balance(event.getBalance()).build()).log("save")
                .subscribe();
    }

    @QueryHandler
    public Mono<Transfer> on(TransferQuery query){
        log.debug("Query Transfer by id");
        return this.repository.findById(query.getAccount_id())
                .switchIfEmpty(Mono.defer(Mono::empty));
    }
}