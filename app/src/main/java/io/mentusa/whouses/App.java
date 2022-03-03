package io.mentusa.whouses;

import io.mentusa.whouses.index.FileSearch;
import io.mentusa.whouses.psi.ElementRepository;
import java.io.File;
import java.io.IOException;
import java.util.Locale;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws IOException {
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "index":
                new FileSearch().searchJars(new File(args[1]).toPath());
                break;
        }
        SpringApplication.run(App.class, args);
        System.out.println(ElementRepository.id + " elements found");
    }
}
