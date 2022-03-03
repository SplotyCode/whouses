package io.mentusa.whouses;

import io.mentusa.whouses.index.FileSearch;
import io.mentusa.whouses.psi.ElementRepository;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class App {
    public static void main(String[] args) throws IOException {
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "index":
                new FileSearch().searchJars(new File(args[1]).toPath());
                break;
        }
        System.out.println(ElementRepository.id + " elements found");
    }
}
