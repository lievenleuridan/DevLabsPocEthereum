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

        myContract.registerPerson("kwinten", "vandebroeck", 1).get();
        //.get() waits for the future to complete before moving on. This is a blocking io operation. Not really ideal
        System.out.println(myContract.findPerson(1));
    }
}