package io.mentusa.whouses.psi;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SourceFile {
    private final int id;
    private final String hash;
    private final List<String> locations = new ArrayList<>();

    public void addPath(Path path) {
        locations.add(path.toAbsolutePath().toString());
    }
}
