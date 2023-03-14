package com.cqrs.query.dao;

import com.cqrs.query.entity.Transfer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends R2dbcRepository<Transfer, String> {
}
