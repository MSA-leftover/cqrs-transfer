package com.cqrs.query.application;

import com.cqrs.query.entity.Transfer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransferQueryService {
    Mono<Void> reset();
    Mono<Transfer> getTransferInfo(String account_id);
    Flux<Transfer> getTransferSubscription(String account_id);
}
