package com.cqrs.core.configuration;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableR2dbcRepositories(basePackages = {"com.cqrs.command.dao.reactive","com.cqrs.query.dao"})
@EnableTransactionManagement
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
@Profile("local")
@Configuration
public class H2ConnectionConfig extends AbstractR2dbcConfiguration {
    private final Environment environment;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder()
                .inMemory("test")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .username(environment.getProperty("spring.r2dbc.username"))
                .password(environment.getProperty("spring.r2dbc.password"))
                .build());//Closed 되도 유지.
    }
    @Bean
    public ConnectionFactoryInitializer h2DbInitializer(ConnectionFactory connectionFactory){
        log.info("H2 database initialize.");
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory){
        return new R2dbcTransactionManager(connectionFactory);
    }
}
