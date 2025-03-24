package com.huangmingwang;

import java.util.HashSet;
import java.util.Set;

public class KnowledgeBase {
    private Set<Clause> clauses;

    public KnowledgeBase() {
        clauses = new HashSet<>();
    }

    public void addClause(Clause clause) {
        this.clauses.add(clause);
    }

    public void addClauses(Set<Clause> clauses) {
        this.clauses.addAll(clauses);
    }

    public Set<Clause> getClauses() {
        return clauses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Clause clause : clauses) {
            sb.append(clause.toString()).append("\n");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); 
        }
        return sb.toString();
    }
}
