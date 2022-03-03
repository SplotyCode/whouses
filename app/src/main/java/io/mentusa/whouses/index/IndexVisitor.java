package io.mentusa.whouses.index;

import io.mentusa.whouses.access.AccessType;
import io.mentusa.whouses.psi.Element;
import io.mentusa.whouses.psi.ElementRepository;
import io.mentusa.whouses.psi.ElementType;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class IndexVisitor extends MethodVisitor {
    private final Element method;
    private int line;

    public IndexVisitor(Element method, MethodVisitor classVisitor) {
        super(Opcodes.ASM8, classVisitor);
        this.method = method;
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        this.line = line;
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        method.access(ElementRepository.INSTANCE.ensureRegistered(new Element(owner, ElementType.FIELD, name)).getId(), AccessType.ACCESS_FILED, line);
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor,
                                boolean isInterface) {
        String fullName = name + descriptor.substring(0, descriptor.indexOf(')') + 1);
        Element method = ElementRepository.INSTANCE.ensureRegistered(new Element(owner, ElementType.METHOD, fullName));
        this.method.access(method.getId(), AccessType.INVOKE, line);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle,
                                       Object... bootstrapMethodArguments) {
        //TODO
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle,
            bootstrapMethodArguments);
    }
}