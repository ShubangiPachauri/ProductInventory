package com.demo.inventory.csvhelper;

import com.demo.inventory.model.Product;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

public class CSVHelper {

    public static boolean isCSVFormat(MultipartFile file) {
        return file.getContentType().equals("text/csv") ||
               file.getOriginalFilename().endsWith(".csv");
    }

    public static List<Product> csvToProducts(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {

            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(fileReader)
                    .withType(Product.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
