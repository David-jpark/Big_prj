package com.kosta.big.replication.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
//@EnableJpaRepositories(basePackages = {"com.kosta.big.board.repository"}, entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "jpaTxManager")
public class DataSourceConfig {
    public static final String MASTER_DATASOURCE = "masterDataSource";
    public static final String SLAVE_DATASOURCE = "slaveDataSource";

    @Bean(MASTER_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(SLAVE_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    @DependsOn({MASTER_DATASOURCE, SLAVE_DATASOURCE})
    public DataSource routingDataSource(@Qualifier(MASTER_DATASOURCE) DataSource masterDataSource,
                                        @Qualifier(SLAVE_DATASOURCE) DataSource slaveDataSource) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>() {
            {
                put("master", masterDataSource);
                put("slave", slaveDataSource);
            }
        };
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);

        return routingDataSource;
    }

    @Bean
    @DependsOn("routingDataSource")
    public LazyConnectionDataSourceProxy dataSourceProxy(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
