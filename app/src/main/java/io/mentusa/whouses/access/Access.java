package io.mentusa.whouses.access;

import io.mentusa.whouses.psi.Element;
import io.mentusa.whouses.psi.ElementRepository;
import io.mentusa.whouses.psi.ElementType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Access {
    @Column(insertable= false, updatable = false)
    private long accessed;
    @Column
    private long accessor;
    @Column
    @Getter
    private AccessType type;
    @Getter
    @Column
    private int line;

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
