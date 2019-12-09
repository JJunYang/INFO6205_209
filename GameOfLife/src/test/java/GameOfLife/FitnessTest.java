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
public class FitnessTest {
    
    public FitnessTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testEvaluate() {
        GeneticAlgorithm ga = new GeneticAlgorithm();
        Chromosome g = new Chromosome(5,new Random());
        int[][] a = {{1,0,1,1,1}, {1,1,0,1,0}, {0,0,1,0,1}, {1,0,1,1,1}, {0,1,0,1,0}};
        int[] b = {1,0,1,1,1,1,1,0,1,0,0,0,1,0,1,1,0,1,1,1,0,1,0,1,0};
        g.setFitness(0);
        g.setGene(a);
        g.setGenotype(b);
        double result = ga.evaluate(g);
        assertEquals(120, result, 0.0);
    }
}
