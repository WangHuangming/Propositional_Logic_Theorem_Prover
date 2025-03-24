package com.huangmingwang;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class ResolutionProver {

    public Clause merge(Clause clause1, Clause clause2, Literal l) {
        Clause result = new Clause();
        for (Literal literal : clause1.getLiterals()) {
            if (!literal.equals(l) && !literal.equals(l.getNegation())) {
                result.addLiteral(literal);
            }
        }
        for (Literal literal : clause2.getLiterals()) {
            if (!literal.equals(l) && !literal.equals(l.getNegation())) {
                result.addLiteral(literal);
            }
        }
        return result;
    }

    public Clause resolution(Clause clause1, Clause clause2) {
        if (clause1.getLiterals().isEmpty() || clause2.getLiterals().isEmpty()) {
            return null;
        }
        Clause resolvent = null;
        boolean valid = false;
        Literal target = null;
        for (Literal l : clause1.getLiterals()) {
            if (clause2.getLiterals().contains(l.getNegation())) {
                if (target == null) {
                    target = l;
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            }
        }
        if (valid) {
            resolvent = merge(clause1, clause2, target);
            if (resolvent.getLiterals().isEmpty()) {
                return Clause.FALSE;
            }
        }
        return resolvent;
    }

    public boolean resolutionRefutation(KnowledgeBase kb, Clause theoremClause) {
        Set<Clause> clauses = new HashSet<>(kb.getClauses());
        clauses.addAll(theoremClause.getNegation());

        Queue<Clause[]> queue = new LinkedList<>();
        for (Clause clause1 : clauses) {
            for (Clause clause2 : clauses) {
                if (!clause1.equals(clause2)) {
                    queue.add(new Clause[]{clause1, clause2});
                }
            }
        }

        while (!queue.isEmpty()) {
            Clause[] pair = queue.poll();
            Clause resolvent = resolution(pair[0], pair[1]);
            if (resolvent != null) {
                if (resolvent.equals(Clause.FALSE)) {
                    System.out.println(pair[0] + " and " + pair[1] + " resolve FALSE");
                    return true;
                }
                if (!clauses.contains(resolvent)) {
                    System.out.println(pair[0] + " and " + pair[1] + " resolve " + resolvent);
                    clauses.add(resolvent);
                    for (Clause clause : clauses) {
                        if (!clause.equals(resolvent)) {
                            queue.add(new Clause[]{resolvent, clause});
                        }
                    }
                }
            }
        }
        return false;
    }

    public Clause parseTheorem(String clauseInString) {
        String[] literals = clauseInString.split("\\s*\\+\\s*");
        Clause clause = new Clause();
        for (String literal : literals) {
            char name = literal.charAt(0);
            boolean isPositive = true;
            if (literal.length() > 1 && literal.charAt(0) == '~') {
                name = literal.charAt(1);
                isPositive = false;
            }
            Literal l = new Literal(name, isPositive);
            clause.addLiteral(l);
        }
        return clause;
    }

    public static void main(String[] args) {
        // get user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please put your the knowledge base to resources");
        System.out.println("Please enter the name of your knowledge base(eg. test1.txt): ");
        String fileName = scanner.nextLine();
        System.out.println("-----------------------------");

        // read Kb and theorem
        KnowledgeBase kb = new Parser().parseKB(fileName);
        System.out.println("Please enter the theorem you want to prove: ");
        // TODO: support any input(now only support a Clause)
        String theoremInString = scanner.nextLine();
        System.out.println("-----------------------------");

        // print the knowledge base and theorem
        System.out.println("The knowledge base is: ");
        System.out.println(kb.toString());
        System.out.println("-----------------------------");
        System.out.println("The theorem is: ");
        System.out.println(theoremInString);
        System.out.println("-----------------------------");

        // prove the theorem
        ResolutionProver prover = new ResolutionProver();
        Clause theoremClause = prover.parseTheorem(theoremInString);
        boolean result = prover.resolutionRefutation(kb, theoremClause);
        System.out.println("-----------------------------");

        if (result) {
            System.out.println("The theorem is proved.");
        } else {
            System.out.println("The theorem cannot be proved.");
        }

        scanner.close();
    }
}

