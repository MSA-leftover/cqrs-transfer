package com.cqrs.command.dao.jpa;

import com.cqrs.command.aggregate.StateStoreTransfer;
//import com.cqrs.command.aggregate.StateStoreTransfer;
//import io.smallrye.mutiny.Uni;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.reactive.mutiny.Mutiny;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateTransferJpaRepository extends JpaRepository<StateStoreTransfer, String> {
//@RequiredArgsConstructor
//@Slf4j
//public class StateTransferJpaRepository{
//    private final Mutiny.SessionFactory sessionFactory;
//
//    public Uni<StateStoreTransfer> finById(String id){
//        return this.sessionFactory.withSession(session -> session.find(StateStoreTransfer.class,id))
//                .onItem().ifNull().failWith(()->new RuntimeException("Not found"));
//    }
//
//    public Uni<StateStoreTransfer> save(StateStoreTransfer stateStoreTransfer){
//        return this.sessionFactory.withSession(session -> session.merge(stateStoreTransfer).onItem().call(session::flush));
//    }
}
