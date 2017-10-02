package com.example.demo.controllers;

import com.example.demo.entities.Person;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vandebroeck.k on 2/10/2017.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    @GetMapping(value = "/{name}")
    public Person findPerson(@PathVariable String name) {
        return new Person().setFirstName("first name").setLastName(name);
    }
}
