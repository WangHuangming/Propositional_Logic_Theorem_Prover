package com.huangmingwang;

import java.util.HashSet;
import java.util.Set;

public class Clause {
    private Set<Literal> literals;
    
    public static final Clause FALSE = new Clause(new HashSet<>());

    public Clause() {
        literals = new HashSet<>();
    }

    public Clause(Set<Literal> literals) {
        this.literals = literals;
    }

    public void addLiteral(Literal literal) {
        this.literals.add(literal);
    }

    public Set<Literal> getLiterals() {
        return literals;
    }

    public Set<Clause> getNegation() {
        Set<Clause> negation = new HashSet<>();
        for (Literal literal : literals) {
            Clause clause = new Clause();
            clause.addLiteral(literal.getNegation());
            negation.add(clause);
        }
        return negation;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj instanceof Clause) {
            Clause other = (Clause) obj;
            if(literals.size() != other.literals.size()) {
                return false;
            }
            for (Literal literal : literals) {
                if (!other.literals.contains(literal)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return literals.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Literal literal : literals) {
            if (sb.length() > 0) {
                sb.append(" + ");
            }
            sb.append(literal.toString());
        }
        return sb.toString();
    }   
}
