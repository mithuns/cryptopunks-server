package com.opensc.cryptopunksserver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;


public class ApplicationInit {

    @Autowired
    private Environment environment;

    public void init() {
        String[] defaultProfiles = environment.getDefaultProfiles();
        Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/d3674706e22f4229b09d66bde9a3c044"));
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(web3ClientVersion.getWeb3ClientVersion());
    }
}
