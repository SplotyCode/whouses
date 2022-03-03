package io.mentusa.whouses.access;

import io.mentusa.whouses.psi.Element;
import io.mentusa.whouses.psi.ElementRepository;
import io.mentusa.whouses.psi.ElementType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Access {
    private final long accessed;
    private final long accessor;
    @Getter
    private final AccessType type;
    @Getter
    private final int line;

    @Getter
    @RequiredArgsConstructor
    public static class Accessor {
        private final String displayName;
        private final ElementType elementType;
    }

    public Accessor getAccessor() {
        Element element = ElementRepository.INSTANCE.byId(accessor);
        return new Accessor(element.displayName(), element.getElementType());
    }
}
