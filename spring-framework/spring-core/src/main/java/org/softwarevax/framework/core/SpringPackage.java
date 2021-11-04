package org.softwarevax.framework.core;

import java.util.Set;

public interface SpringPackage {

    String[] getPackages();

    Set<Class<?>> getClasses(Class<?> clazz);
}
