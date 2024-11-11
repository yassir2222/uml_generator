package org.tp.uml_generator.Bean.enums;

public enum ClassType {
    ABSTRACT,
    INTERFACE,
    ENUM,
    CLASS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
