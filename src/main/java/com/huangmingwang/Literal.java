package com.huangmingwang;

import java.util.Objects;

public class Literal {
    private char name;
    private boolean isPositive;

    public Literal(char name) {
        this(name, true);
    }

    public Literal(char name, boolean isPositive) {
        this.name = name;
        this.isPositive = isPositive;
    }

    public char getName() {
        return name;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void negate() {
        isPositive = !isPositive;
    }

    public Literal getNegation() {
        return new Literal(name, !isPositive);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj instanceof Literal) {
            Literal other = (Literal) obj;
            return name == other.name && isPositive == other.isPositive;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isPositive);
    }

    @Override
    public String toString() {
        return (isPositive ? "" :"~") + name;
    }
}
