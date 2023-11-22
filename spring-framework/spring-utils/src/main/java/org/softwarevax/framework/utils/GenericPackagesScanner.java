package org.softwarevax.framework.utils;

import java.util.HashSet;
import java.util.Set;

public class GenericPackagesScanner implements PackageScanner {

    private String[] packages;

    public GenericPackagesScanner() {
    }

    public GenericPackagesScanner(String[] packages) {
        this.packages = packages;
    }

    @Override
    public Set<Class<?>> getFullyQualifiedClassNameList() {
        if(ArrayUtils.isEmpty(packages)) {
            return new HashSet<>();
        }
        Set<Class<?>> classSet = new HashSet<>();
        if (ArrayUtils.isEmpty(this.packages)) {
            return classSet;
        }
        for(String pack : this.packages) {
            classSet.addAll(ClassUtils.getClassSet(pack));
        }
        return classSet;
    }

    @Override
    public void setPackages(String ... packages) {
        this.packages = packages;
    }
}
