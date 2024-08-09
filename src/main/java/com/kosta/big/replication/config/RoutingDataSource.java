package com.kosta.big.replication.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.reactive.TransactionContext;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        //Context에 따라 마스터 또는 슬레이브 선택
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "slave" : "master";
    }
}
