package org.softwarevax.framework.beans.definition;

import org.softwarevax.framework.beans.GlobalConstants;
import org.softwarevax.framework.utils.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 类解析器(不支持构造器注入: 不支持循环依赖)
 */
public class GenericDependOnDefinitionParser implements DependOnDefinitionParser {

    private List<DependOnDefinition> dependOnDefinitions;

    private Class<?> clazz;

    //private Constructor[] constructors;

    private Field[] fields;

    private Method[] methods;

    @Override
    public void setClass(Class<?> clazz) {
        this.clazz = clazz;
        //this.constructors = clazz.getDeclaredConstructors();
        this.fields = clazz.getDeclaredFields();
        this.methods = clazz.getMethods();
        this.dependOnDefinitions = new ArrayList<>();
        //parseAnnotatedElement(constructors);
        parseAnnotatedElement(fields);
        parseAnnotatedElement(methods);
    }

    @Override
    public List<DependOnDefinition> dependOnDefinitions() {
        return this.dependOnDefinitions;
    }

    private void parseAnnotatedElement(AnnotatedElement[] annotatedElements) {
        for(AnnotatedElement annotated : annotatedElements) {
            if(annotated instanceof Method) {
                this.parseAnnotatedElement(((Method) annotated).getParameters());
            }
            /*if(annotated instanceof Constructor) {
                this.parseAnnotatedElement(((Constructor) annotated).getParameters());
            }*/
            if(!AnnotationUtils.containsAnyAnnotation(annotated, GlobalConstants.AUTOWIRED_ANNOTATIONS)) {
                continue;
            }
            dependOnDefinitions.add(new GenericDependOnDefinition(this.clazz, annotated));
        }
    }
}
