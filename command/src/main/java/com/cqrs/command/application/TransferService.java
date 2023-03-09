package com.cqrs.command.application;

import com.cqrs.command.dto.DepositDTO;
import com.cqrs.command.dto.TransactionDTO;
import com.cqrs.command.dto.WithdrawDTO;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface TransferService {

    Mono<String> depositMoney(DepositDTO depositDTO);
    Mono<String> withdrawMoney(WithdrawDTO withdrawDTO);
    Mono<Map<String,String>> createTransaction(TransactionDTO transactionDTO);
    Mono<Map<String, String>> creationStateTransaction(TransactionDTO transactionDTO);
}
