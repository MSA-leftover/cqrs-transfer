package com.cqrs.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class TransferCreationCommand {
    @TargetAggregateIdentifier
    private String accountID;
    private String destinationAccountID;
    private String userID;
    private long balance;
}
