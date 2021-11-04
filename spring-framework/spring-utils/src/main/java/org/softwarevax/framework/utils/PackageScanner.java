package org.softwarevax.framework.utils;

import java.util.Set;

public interface PackageScanner {

    void setPackages(String ... packages);

    Set<Class<?>> getFullyQualifiedClassNameList();

}
