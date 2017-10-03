//package com.example.demo;
//
///**
// * Created by vandebroeck.k on 2/10/2017.
// */
//
//import org.adridadou.ethereum.EthereumFacade;
//import org.adridadou.ethereum.ethj.TestConfig;
//import org.adridadou.ethereum.ethj.provider.EthereumFacadeProvider;
//import org.adridadou.ethereum.ethj.provider.PrivateEthereumFacadeProvider;
//import org.adridadou.ethereum.keystore.AccountProvider;
//import org.adridadou.ethereum.values.*;
//import org.adridadou.exception.EthereumApiException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.File;
//import java.net.URISyntaxException;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
//import static org.adridadou.ethereum.ethj.provider.EthereumJConfigs.ropsten;
//import static org.adridadou.ethereum.ethj.provider.PrivateNetworkConfig.config;
//import static org.adridadou.ethereum.values.EthValue.ether;
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ExtraTest {
//
//    private final PrivateEthereumFacadeProvider privateNetwork = new PrivateEthereumFacadeProvider();
//    private EthAccount mainAccount = AccountProvider.fromSeed("cow");
//    private SoliditySource contractSource = SoliditySource.from(new File(this.getClass().getResource("/contracts/contract.sol").toURI()));
//
//    public ExtraTest() throws URISyntaxException {
//    }
//
//    @Test
//    public void main_example_how_the_lib_works() throws Exception {
//        // start ethereum here
//        final EthereumFacade ethereum = fromTest();
//        EthAddress address = publishAndMapContract(ethereum);
//        CompiledContract compiledContract = ethereum.compile(contractSource).get().get("myContract2");
//        MyContract2 myContract = ethereum.createContractProxy(compiledContract, address, mainAccount, MyContract2.class);
//
//        testMethodCalls(myContract, address, ethereum);
//
//        assertEquals(mainAccount.getAddress(), myContract.getOwner());
//    }
//
//    private EthereumFacade fromRopsten() {
//        EthereumFacadeProvider.Builder ethereumProvider = EthereumFacadeProvider.forNetwork(ropsten());
//        ethereumProvider.extendConfig().fastSync(true);
//        return ethereumProvider.create();
//    }
//
//    private EthereumFacade fromPrivateNetwork() {
//        return privateNetwork.create(config()
//                .reset(true)
//                .initialBalance(mainAccount, ether(10)));
//    }
//
//    private EthereumFacade fromTest() {
//        return EthereumFacadeProvider.forTest(TestConfig.builder()
//                .balance(mainAccount, ether(10000000))
//                .build());
//    }
//
//    private EthAddress publishAndMapContract(EthereumFacade ethereum) throws Exception {
//        ethereum.compile(SoliditySource.from(new File(this.getClass().getResource("/contracts/contract.sol").toURI()))).get();
//        CompiledContract compiledContract = ethereum.compile(contractSource).get().get("myContract2");
//        CompletableFuture<EthAddress> futureAddress = ethereum.publishContract(compiledContract, mainAccount);
//        return futureAddress.get();
//    }
//
//    private void testMethodCalls(MyContract2 myContract, EthAddress address, EthereumFacade ethereum) throws Exception {
//        assertEquals("", myContract.getI1());
//        System.out.println("*** calling contractSource myMethod ***");
//        Future<Integer> future = myContract.myMethod("this is a test");
//        assertEquals(12, future.get().intValue());
//        assertEquals("this is a test", myContract.getI1());
//        assertTrue(myContract.getT());
//
//        Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
//        assertArrayEquals(expected, myContract.getArray().toArray(new Integer[10]));
//
//        assertArrayEquals(expected, myContract.getSet().toArray(new Integer[10]));
//
//        assertEquals(new MyReturnType(true, "hello", 34), myContract.getM());
//
//        assertEquals("", myContract.getI2());
//        System.out.println("*** calling contractSource myMethod2 async ***");
//        myContract.myMethod2("async call").get();
//
//        myContract.myMethod3("async call").with(ether(150)).get();
//
//        assertEquals(ether(150), ethereum.getBalance(address));
//
//        assertEquals(mainAccount.getAddress(), myContract.getOwner());
//
//        assertEquals("async call", myContract.getI2());
//
//        assertEquals(EnumTest.VAL2, myContract.getEnumValue());
//
//        assertEquals(new Date(150_000), myContract.getInitTime(new Date(150_000)));
//        assertEquals(mainAccount.getAddress(), myContract.getAccountAddress(mainAccount));
//        try {
//            myContract.throwMe().get();
//            fail("the call should fail!");
//        } catch (final ExecutionException ex) {
//            assertEquals(EthereumApiException.class, ex.getCause().getClass());
//        }
//    }
//
//
//    private enum EnumTest {
//        VAL1, VAL2, VAL3
//    }
//
//    private interface MyContract2 {
//        CompletableFuture<Integer> myMethod(String value);
//
//        CompletableFuture<Void> myMethod2(String value);
//
//        Payable<Void> myMethod3(String value);
//
//        EnumTest getEnumValue();
//
//        String getI1();
//
//        String getI2();
//
//        boolean getT();
//
//        MyReturnType getM();
//
//        List<Integer> getArray();
//
//        Set<Integer> getSet();
//
//        CompletableFuture<Void> throwMe();
//
//        EthAddress getOwner();
//
//        Date getInitTime(final Date date);
//
//        EthAddress getAccountAddress(final EthAccount account);
//    }
//
//    public static class MyReturnType {
//        private final Boolean val1;
//        private final String val2;
//        private final Integer val3;
//
//        public MyReturnType(Boolean val1, String val2, Integer val3) {
//            this.val1 = val1;
//            this.val2 = val2;
//            this.val3 = val3;
//        }
//
//        @Override
//        public String toString() {
//            return "MyReturnType{" +
//                    "val1=" + val1 +
//                    ", val2='" + val2 + '\'' +
//                    ", val3=" + val3 +
//                    '}';
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            MyReturnType that = (MyReturnType) o;
//
//            if (val1 != null ? !val1.equals(that.val1) : that.val1 != null) return false;
//            if (val2 != null ? !val2.equals(that.val2) : that.val2 != null) return false;
//            return val3 != null ? val3.equals(that.val3) : that.val3 == null;
//
//        }
//
//        @Override
//        public int hashCode() {
//            int result = val1 != null ? val1.hashCode() : 0;
//            result = 31 * result + (val2 != null ? val2.hashCode() : 0);
//            result = 31 * result + (val3 != null ? val3.hashCode() : 0);
//            return result;
//        }
//    }
//}
