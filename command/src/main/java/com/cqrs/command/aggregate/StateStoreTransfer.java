package com.cqrs.command.aggregate;

import com.cqrs.command.commands.StateTransferCreationCommand;
import com.cqrs.core.events.StateTransferCreationEvent;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Aggregate
@Table(name = "transfer")
@Entity(name = "transfer")
public class StateStoreTransfer {

    @AggregateIdentifier
    @jakarta.persistence.Id//For JPA
    @Id//For R2dbc
    @Column("account_id")
    @Getter
    private String accountID;
    @Column("user_id")
    private String userID;
    @Column("destination_account_id")
    private String destinationAccountID;
    @Column("balance")
    private Long balance;

    @CommandHandler
    public StateStoreTransfer(StateTransferCreationCommand command){ //EventSourcing Handler 필요 없음. 대신, 저장 로직을 이곳에 포함시켜야 함.
        log.info("handling {}",command);
        this.accountID = command.getAccountID();
        this.userID = command.getUserID();
        this.destinationAccountID = command.getDestinationAccountID();
        this.balance = command.getBalance();

        apply(new StateTransferCreationEvent(command.getAccountID(),command.getDestinationAccountID(),
                command.getUserID(),command.getBalance()));
    }
}
