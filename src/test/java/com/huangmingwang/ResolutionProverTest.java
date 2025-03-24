package com.huangmingwang;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResolutionProverTest {

    @Test
    public void testMerge() {
        ResolutionProver prover = new ResolutionProver();
        
        Clause clause1 = new Clause();
        clause1.addLiteral(new Literal('A', true));
        clause1.addLiteral(new Literal('B', false));
        clause1.addLiteral(new Literal('D', false));
        
        Clause clause2 = new Clause();
        clause2.addLiteral(new Literal('B', true));
        clause2.addLiteral(new Literal('C', true));
        
        Literal l = new Literal('B', true);
        
        Clause result = prover.merge(clause1, clause2, l);
        
        Clause expected = new Clause();
        expected.addLiteral(new Literal('A', true));
        expected.addLiteral(new Literal('C', true));
        expected.addLiteral(new Literal('D', false));
        
        assertEquals(expected, result);
    }

    @Test
    public void testResolution() {
        ResolutionProver prover = new ResolutionProver();
        
        Clause clause1 = new Clause();
        clause1.addLiteral(new Literal('A', true));
        clause1.addLiteral(new Literal('B', false));
        clause1.addLiteral(new Literal('D', false));
        
        Clause clause2 = new Clause();
        clause2.addLiteral(new Literal('B', true));
        clause2.addLiteral(new Literal('C', true));
        
        Clause result = prover.resolution(clause1, clause2);
        
        Clause expected = new Clause();
        expected.addLiteral(new Literal('A', true));
        expected.addLiteral(new Literal('C', true));
        expected.addLiteral(new Literal('D', false));
        
        assertEquals(expected, result);

        Clause clause3 = new Clause();
        clause3.addLiteral(new Literal('A', true));

        Clause clause4 = new Clause();
        clause4.addLiteral(new Literal('A', false));

        result = prover.resolution(clause3, clause4);

        assertEquals(Clause.FALSE, result);
    }

    @Test
    public void testResolutionRefutation() {
        
        KnowledgeBase kb = new KnowledgeBase();
        
        Clause clause1 = new Clause();
        clause1.addLiteral(new Literal('A', true));
        clause1.addLiteral(new Literal('B', true));
        
        Clause clause2 = new Clause();
        clause2.addLiteral(new Literal('B', false));
        clause2.addLiteral(new Literal('C', true));
        
        Clause clause3 = new Clause();
        clause3.addLiteral(new Literal('C', false));
        
        kb.addClause(clause1);
        kb.addClause(clause2);
        kb.addClause(clause3);
        
        Clause theoremClause = new Clause();
        theoremClause.addLiteral(new Literal('A', true));
        
        ResolutionProver prover = new ResolutionProver();
        
        boolean result = prover.resolutionRefutation(kb, theoremClause);
        assertTrue(result);
    }
}
