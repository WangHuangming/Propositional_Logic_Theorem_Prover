package com.huangmingwang;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {

    // Method to read a file using ClassLoader to access resources in the class path
    public Set<String> readFile(String fileName) {
        Set<String> lines = new HashSet<>();
        try {
            //use ClassLoader to get the resource path
            Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
            List<String> allLines = Files.readAllLines(path);
            lines.addAll(allLines);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    //method to parse lines into a set of clauses
    public Set<Clause> parse(Set<String> lines) {
        Set<Clause> clauses = new HashSet<>();
        for (String line : lines) {
            // TODO: convert to CNF

            // convert to Clause
            String[] literals = line.split("\\s*\\+\\s*");
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
            clauses.add(clause);
        }
        return clauses;
    }

    public KnowledgeBase parseKB(String fileName) {
        Set<String> lines = readFile(fileName);
        Set<Clause> clauses = parse(lines);
        KnowledgeBase kb = new KnowledgeBase();
        kb.addClauses(clauses);
        return kb;
    }
}
