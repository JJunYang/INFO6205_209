/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameOfLife;

import GA.Chromosome;
import GA.GeneticAlgorithm;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xiyuanjin
 */
public class MutationTest {
    
    public MutationTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testInternalMutation() {
        int len = 10;
        int sameCount = 0;
        Chromosome g = new Chromosome(len,new Random());
        g.mutation(len, 0.2);
        for (int i=0; i < len; i++) {
            for (int j=0; j < len; j++) {
                if (g.getGene()[i][j] == g.getStartGene()[i][j]) {
                    sameCount ++;
                }
            }
        }
        
        assertFalse(len*len == sameCount);
    }
    
    @Test
    public void testMutation() {
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setScale(50);
        ga.initPopulation();
        ga.mutation();
        int len = ga.getLen();
        int count = 0;
        for (Chromosome g:ga.getPopulation()) {
            int same = 0;
            for (int i=0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (g.getGene()[i][j] == g.getStartGene()[i][j]) {
                        same ++;
                    }
                }
            }
            if (same != len*len) count ++;
        }
        double p = count*1.0/ga.getScale();
        
        assertEquals(0.3, p, 0.1);
        
    }
    
    
}
