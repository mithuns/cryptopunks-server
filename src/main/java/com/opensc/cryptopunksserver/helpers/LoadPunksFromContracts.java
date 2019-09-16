package com.opensc.cryptopunksserver.helpers;


import com.opensc.cryptopunkserver.CryptoPunksContract;
import com.opensc.cryptopunksserver.model.CryptoPunkInfo;
import com.opensc.cryptopunksserver.repository.CryptoPunkInfoRepository;
import jnr.ffi.Struct;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class LoadPunksFromContracts {

    private final static String CONTRACT_ADDRESS = "0xb47e3cd837dDF8e4c57F05d70Ab865de6e193BBB";

    @Autowired
    private CryptoPunkInfoRepository cryptoPunkInfoRepository;

    @PostConstruct
    public void init() {
        //if below url complains about rate-limit.
        //final Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/d3674706e22f4229b09d66bde9a3c044"));
        final Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/498494c790964af8be6eafe6e2cdffec"));

        Web3ClientVersion web3ClientVersion = null;
        try {
            //web3ClientVersion = web3.web3ClientVersion().send();
            //System.out.println(web3ClientVersion.getWeb3ClientVersion());

            String filePath = getClass().getClassLoader().getResource("keystore/opensc-wallet.json").getPath();

            final Credentials credentials = WalletUtils.loadCredentials("opensc911", filePath);

            //System.out.println("Credentials loaded"+credentials.getAddress());

            final CryptoPunksContract contract = CryptoPunksContract.load(CONTRACT_ADDRESS,web3,credentials,new DefaultGasProvider());
            for(int i=1;i<=10000;i++){
                Tuple5<Boolean, BigInteger, String, BigInteger, String> tuple =
                        contract.punksOfferedForSale(new BigInteger(i+"")).send();
                if(tuple!=null){
                    //shows this punk is not for sale hence we delete from our local storage.
                    if(i<=8999 && !tuple.component1()){
                        if(cryptoPunkInfoRepository.existsById(i)){
                            cryptoPunkInfoRepository.deleteById(i);
                        }
                    }else if(i>8999 && tuple.component1()){     //we dont have this so save it.
                        final CryptoPunkInfo cryptoPunkInfo = new CryptoPunkInfo(i, null);
                        cryptoPunkInfoRepository.save(cryptoPunkInfo);
                    }
                    System.out.println("Processing punk:-"+i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final List<CryptoPunkInfo> allLeft = new ArrayList<>();
        cryptoPunkInfoRepository.findAll().forEach(entry->allLeft.add(entry));
        System.out.println("So many for sale:----"+allLeft.size());
    }
}