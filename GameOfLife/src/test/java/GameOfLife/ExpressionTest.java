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
public class ExpressionTest {
    
    public ExpressionTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testExpression1() {
        Chromosome g = new Chromosome(5,new Random());
        int[][] a = {{1,0,1,1,1}, {1,1,0,1,0}, {0,0,1,0,1}, {1,0,1,1,1}, {0,1,0,1,0}};
        int[] b = {1,0,1,1,1,1,1,0,1,0,0,0,1,0,1,1,0,1,1,1,0,1,0,1,0};
        int[][] x = g.expression(b);
        assertTrue(isSame(a, x));
    }
    
    
    @Test
    public void testExpression2() {
        Chromosome g = new Chromosome(5, new Random());
        int[][] a = {{0,0,1,1,1}, {1,1,0,1,0}, {0,0,1,0,1}, {1,0,1,1,1}, {0,1,0,1,0}};
        int[] b = {1,0,1,1,1,1,1,0,1,0,0,0,1,0,1,1,0,1,1,1,0,1,0,1,0};
        int[][] x = g.expression(b);
        assertFalse(isSame(a, x));
    }
    
    
    
    public boolean isSame(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) return false;
        else for (int i=0; i < a.length; i++) {
            for (int j=0; j < a.length; j++) {
                if (a[i][j] != b[i][j]) return false; 
            }
        }
        return true;
    }
}
