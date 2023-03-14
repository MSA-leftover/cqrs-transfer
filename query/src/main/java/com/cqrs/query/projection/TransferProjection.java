package com.cqrs.query.projection;

import com.cqrs.core.events.TransferCreationEvent;
import com.cqrs.query.dao.TransferRepository;
import com.cqrs.query.entity.Transfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferProjection {

    private final TransferRepository repository;

    @EventHandler
    protected void on(TransferCreationEvent event, @Timestamp Instant instant){
        log.debug("projecting {} , timestamp : {}", event, instant.toString());

        this.repository.save(Transfer.builder()
                .accountID(event.getAccountID())
                .userID(event.getUserID())
                .destinationAccountID(event.getDestinationAccountID())
                .balance(event.getBalance()).build())
                .subscribe();
    }
}
