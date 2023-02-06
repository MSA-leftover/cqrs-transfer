package com.cqrs.command.application;

import com.cqrs.command.dto.DepositDTO;
import com.cqrs.command.dto.WithdrawDTO;
import reactor.core.publisher.Mono;

public interface TransferService {

    Mono<String> depositMoney(DepositDTO depositDTO);
    Mono<String> withdrawMoney(WithdrawDTO withdrawDTO);
}
