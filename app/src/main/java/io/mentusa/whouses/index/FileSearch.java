package io.mentusa.whouses.index;

import io.mentusa.whouses.psi.SourceFile;
import io.mentusa.whouses.psi.WorkingFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;

public class FileSearch implements JarProcessor {
    private static int id;
    private final Map<String, SourceFile> byHash = new HashMap<>();

    public void searchJars(Path path) throws IOException {
        Files.walkFileTree(path, new JarSearch(this));
    }

    @SneakyThrows
    private SourceFile scanJar(Path path, String hash) {
        SourceFile sourceFile = new SourceFile(id++, hash);
        new WorkingFile(path.toFile());
        return sourceFile;
    }

    @Override
    public void processJar(Path path, String hash) {
        byHash.computeIfAbsent(hash, key -> scanJar(path, hash)).addPath(path);
    }
}
