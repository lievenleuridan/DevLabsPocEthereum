package com.example.demo;

import org.adridadou.ethereum.EthereumFacade;
import org.adridadou.ethereum.ethj.TestConfig;
import org.adridadou.ethereum.ethj.provider.EthereumFacadeProvider;
import org.adridadou.ethereum.keystore.AccountProvider;
import org.adridadou.ethereum.values.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.adridadou.ethereum.values.EthValue.ether;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private SoliditySource contractSource = SoliditySource.from(new File(this.getClass().getResource("/contracts/contract.sol").toURI()));

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
    private interface MyContract2 {
        CompletableFuture<Integer> myMethod(String value);

        CompletableFuture<Void> myMethod2(String value);

        Payable<Void> myMethod3(String value);

        EnumTest getEnumValue();

        String getI1();

        String getI2();

        boolean getT();

        MyReturnType getM();

        List<Integer> getArray();

        Set<Integer> getSet();

        CompletableFuture<Void> throwMe();

        EthAddress getOwner();

        Date getInitTime(final Date date);

        EthAddress getAccountAddress(final EthAccount account);
    }
    private enum EnumTest {
        VAL1, VAL2, VAL3
    }

    public static class MyReturnType {
        private final Boolean val1;
        private final String val2;
        private final Integer val3;

        public MyReturnType(Boolean val1, String val2, Integer val3) {
            this.val1 = val1;
            this.val2 = val2;
            this.val3 = val3;
        }

        @Override
        public String toString() {
            return "MyReturnType{" +
                    "val1=" + val1 +
                    ", val2='" + val2 + '\'' +
                    ", val3=" + val3 +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MyReturnType that = (MyReturnType) o;

            if (val1 != null ? !val1.equals(that.val1) : that.val1 != null) return false;
            if (val2 != null ? !val2.equals(that.val2) : that.val2 != null) return false;
            return val3 != null ? val3.equals(that.val3) : that.val3 == null;

        }

        @Override
        public int hashCode() {
            int result = val1 != null ? val1.hashCode() : 0;
            result = 31 * result + (val2 != null ? val2.hashCode() : 0);
            result = 31 * result + (val3 != null ? val3.hashCode() : 0);
            return result;
        }
    }
}
