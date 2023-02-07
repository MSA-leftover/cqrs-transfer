package com.cqrs.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class DepositMoneyEvent {
    private String userID;
    private String accountID;
    private String destinationAccountID;
    private Long balance;// Money 금액.
}
