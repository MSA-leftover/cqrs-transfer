package com.cqrs.command.aggregate;

import com.cqrs.command.commands.DepositMoneyCommand;
import com.cqrs.command.commands.TransferCreationCommand;
import com.cqrs.command.commands.WithdrawMoneyCommand;
import com.cqrs.core.events.DepositMoneyEvent;
import com.cqrs.core.events.TransferCreationEvent;
import com.cqrs.core.events.WithdrawMoneyEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Aggregate
public class Transfer {

    @AggregateIdentifier
    private String accountID;
    private String userID;
    private String destinationAccountID;
    private Long balance;

    @CommandHandler
    public Transfer(TransferCreationCommand command){
        log.debug("handling command : {}",command);
        TransferCreationEvent event = new TransferCreationEvent(command.getAccountID(),command.getDestinationAccountID(),command.getUserID(), command.getBalance());
        apply(event);
    }

    @EventSourcingHandler
    protected void on(TransferCreationEvent event){
        this.accountID = event.getAccountID();
        this.userID = event.getUserID();
        this.balance = event.getBalance();
        this.destinationAccountID = event.getDestinationAccountID();
    }

    @CommandHandler
    protected void depositMoney(DepositMoneyCommand command){
        // Exception Handling 여기서 수행.
        log.debug("handling deposit command : {}",command);
        apply(new DepositMoneyEvent(command.getUserID(),command.getAccountID(),command.getDestinationAccountID(),command.getBalance()));
    }

    @EventSourcingHandler
    protected void on(DepositMoneyEvent event){
        log.debug("handling deposit event : {}",event);
        this.balance += event.getBalance();
    }

    @CommandHandler
    protected void withdrawMoney(WithdrawMoneyCommand command){
        //예외 처리는 Account Service 에서 검증하고 왔을 것임.
        log.debug("handling withdraw command : {}",command);
        apply(new WithdrawMoneyEvent(command.getUserID(),command.getAccountID(), command.getBalance()));
    }

    @EventSourcingHandler
    protected void on(WithdrawMoneyEvent event){
        log.debug("handling withdraw event : {}",event);
        this.balance -= event.getBalance();// 이부분을 나중에 Ochestrator 에게 전달해야 함.
    }
}
