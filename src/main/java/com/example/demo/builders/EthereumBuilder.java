package com.example.demo.builders;

import org.adridadou.ethereum.EthjEthereumFacadeProvider;
import org.adridadou.ethereum.ethj.TestConfig;
import org.adridadou.ethereum.propeller.EthereumFacade;

import static com.example.demo.builders.EthereumAccountBuilder.accountBuilder;
import static org.adridadou.ethereum.propeller.values.EthValue.ether;

/**
 * Created by vandebroeck.k on 9/10/2017.
 */
public class EthereumBuilder {

    public static EthereumFacade fromTest() {
        return EthjEthereumFacadeProvider.forTest(TestConfig.builder()
                .balance(accountBuilder(), ether(10000000))
                .build());
    }
}
