package io.mentusa.whouses.index;

import io.mentusa.whouses.psi.SourceFile;
import io.mentusa.whouses.psi.SourceRepo;
import io.mentusa.whouses.psi.WorkingFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileSearch implements JarProcessor {
    @Autowired
    private SourceRepo repo;

    public void searchJars(Path path) throws IOException {
        Files.walkFileTree(path, new JarSearch(this));
    }

    @SneakyThrows
    private SourceFile scanJar(Path path, String hash) {
        SourceFile sourceFile = new SourceFile(hash);
        new WorkingFile(path.toFile());
        return sourceFile;
    }

    @Override
    public void processJar(Path path, String hash) {
        SourceFile sourceFile = repo.findOneByHash(hash);
        if (sourceFile == null) {
            sourceFile = scanJar(path, hash);
        }
        sourceFile.addPath(path);
        repo.save(sourceFile);
    }
}
