package com.example.demo;

import org.adridadou.ethereum.EthjEthereumFacadeProvider;
import org.adridadou.ethereum.ethj.TestConfig;
import org.adridadou.ethereum.propeller.EthereumFacade;
import org.adridadou.ethereum.propeller.keystore.AccountProvider;
import org.adridadou.ethereum.propeller.solidity.SolidityContractDetails;
import org.adridadou.ethereum.propeller.values.EthAccount;
import org.adridadou.ethereum.propeller.values.EthAddress;
import org.adridadou.ethereum.propeller.values.SoliditySourceFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import static org.adridadou.ethereum.propeller.values.EthValue.ether;

/**
 * Created by vandebroeck.k on 6/10/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonContractTests {

    private EthAccount mainAccount = AccountProvider.fromSeed("cow");

    @Test
    public void testContractDeployment() throws Exception {
        final EthereumFacade ethereum = fromTest();
        PersonContractTests.PersonContract myContract = publishAndMapContract(ethereum);
        myContract.registerPerson("kwinten", "vandebroeck", 1);
        boolean loopingFlag = false;
        while (!loopingFlag) {
            if (myContract.findPerson(1).firstName.length() > 3 && myContract.findPerson(1).lastName.length() > 3) {
                System.out.println(myContract.findPerson(1));
                loopingFlag = true;
            }
        }
//        assertEquals(mainAccount.getAddress(), myContract.getOwner());
    }

    private PersonContractTests.PersonContract publishAndMapContract(EthereumFacade ethereum) throws Exception {
        SolidityContractDetails compiledContract =
                ethereum.compile(SoliditySourceFile.from(new File("src/test/resources/contracts/PersonContract.sol"))).findContract("PersonContract").get();

        CompletableFuture<EthAddress> futureAddress = ethereum.publishContract(compiledContract, mainAccount);
        return ethereum.createContractProxy(compiledContract, futureAddress.get(), mainAccount, PersonContractTests.PersonContract.class);
    }

    private EthereumFacade fromTest() {
        return EthjEthereumFacadeProvider.forTest(TestConfig.builder()
                .balance(mainAccount, ether(10000000))
                .build());
    }

    private interface PersonContract {
        Person findPerson(Integer value);

        CompletableFuture<Void> registerPerson(String firstName, String lastName, Integer personId);
    }

    public static class Person {
        private final String firstName;
        private final String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;

            Person person = (Person) o;

            return (firstName != null ? firstName.equals(person.firstName) : person.firstName == null)
                    && (lastName != null ? lastName.equals(person.lastName) : person.lastName == null);
        }

        @Override
        public int hashCode() {
            int result = firstName != null ? firstName.hashCode() : 0;
            result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
            return result;
        }
    }
}
