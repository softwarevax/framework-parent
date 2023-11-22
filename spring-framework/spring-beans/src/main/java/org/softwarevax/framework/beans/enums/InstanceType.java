package org.softwarevax.framework.beans.enums;

public enum InstanceType {

    Constructor("构造器"), Attribute("属性"), Get("get方法");

    public String text;

    InstanceType(String text) {
        this.text = text;
    }
}
