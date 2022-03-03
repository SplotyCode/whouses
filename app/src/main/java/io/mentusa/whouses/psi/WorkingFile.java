package io.mentusa.whouses.psi;

import io.mentusa.whouses.index.IndexVisitor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class WorkingFile {
    private final JarFile jar;

    public WorkingFile(File file) throws IOException {
        jar = new JarFile(file);
        Stream<JarEntry> str = jar.stream();
        str.forEach(this::readClass);
        jar.close();
    }

    @SneakyThrows
    private void readClass(JarEntry entry) {
        String name = entry.getName();
        try (InputStream jis = jar.getInputStream(entry)){
            if (name.endsWith(".class")) {
                ClassReader reader = new ClassReader(jis);
                reader.accept(new ClassVisitor(Opcodes.ASM8) {
                    private String className;

                    @Override
                    public void visit(int version, int access, String name, String signature,
                                      String superName, String[] interfaces) {
                        className = name;
                        super.visit(version, access, name, signature, superName, interfaces);
                    }

                    @Override
                    public FieldVisitor visitField(int access, String name, String descriptor,
                                                   String signature, Object value) {
                        ElementRepository.INSTANCE.ensureRegistered(new Element(className, ElementType.FIELD, name));
                        return super.visitField(access, name, descriptor, signature, value);
                    }

                    @Override
                    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                                     String signature, String[] exceptions) {
                        String fullName = name + descriptor.substring(0, descriptor.indexOf(')') + 1);
                        Element method = ElementRepository.INSTANCE.ensureRegistered(new Element(className, ElementType.METHOD, fullName));
                        return new IndexVisitor(method, super.visitMethod(access, name, descriptor, signature, exceptions));
                    }
                }, ClassReader.SKIP_FRAMES);
            }
        }
    }
}
