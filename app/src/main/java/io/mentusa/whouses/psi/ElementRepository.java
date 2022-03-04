package io.mentusa.whouses.psi;

import io.mentusa.whouses.access.Access;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ElementRepository {
    public static long id;
    public static final ElementRepository INSTANCE = new ElementRepository();

    private final Map<String, Element> elements = new ConcurrentHashMap<>();
    private final Map<Long, Element> byId = new HashMap<>();

    public Element ensureRegistered(Element element) {
        return elements.computeIfAbsent(element.identifierName(), identifier -> {
            long longId = id++;
            element.setId(longId);
            byId.put(longId, element);
            return element;
        });
    }

    public Set<Access> whoUses(String input) {
        Element element = elements.get(input);
        return element == null ? null : element.getAccess();
    }

    public Element byId(long accessor) {
        return byId.get(accessor);
    }

    public List<String> startsWith(String input) {
        return elements.keySet().stream()
            .filter(s -> s.startsWith(input))
            .limit(20)
            .collect(Collectors.toList());
    }

    public void export(ElementRepo elementRepo) {
        elementRepo.saveAll(elements.values());
    }
}
