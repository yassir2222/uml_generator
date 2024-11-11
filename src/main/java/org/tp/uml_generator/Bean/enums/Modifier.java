package org.tp.uml_generator.Bean.enums;

public enum Modifier {
    PUBLIC,
    DEFAULT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
