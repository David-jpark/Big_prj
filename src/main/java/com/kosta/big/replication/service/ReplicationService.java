package com.kosta.big.replication.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

//@Service
public class ReplicationService {

    @Transactional
    public void writeOperation(){

    }

    @Transactional(readOnly = true)
    public void readOperation(){

    }
}
