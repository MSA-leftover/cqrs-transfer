package com.cqrs.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class TransferCreationEvent {
    private String accountID;
    private String destinationAccountID;
    private String userID;
}
