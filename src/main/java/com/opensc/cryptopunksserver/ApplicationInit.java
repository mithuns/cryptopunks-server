package com.opensc.cryptopunksserver;


import com.opensc.cryptopunkserver.CryptoPunksContract;
import jnr.ffi.Struct;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
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

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;


public class ApplicationInit {

    private final static String CONTRACT_ADDRESS = "0xb47e3cd837dDF8e4c57F05d70Ab865de6e193BBB";

    public void init() {
        //if below url complains about rate-limit.
        //final Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/d3674706e22f4229b09d66bde9a3c044"));
        final Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/498494c790964af8be6eafe6e2cdffec"));

        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().send();
            System.out.println(web3ClientVersion.getWeb3ClientVersion());

            String filePath = getClass().getClassLoader().getResource("keystore/opensc-wallet.json").getPath();

            final Credentials credentials = WalletUtils.loadCredentials("opensc911", filePath);

            System.out.println("Credentials loaded"+credentials.getAddress());

            CryptoPunksContract contract = CryptoPunksContract.load(CONTRACT_ADDRESS,web3,credentials,new DefaultGasProvider());
            String answer = contract.punkIndexToAddress(new BigInteger("12")).send();
            System.out.println(answer);
            CompletableFuture<Tuple5<Boolean, BigInteger, String, BigInteger, String>> future = contract.punksOfferedForSale(new BigInteger("4537")).sendAsync();
            Tuple5<Boolean, BigInteger, String, BigInteger, String> tuple = future.get();

            System.out.println("isForSale:-"  + tuple.component1());
            System.out.println("punkIndex:-"  + tuple.component2());
            System.out.println("seller:-"     + tuple.component3());
            System.out.println("minValue:-"   + tuple.component4());
            System.out.println("onlySellTo:-" + tuple.component5());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}