package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.nio.file.Paths;

//import static com.example.demo.util.HashBuilder.createHash;
//import static com.example.demo.util.PdfParser.getStringFromPdf;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(DemoApplication.class, args);
//        System.out.println(createHash(getStringFromPdf(String.valueOf(Paths.get(DemoApplication.class.getResource("/Permissioned-distributed-ledgers.pdf").toURI())))));
//        System.out.println(createHash(getStringFromPdf(String.valueOf(Paths.get(DemoApplication.class.getResource("/Permissioned-distributed-ledgers - kopie.pdf").toURI())))));
    }


}
