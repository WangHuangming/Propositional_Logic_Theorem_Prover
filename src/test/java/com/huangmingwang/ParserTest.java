package com.huangmingwang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void testReadFile() {
        Parser parser = new Parser();
        Set<String> lines = parser.readFile("test2.txt");
        assertNotNull(lines);
        assertEquals(3, lines.size());
        assertEquals(true, lines.contains("A + B"));
        assertEquals(true, lines.contains("~B + C"));
        assertEquals(true, lines.contains("~C"));
    }

    @Test
    public void testParse() {
        Parser parser = new Parser();
        Set<String> lines = new HashSet<>();
        lines.add("A + B");
        lines.add("~B + C");
        lines.add("~C");

        Set<Clause> clauses = parser.parse(lines);
        Clause clause1 = new Clause();
        clause1.addLiteral(new Literal('A', true));
        clause1.addLiteral(new Literal('B', true));

        Clause clause2 = new Clause();
        clause2.addLiteral(new Literal('B', false));
        clause2.addLiteral(new Literal('C', true));

        Clause clause3 = new Clause();
        clause3.addLiteral(new Literal('C', false)); 
        
        Clause clause4 = new Clause();
        clause4.addLiteral(new Literal('A', true));
        clause4.addLiteral(new Literal('B', true));
        
        assertNotNull(clauses);
        assertEquals(3, clauses.size());
        assertEquals(clause1, clause4);
        assertTrue(clauses.contains(clause1));
        assertTrue(clauses.contains(clause2));
        assertTrue(clauses.contains(clause3));

    }

    @Test
    public void testParseSingleLiteral() {
        Parser parser = new Parser();
        Set<String> lines = new HashSet<>();
        lines.add("A");

        Set<Clause> clauses = parser.parse(lines);
        assertNotNull(clauses);
        assertEquals(1, clauses.size());

        Clause clause = clauses.iterator().next();
        assertEquals(1, clause.getLiterals().size());
        Literal literal = clause.getLiterals().iterator().next();
        assertEquals("A", literal.toString());
        assertTrue(literal.isPositive());
    }

    @Test
    public void testParseNegatedLiteral() {
        Parser parser = new Parser();
        Set<String> lines = new HashSet<>();
        lines.add("~A");

        Set<Clause> clauses = parser.parse(lines);
        assertNotNull(clauses);
        assertEquals(1, clauses.size());

        Clause clause = clauses.iterator().next();
        assertEquals(1, clause.getLiterals().size());
        Literal literal = clause.getLiterals().iterator().next();
        assertEquals('A', literal.getName());
        assertFalse(literal.isPositive());
    }

    @Test
    public void testParseMultipleLiterals() {
        Parser parser = new Parser();
        Set<String> lines = new HashSet<>();
        lines.add("A + B + ~C");
        assertEquals(1, lines.size());

        Set<Clause> clauses = parser.parse(lines);
        assertEquals(1, clauses.size());

        Clause clause1 = new Clause();
        clause1.addLiteral(new Literal('A', true));
        clause1.addLiteral(new Literal('B', true));
        clause1.addLiteral(new Literal('C', false));
        
        // assertEquals("A + B", clause1.toString());

        Clause clause2 = clauses.iterator().next();

        assertEquals("~C + B + A", clause2.toString());

        assertEquals(3, clause2.getLiterals().size());
        assertTrue(clause1.equals(clause2));

        assertNotNull(clauses);
    }

    @Test
    public void testParseKB() {
        Parser parser = new Parser();
        KnowledgeBase kb = parser.parseKB("test3.txt");
        assertNotNull(kb);
        assertEquals(3, kb.getClauses().size());

        Clause clause1 = new Clause();
        clause1.addLiteral(new Literal('A', true));
        clause1.addLiteral(new Literal('B', true));
        clause1.addLiteral(new Literal('C', true));

        Clause clause2 = new Clause();
        clause2.addLiteral(new Literal('B', false));
        
        Clause clause3 = new Clause();
        clause3.addLiteral(new Literal('C', false));

        assertTrue(kb.getClauses().contains(clause1));
        assertTrue(kb.getClauses().contains(clause2));
        assertTrue(kb.getClauses().contains(clause3));
    }
}