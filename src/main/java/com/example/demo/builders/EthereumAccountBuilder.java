package com.example.demo.builders;

import org.adridadou.ethereum.propeller.keystore.AccountProvider;
import org.adridadou.ethereum.propeller.values.EthAccount;

/**
 * Created by vandebroeck.k on 9/10/2017.
 */
public class EthereumAccountBuilder {

    private static EthAccount mainAccount = AccountProvider.fromSeed("This is a seed phrase");

    public static EthAccount accountBuilder() {
        return mainAccount;
    }

}
