package io.mentusa.whouses.psi;

import java.util.HashMap;
import java.util.Map;

public class ElementRepository {
    public static long id;
    public static final ElementRepository INSTANCE = new ElementRepository();

    private final Map<String, Element> elements = new HashMap<>();

    public Element ensureRegistered(Element element) {
        return elements.computeIfAbsent(element.identifier(), identifier -> {
            long longId = id++;
            element.setId(longId);
            return element;
        });
    }
}
