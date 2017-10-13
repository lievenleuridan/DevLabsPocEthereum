package com.example.demo.controllers;

import com.example.demo.contractinterfaces.PersonContract;
import com.example.demo.entities.Person;
import com.example.demo.services.ContractPublisherService;
import com.example.demo.util.HashBuilder;
import org.adridadou.ethereum.propeller.EthereumFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.builders.EthereumBuilder.fromTest;
import static java.util.stream.Collectors.toList;

/**
 * Created by vandebroeck.k on 2/10/2017.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final ContractPublisherService contractPublisherService;

    @Autowired
    public PersonController(ContractPublisherService contractPublisherService) {
        this.contractPublisherService = contractPublisherService;
    }

    @GetMapping(value = "/{name}/{lastName}")
    public Person findPerson(@PathVariable String name, @PathVariable String lastName) throws Exception {
        final EthereumFacade ethereum = fromTest();

        PersonContract myContract = contractPublisherService.publishAndMapContract(ethereum);
        myContract.registerPerson(name, lastName, 1).get();
        Person person = myContract.findPerson(1);
        return new Person().setFirstName(HashBuilder.createHash(person.getFirstName())).setLastName(HashBuilder.createHash(person.getLastName()));
    }
}