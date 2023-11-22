package org.softwarevax.framework.context;

import org.softwarevax.framework.beans.factory.BeanFactory;

import java.util.Set;

public interface ApplicationContext extends BeanFactory {

    String[] getPackages();
    boolean parseClazz(Set<Class<?>> clazz);

    boolean refresh(boolean finishRefresh);


}
