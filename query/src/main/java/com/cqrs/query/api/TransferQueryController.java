package com.cqrs.query.api;

import com.cqrs.query.application.TransferQueryService;
import com.cqrs.query.entity.Transfer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/transfer/query")
public class TransferQueryController {

    private final TransferQueryService service;

    @PostMapping(value = "/reset", produces = MediaTypes.HAL_JSON_VALUE)
    public Mono<EntityModel<String>> reset(){
        log.debug("resets");
        TransferQueryController controller = methodOn(TransferQueryController.class);
        Mono<Link> selfLink = linkTo(controller.reset()).withSelfRel().toMono();

        return Mono.zip(this.service.reset(),selfLink)
                .map(objects -> EntityModel.of("str", objects.getT2()));
    }

    @GetMapping(value = "/{account_id}",produces = MediaTypes.HAL_JSON_VALUE)
    public Mono<EntityModel<Transfer>> query(
            @PathVariable String account_id
    ){
        TransferQueryController controller = methodOn(TransferQueryController.class);
        Mono<Link> selfLink = linkTo(controller.query(account_id)).withSelfRel().toMono();
        log.debug("account query : {}",account_id);
        return Mono.zip(this.service.getTransferInfo(account_id),selfLink)
                .map(objects -> EntityModel.of(objects.getT1(), objects.getT2()));
    }

    @GetMapping(value = "/sub/{account_id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //ServerSentEvent 의 경우 장점이 명확해서, 굳이 이걸 안써도 됨.
//    public Flux<ServerSentEvent<EntityModel<Transfer>>> querySubscription(
    public Flux<Transfer> querySubscription(
            @PathVariable @NonNull String account_id
    ){
        TransferQueryController controller = methodOn(TransferQueryController.class);
//        Mono<Link> selfLink = linkTo(controller.querySubscription(account_id)).withSelfRel().toMono();
        log.debug("account subscription : {}",account_id);
        //FIXME EntityModel, Flux.zip 으로 묶으면 Eventstream 닫힘..
//        return Flux.zip(this.service.getTransferSubscription(account_id),selfLink)
//                .map(objects -> ServerSentEvent.builder(EntityModel.of(objects.getT1(), objects.getT2())).build());
        return this.service.getTransferSubscription(account_id);
    }
}
