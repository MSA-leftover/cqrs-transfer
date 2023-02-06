package com.cqrs.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class WithdrawMoneyEvent {
    private String userID;
    private String accountID;
    private Long balance;// Money 금액.
}
