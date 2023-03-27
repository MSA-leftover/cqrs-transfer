package com.cqrs.query.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferQuery {
    private String account_id;
}
