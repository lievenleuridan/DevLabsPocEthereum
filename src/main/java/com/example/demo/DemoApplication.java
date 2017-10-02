package com.example.demo;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static com.example.demo.util.HashBuilder.createHash;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println(createHash(getFile(String.valueOf(Paths.get(DemoApplication.class.getResource("/Permissioned-distributed-ledgers.pdf").toURI())))));
        System.out.println(createHash(getFile(String.valueOf(Paths.get(DemoApplication.class.getResource("/Permissioned-distributed-ledgers - kopie.pdf").toURI())))));
    }

    private static String getFile(String filePath) throws URISyntaxException {
        StringBuilder result = new StringBuilder();
        try {
            PdfReader reader = new PdfReader(filePath);
            int n = reader.getNumberOfPages();
            int i = 0;
            while (i++ < n) {
                String str = PdfTextExtractor.getTextFromPage(reader, i);
                result.append(str);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return result.toString();
    }
}
