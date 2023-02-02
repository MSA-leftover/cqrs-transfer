package com.cqrs.core.configuration;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;

@Slf4j
@Configuration
@Profile("local")
public class H2ServerConfig {
    private Server webServer;
    @Value("${webclient.h2-console-port}")
    private Integer h2ConsolePort;

    @EventListener(ContextRefreshedEvent.class)
    public void run() throws SQLException {
        log.info("start h2 console at {}",this.h2ConsolePort);
        this.webServer = Server.createWebServer("-webPort", this.h2ConsolePort.toString());//h2 console Start
        this.webServer.start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop(){
        log.info("h2 console stopped.");
        this.webServer.stop();
    }
}
