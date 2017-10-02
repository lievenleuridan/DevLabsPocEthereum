//package com.example.demo.util;
//
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.parser.PdfTextExtractor;
//
//import java.net.URISyntaxException;
//
///**
// * Created by vandebroeck.k on 2/10/2017.
// */
//public class PdfParser {
//
//    public static String getStringFromPdf(String filePath) throws URISyntaxException {
//        StringBuilder result = new StringBuilder();
//        try {
//            PdfReader reader = new PdfReader(filePath);
//            int n = reader.getNumberOfPages();
//            int i = 0;
//            while (i++ < n) {
//                String str = PdfTextExtractor.getTextFromPage(reader, i);
//                result.append(str);
//            }
//            reader.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return result.toString();
//    }
//
//}
