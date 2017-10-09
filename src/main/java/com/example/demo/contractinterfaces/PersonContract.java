package com.example.demo.contractinterfaces;

import com.example.demo.entities.Person;

import java.util.concurrent.CompletableFuture;

/**
 * Created by vandebroeck.k on 9/10/2017.
 */
public interface PersonContract {

    Person findPerson(Integer value);

    CompletableFuture<Void> registerPerson(String firstName, String lastName, Integer personId);
}
