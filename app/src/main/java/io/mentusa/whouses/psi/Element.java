package io.mentusa.whouses.psi;

import io.mentusa.whouses.access.Access;
import io.mentusa.whouses.access.AccessType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.text.StringEscapeUtils;
import org.objectweb.asm.Type;

@Getter
@RequiredArgsConstructor
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
    private long id;
    private final String className;
    private final ElementType elementType;
    private final String name;
    private final List<Access> access = new ArrayList<>();

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
