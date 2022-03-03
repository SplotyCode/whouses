package io.mentusa.whouses.psi;

import io.mentusa.whouses.access.Access;
import io.mentusa.whouses.access.AccessType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Element {
    @Setter
    private long id;
    private final String className;
    private final ElementType elementType;
    private final String name;
    private final List<Access> access = new ArrayList<>();

    public void access(long accessor, AccessType type, int line) {
        access.add(new Access(id, accessor, type, line));
    }

    public String displayName() {
        String formattedClassName = className.replace('/', '.');
        return formattedClassName + '.' + name;
    }
}
