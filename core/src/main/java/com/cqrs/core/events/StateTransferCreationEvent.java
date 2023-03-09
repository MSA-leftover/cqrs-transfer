package com.cqrs.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class StateTransferCreationEvent {
    private String accountID;
    private String destinationAccountID;
    private String userID;
    private Long balance;
}
