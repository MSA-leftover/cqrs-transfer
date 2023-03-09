package com.cqrs.core.configuration;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@RequiredArgsConstructor
@EnableR2dbcRepositories(basePackages = "com.cqrs.command.dao.reactive")
@EnableTransactionManagement
@PropertySource("classpath:application.yaml")
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {
    private final Environment environment;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {//FIXME Bean Profile 에 따라서 다르게 주입 필요.
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(environment.getProperty("spring.r2dbc.url"));
        return ConnectionFactories.get(stringBuilder.toString());
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory){
        return new R2dbcTransactionManager(connectionFactory);
    }


}
