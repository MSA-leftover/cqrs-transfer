package com.cqrs.command.dao.reactive;

import com.cqrs.command.aggregate.StateStoreTransfer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateTransferRepository extends R2dbcRepository<StateStoreTransfer,String> {

}
