package com.example.demo;

import org.adridadou.ethereum.EthjEthereumFacadeProvider;
import org.adridadou.ethereum.ethj.TestConfig;
import org.adridadou.ethereum.propeller.EthereumFacade;
import org.adridadou.ethereum.propeller.keystore.AccountProvider;
import org.adridadou.ethereum.propeller.solidity.SolidityContractDetails;
import org.adridadou.ethereum.propeller.values.EthAccount;
import org.adridadou.ethereum.propeller.values.EthAddress;
import org.adridadou.ethereum.propeller.values.SoliditySourceFile;
import org.ethereum.net.eth.handler.Eth;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import static org.adridadou.ethereum.propeller.values.EthValue.ether;

public class RealDolmenCoinTest {

    private EthAccount mainAccount = AccountProvider.fromSeed("This is a seed phrase");
    private EthAccount secondAccount = AccountProvider.fromSeed("This is a second phrase");

    @Test
    public void testContractDeployment() throws Exception {
        final EthereumFacade ethereum = fromTest();
        RealDolmenCoin myContract = publishAndMapContract(ethereum);
        myContract.send(secondAccount.getAddress(), 123456);
        System.out.println(myContract.getAddress().get());
        System.out.println(myContract.getBalance(myContract.getAddress().get()).get());
        System.out.println(myContract.getBalance(secondAccount.getAddress()).get());
    }

    private RealDolmenCoin publishAndMapContract(EthereumFacade ethereum) throws Exception {
        SolidityContractDetails compiledContract =
                ethereum.compile(SoliditySourceFile.from(new File("src/test/resources/contracts/RealDolmenCoinContract.sol")))
                        .findContract("RealDolmenCoinContract").get();

        CompletableFuture<EthAddress> futureAddress = ethereum.publishContract(compiledContract, mainAccount);
        return ethereum.createContractProxy(compiledContract, futureAddress.get(), mainAccount, RealDolmenCoinTest.RealDolmenCoin.class);
    }

    private EthereumFacade fromTest() {
        return EthjEthereumFacadeProvider.forTest(TestConfig.builder()
                .balance(mainAccount, ether(10000000))
                .build());
    }

    private interface RealDolmenCoin {
        CompletableFuture<Void> send(EthAddress receiver, int amount);
        CompletableFuture<EthAddress> getAddress();
        CompletableFuture<Integer> getBalance(EthAddress balanceAddress);
    }

}
