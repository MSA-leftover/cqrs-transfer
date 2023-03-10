package com.cqrs.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@ToString
@Getter
public class DepositMoneyCommand {
    @TargetAggregateIdentifier
    private String accountID;
    private String userID;
    private String destinationAccountID;
    private Long balance;
}
