package io.mentusa.whouses.index;

import java.nio.file.Path;

public interface JarProcessor {
    void processJar(Path path, String hash);
}
