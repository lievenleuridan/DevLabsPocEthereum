package com.example.demo;

import com.example.demo.contractinterfaces.PersonContract;
import com.example.demo.services.ContractPublisherService;
import org.adridadou.ethereum.propeller.EthereumFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static com.example.demo.builders.EthereumBuilder.fromTest;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
        ContractPublisherService contractPublisherService = applicationContext.getBean(ContractPublisherService.class);

        final EthereumFacade ethereum = fromTest();
        PersonContract myContract = contractPublisherService.publishAndMapContract(ethereum);
        myContract.registerPerson("kwinten", "vandebroeck", 1);
        boolean loopingFlag = false;
        while (!loopingFlag) {
            if (myContract.findPerson(1).getFirstName().length() > 3 && myContract.findPerson(1).getLastName().length() > 3) {
                System.out.println(myContract.findPerson(1));
                loopingFlag = true;
            }
        }
    }


}
