package io.mentusa.whouses.psi;

import io.mentusa.whouses.access.Access;
import io.mentusa.whouses.access.AccessType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.text.StringEscapeUtils;
import org.objectweb.asm.Type;

@Getter
@Entity
@NoArgsConstructor
@Table(indexes = @Index(columnList = "identifierName"))
public class Element {
    public static String formatMethodArguments(String method) {
        Type[] types = Type.getArgumentTypes(method);
        if (types.length == 0) {
            return "()";
        }
        StringBuilder name = new StringBuilder("(");
        name.append(types[0].getClassName());
        for (int i = 1; i < types.length; i++) {
            name.append(", ").append(types[i].getClassName());
        }
        return name.append(')').toString();
    }

    @Setter
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String className;
    @Column
    private ElementType elementType;
    @Column
    private String name;
    @Column
    private String identifierName;
    @ElementCollection
    @CollectionTable(name="element_source", joinColumns = @JoinColumn(name="id"))
    @Column(name="source")
    private Set<SourceFile> sourceFiles = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "access", joinColumns = @JoinColumn(name = "accessed"))
    private Set<Access> access = new HashSet<>();

    public Element(String className, ElementType elementType, String name) {
        this.className = className;
        this.elementType = elementType;
        this.name = name;
        identifierName = identifierName();
    }

    public void access(long accessor, AccessType type, int line) {
        access.add(new Access(id, accessor, type, line));
    }

    public String displayName() {
        if (elementType == ElementType.STRING) {
            return identifierName();
        }
        String formattedClassName = FormatTool.formatClassName(className.replace('/', '.'));
        return formattedClassName + '.' + name;
    }

    public String identifierName() {
        if (elementType == ElementType.STRING) {
            return "\"" + StringEscapeUtils.escapeJava(name) + "\"";
        }
        String formattedClassName = className.replace('/', '.');
        return formattedClassName + '.' + name;
    }
}
