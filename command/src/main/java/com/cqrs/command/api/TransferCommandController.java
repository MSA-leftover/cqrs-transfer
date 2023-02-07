package com.cqrs.command.api;

import com.cqrs.command.application.TransferService;
import com.cqrs.command.dto.DepositDTO;
import com.cqrs.command.dto.TransactionDTO;
import com.cqrs.command.dto.WithdrawDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/transfer")
public class TransferCommandController {
    private final TransferService transferService;

    @PostMapping(value = "/deposit", produces = MediaTypes.HAL_JSON_VALUE)
    public Mono<EntityModel<String>> deposit(
            @RequestBody DepositDTO depositDTO
    ){
        log.debug("deposit");
        TransferCommandController controller = methodOn(TransferCommandController.class);
        Mono<Link> selfLink = linkTo(controller.deposit(depositDTO)).withSelfRel().toMono();

        return Mono.zip(this.transferService.depositMoney(depositDTO),selfLink)
                .map(objects -> EntityModel.of(objects.getT1(), objects.getT2()));
    }

    @PostMapping(value = "/create", produces = MediaTypes.HAL_JSON_VALUE)
    public Mono<EntityModel<?>> transferCreate(
            @RequestBody TransactionDTO transactionDTO
    ){
        log.debug("transferCreate");
        TransferCommandController controller = methodOn(TransferCommandController.class);
        Mono<Link> selfLink = linkTo(controller.transferCreate(transactionDTO)).withSelfRel().toMono();

        Map<String, String> result = new ConcurrentHashMap<>();

        return Mono.zip(this.transferService.createTransaction(transactionDTO),selfLink)
                .map(objects -> EntityModel.of(objects.getT1(), objects.getT2()));
    }

    @PostMapping(value = "/withdraw", produces = MediaTypes.HAL_JSON_VALUE)
    public Mono<EntityModel<String>> withdraw(
            @RequestBody WithdrawDTO withdrawDTO
    ){
        TransferCommandController controller = methodOn(TransferCommandController.class);
        Mono<Link> selfLink = linkTo(controller.withdraw(withdrawDTO)).withSelfRel().toMono();

        return Mono.zip(this.transferService.withdrawMoney(withdrawDTO),selfLink)
                .map(objects -> EntityModel.of(objects.getT1(), objects.getT2()));
    }
}
