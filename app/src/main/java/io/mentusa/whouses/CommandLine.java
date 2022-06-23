package io.mentusa.whouses;

import io.mentusa.whouses.index.FileSearch;
import io.mentusa.whouses.psi.ElementRepo;
import io.mentusa.whouses.psi.ElementRepository;
import java.io.File;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLine implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    @Autowired
    private ElementRepo elementRepo;
    @Autowired
    private FileSearch fileSearch;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Runtime.getRuntime().maxMemory());
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "index":
                fileSearch.searchJars(new File(args[1]).toPath());
                logger.info("Starting export");
                ElementRepository.INSTANCE.export(elementRepo);
                logger.info("Finished export");
                break;
        }
    }
}
