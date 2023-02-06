package com.cqrs.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private String accountID;
    private String userID;
    private String destinationAccountID;
    private Long balance;
}
