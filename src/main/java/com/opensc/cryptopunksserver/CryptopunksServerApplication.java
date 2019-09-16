package com.opensc.cryptopunksserver;

import com.opensc.cryptopunksserver.helpers.EventSubscriber;
import com.opensc.cryptopunksserver.helpers.LoadPunksFromContracts;
import com.opensc.cryptopunksserver.helpers.LoadPunksFromJsonData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.Arrays;

@SpringBootApplication
public class CryptopunksServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptopunksServerApplication.class, args);
	}

    @Bean(initMethod = "init")
    public EventSubscriber bean3(){
	    return new EventSubscriber();
    }

    /*@Bean(initMethod="init")
    public LoadPunksFromJsonData bean2() {
        return new LoadPunksFromJsonData();
    }

    @Bean(initMethod="init")
    public LoadPunksFromContracts bean1() {
        return new LoadPunksFromContracts();
    }*/

}