package org.softwarevax.framework.utils;

import org.objectweb.asm.Type;
import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) throws ClassNotFoundException {
        return Class.forName(className, isInitialized, getClassLoader());
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        return loadClass(className, true);
    }

    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url == null) {
                    continue;
                }
                String protocol = url.getProtocol();
                if (protocol.equals("file")) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    addClass(classSet, packagePath, packageName);
                } else if (protocol.equals("jar")) {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    if (jarURLConnection == null) {
                        continue;
                    }
                    JarFile jarFile = jarURLConnection.getJarFile();
                    if (jarFile == null) {
                        continue;
                    }
                    Enumeration<JarEntry> jarEntries = jarFile.entries();
                    while (jarEntries.hasMoreElements()) {
                        JarEntry jarEntry = jarEntries.nextElement();
                        String jarEntryName = jarEntry.getName();
                        if (!jarEntryName.endsWith(".class")) {
                            continue;
                        }
                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                        doAddClass(classSet, className);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) throws ClassNotFoundException {
        File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (!StringUtils.isBlank(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (!StringUtils.isBlank(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (!StringUtils.isBlank(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) throws ClassNotFoundException {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

    /**
     * 获取类被annotations注解的属性
     * @param clazz
     * @param annotations
     * @return
     */
    public static List<Field> getMarkedField(Class<?> clazz, Class<?> ... annotations) {
        Assert.notNull(clazz, "class cannot be null");
        Assert.notEmpty(annotations, "annotation cannot be null");
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        for(Field field : declaredFields) {
            if(!AnnotationUtils.containsAnyAnnotation(field, annotations)) {
                continue;
            }
            fields.add(field);
        }
        return fields;
    }

    public static List<Method> getMarkedMethod(Class<?> clazz, Class<?> ... annotations) {
        Assert.notNull(clazz, "class cannot be null");
        Assert.notEmpty(annotations, "annotation cannot be null");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> methods = new ArrayList<>();
        for(Method method : declaredMethods) {
            if(!AnnotationUtils.containsAnyAnnotation(method, annotations)) {
                continue;
            }
            methods.add(method);
        }
        return methods;
    }

    public static Class<?> getType(AnnotatedElement element) {
        if(element instanceof Parameter) {
            return ((Parameter) element).getType();
        }
        if(element instanceof Field) {
            return ((Field) element).getType();
        }
        return null;
    }

    /**
     * 获取方法参数名
     * @param method
     * @return
     */
    public static List<String> getMethodParameters(Method method) {
        List<String> parameters = new ArrayList<>();
        try {
            final String methodName = method.getName();
            final Class<?>[] methodParameterTypes = method.getParameterTypes();
            final int methodParameterCount = methodParameterTypes.length;
            final String className = method.getDeclaringClass().getName();
            final boolean isStatic = Modifier.isStatic(method.getModifiers());

            ClassReader cr = new ClassReader(className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cr.accept(new ClassVisitor(Opcodes.ASM9, cw) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                    final Type[] argTypes = Type.getArgumentTypes(desc);
                    //参数类型不一致
                    if (!methodName.equals(name) || !matchTypes(argTypes, methodParameterTypes)) {
                        return mv;
                    }
                    return new MethodVisitor(Opcodes.ASM9, mv) {
                        @Override
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                            //如果是静态方法，第一个参数就是方法参数，非静态方法，则第一个参数是 this ,然后才是方法的参数
                            int methodParameterIndex = isStatic ? index : index - 1;
                            if (0 <= methodParameterIndex && methodParameterIndex < methodParameterCount) {
                                parameters.add(methodParameterIndex, name);
                            }
                            super.visitLocalVariable(name, desc, signature, start, end, index);
                        }
                    };
                }
            }, 0);
            return parameters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parameters;
    }

    /**
     * 比较参数是否一致
     */
    private static boolean matchTypes(Type[] types, Class<?>[] parameterTypes) {
        if (types.length != parameterTypes.length) {
            return false;
        }
        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(parameterTypes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取所有继承的父类，多层级
     */
    private static void getExtendClass(List<Class<?>> list, Class<?> className) {
        if(Objects.isNull(className) || className.getSuperclass().equals(Object.class)) {
            return;
        }
        Class<?> superclass = className.getSuperclass();
        list.add(superclass);
        getExtendClass(list, superclass);
    }

    /**
     * 获取所有的接口
     */
    private static void getInterfaces(List<Class<?>> list, Class<?> className) {
        if(Objects.isNull(className)) {
            return;
        }
        Class<?>[] interfaces = className.getInterfaces();
        list.addAll(Arrays.asList(interfaces));
        for (Class<?> clazz : interfaces) {
            getInterfaces(list, clazz);
        }
    }

    /**
     * 获取所有接口及父类的接口，多层级
     */
    public static List<Class<?>> getInterfaces(Class<?> className) {
        if(Objects.isNull(className)) {
            return null;
        }
        List<Class<?>> interfaces = new ArrayList<>();
        interfaces.addAll(Arrays.asList(className.getInterfaces()));
        List<Class<?>> list = new ArrayList<>();
        getExtendClass(list, className);
        for (Class<?> clazz : list) {
            List<Class<?>> inter = new ArrayList<>();
            getInterfaces(inter, clazz);
            interfaces.addAll(inter);
        }
        return interfaces;
    }
}
