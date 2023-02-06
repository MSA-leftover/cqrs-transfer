package com.cqrs.command.application;


import com.cqrs.command.commands.DepositMoneyCommand;
import com.cqrs.command.commands.WithdrawMoneyCommand;
import com.cqrs.command.dto.DepositDTO;
import com.cqrs.command.dto.WithdrawDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService{
    private final CommandGateway commandGateway;

    @Override
    public Mono<String> depositMoney(DepositDTO depositDTO){
        return Mono.fromFuture(
                this.commandGateway.send(new DepositMoneyCommand(
                        depositDTO.getAccountID(),depositDTO.getUserID(),
                        depositDTO.getDestinationAccountID(),depositDTO.getBalance()
                ))
        );
    }

    @Override
    public Mono<String> withdrawMoney(WithdrawDTO withdrawDTO){//send 는 비동기, sendAndWait 은 동기.
        return Mono.fromFuture(
                this.commandGateway.send(new WithdrawMoneyCommand(
                        withdrawDTO.getAccountID(),withdrawDTO.getUserID(),withdrawDTO.getBalance()
                ))
        );
    }

    // TODO Adapt Java 8+ CompletableFuture to Mono
//    Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
//        return Mono.fromFuture(future);
//    }
}
