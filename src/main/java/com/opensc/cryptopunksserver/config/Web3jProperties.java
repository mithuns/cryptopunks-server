package com.opensc.cryptopunksserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * web3j property container.
 */
@ConfigurationProperties(prefix = "web3j")
@Data
public class Web3jProperties {
    public static final String WEB3J_PREFIX = "web3j";

    private String clientAddress;
    private Boolean adminClient;
    private String networkId;
    private Long httpTimeoutSeconds;
}