package com.cqrs.query.dao;

import com.cqrs.query.entity.Transfer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TransferRepository extends R2dbcRepository<Transfer, String> {

    // R2dbc primary key save issue
    @Override
    @Query("INSERT INTO transfer_proj " +
            "(account_id, user_id, destination_account_id, balance) " +
            "VALUES (:#{#transfer.accountID}, :#{#transfer.userID}, :#{#transfer.destinationAccountID}, :#{#transfer.balance})")
    Mono<Void> save(
            @Param(value = "transfer") Transfer transfer);
}
