package com.opensc.cryptopunksserver.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Arrays;

public class EventSubscriber {

    private final static String CONTRACT_ADDRESS = "0xb47e3cd837dDF8e4c57F05d70Ab865de6e193BBB";

    @Autowired
    private Web3j web3j;

    @PostConstruct
    public void init(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Event MY_EVENT = new Event("PunkNoLongerForSale",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>(false) {},
                                new TypeReference<Address>(false) {},
                                new TypeReference<Uint256>(false) {},
                                new TypeReference<Uint256>(true) {}
                        ));
                final String MY_EVENT_HASH = EventEncoder.encode(MY_EVENT);
                final EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST,CONTRACT_ADDRESS);
                filter.addSingleTopic(MY_EVENT_HASH);

                web3j.ethLogFlowable(filter).subscribe(log -> {
                    final String eventHash = log.getTopics().get(0);
                    if(eventHash.equals(MY_EVENT_HASH))
                    {
                        System.out.println(log);
                        Address arg1 = (Address) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(1), new TypeReference<Address>() {});
                        Address arg2 = (Address) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(2), new TypeReference<Address>() {});
                        Uint256 arg3 = (Uint256) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(3), new TypeReference<Uint256>(true) {});
                        Integer i=Integer.parseInt(new BigInteger(log.getData().substring(2),16).toString(10));
                        System.out.println("getBlockNumber:"+log.getBlockNumber());
                        System.out.println("arg1:"+arg1+"\n"+
                                "arg2:"+arg2+"\n"+
                                "arg3:"+arg3.getValue()+"\n"+
                                "i:"+i);
                    }
                });
            }
        }).start();
    }
}
