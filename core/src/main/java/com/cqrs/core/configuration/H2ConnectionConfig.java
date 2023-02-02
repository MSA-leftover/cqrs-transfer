package com.cqrs.core.configuration;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableR2dbcRepositories
@EnableTransactionManagement
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
@Profile("local")
@Configuration
public class H2ConnectionConfig extends AbstractR2dbcConfiguration {
    private final Environment environment;

    @Override
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder()
                .inMemory("test")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .username(environment.getProperty("spring.r2dbc.username"))
                .password(environment.getProperty("spring.r2dbc.password"))
                .build());//Closed 되도 유지.
    }
    @Bean
    public ConnectionFactoryInitializer h2DbInitializer(){
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        initializer.setConnectionFactory(connectionFactory());
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }
}
