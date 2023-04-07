package com.cqrs.query.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Table("transfer_proj")
public class Transfer {

    @Id
    @Column("account_id")
    private String accountID;
    @Column("user_id")
    private String userID;
    @Column("destination_account_id")
    private String destinationAccountID;
    @Column("balance")
    private Long balance;

}
