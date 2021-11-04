package org.softwarevax.framework.beans.factory;

public interface SingletonBeanRegistry {

    void registerSingleton(String key, Object bean);

    Object getSingleton(String key);
}
