package com.opensc.cryptopunksserver.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class CryptoPunkService {

    @Autowired
    private Web3j web3j;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getClientVersion() throws IOException {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        return web3ClientVersion.getWeb3ClientVersion();
    }

    /*@PostConstruct
    public void listen() {
        web3j.transactionFlowable().subscribe(tx -> {
            logger.info("BlockHash: {}, BlockNumber:{},Input: {}, Creates: {}, Gas: {}", tx.getBlockHash(),
                    tx.getBlockNumber(),
                    tx.getInput(),tx.getCreates(),tx.getGas());
        });
    }*/
}
