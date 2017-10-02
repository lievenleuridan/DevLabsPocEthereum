package com.example.demo;

import org.adridadou.ethereum.EthereumFacade;
import org.adridadou.ethereum.ethj.TestConfig;
import org.adridadou.ethereum.ethj.provider.EthereumFacadeProvider;
import org.adridadou.ethereum.keystore.AccountProvider;
import org.adridadou.ethereum.values.CompiledContract;
import org.adridadou.ethereum.values.EthAccount;
import org.adridadou.ethereum.values.EthAddress;
import org.adridadou.ethereum.values.SoliditySource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.adridadou.ethereum.values.EthValue.ether;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private SoliditySource contractSource = SoliditySource.from(new File(this.getClass().getResource("/contract.sol").toURI()));

    public DemoApplicationTests() throws URISyntaxException {
    }


    @Test
    public void contextLoads() throws URISyntaxException, ExecutionException, InterruptedException {
        EthAccount mainAccount = AccountProvider.fromSeed("cow");
        EthereumFacade ethereum = EthereumFacadeProvider.forTest(TestConfig.builder()
                .balance(mainAccount, ether(10000000))
                .build());

//        SoliditySource contract = SoliditySource.from(new File(this.getClass().getResource("/contract.sol").toURI()));
//        CompletableFuture<Map<String, CompiledContract>> compiledContract = ethereum.compile(contract);

        ethereum.compile(SoliditySource.from(new File("src/test/resources/contract.sol"))).get();
        CompiledContract compiledContract = ethereum.compile(contractSource).get().get("myContract2");
        CompletableFuture<EthAddress> futureAddress = ethereum.publishContract(compiledContract, mainAccount);
        System.out.println( futureAddress.get());
        System.out.println(futureAddress.isDone());

    }

}
