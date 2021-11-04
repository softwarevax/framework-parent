package org.softwarevax.framework.beans.definition;

import java.util.List;

public interface DependOnDefinitionParser {

    void setClass(Class<?> clazz);

    List<DependOnDefinition> dependOnDefinitions();

}
