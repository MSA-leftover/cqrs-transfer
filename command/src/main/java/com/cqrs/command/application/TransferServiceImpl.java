package com.cqrs.command.application;


import com.cqrs.command.commands.DepositMoneyCommand;
import com.cqrs.command.commands.TransferCreationCommand;
import com.cqrs.command.commands.WithdrawMoneyCommand;
import com.cqrs.command.dto.DepositDTO;
import com.cqrs.command.dto.TransactionDTO;
import com.cqrs.command.dto.WithdrawDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService{
    private final ReactorCommandGateway commandGateway;

    @Override
    public Mono<String> depositMoney(DepositDTO depositDTO){
        return this.commandGateway.send(new DepositMoneyCommand(
                        depositDTO.getAccountID(),depositDTO.getUserID(),
                        depositDTO.getDestinationAccountID(),depositDTO.getBalance()
                ));
    }

    @Override
    public Mono<String> withdrawMoney(WithdrawDTO withdrawDTO){//send 는 비동기, sendAndWait 은 동기.
        return this.commandGateway.send(new WithdrawMoneyCommand(
                        withdrawDTO.getAccountID(),withdrawDTO.getUserID(),withdrawDTO.getBalance()
                ));
    }

    @Override
    public Mono<Map<String, String>> createTransaction(TransactionDTO transactionDTO){
        return this.commandGateway.send(new TransferCreationCommand(
                transactionDTO.getAccountID(),transactionDTO.getDestinationAccountID(),
                transactionDTO.getUserID(), transactionDTO.getBalance()
        )).map(o -> {
            Map<String, String> result = new ConcurrentHashMap<>();
            result.put("res",o.toString());
            return result;
        });
    }

    // TODO Adapt Java 8+ CompletableFuture to Mono
//    Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
//        return Mono.fromFuture(future);
//    }
}
