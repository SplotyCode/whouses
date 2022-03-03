package io.mentusa.whouses.index;

import com.google.common.hash.Hashing;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JarSearch implements FileVisitor<Path> {
    private final JarProcessor jarProcessor;

    private void visitJar(Path path) throws IOException {
        String hash = Hashing.murmur3_32().hashBytes(Files.readAllBytes(path)).toString();
        jarProcessor.processJar(path, hash);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".jar")) {
            visitJar(file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }
}
