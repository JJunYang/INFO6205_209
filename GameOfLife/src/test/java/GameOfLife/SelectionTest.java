/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameOfLife;

import GA.Chromosome;
import GA.GeneticAlgorithm;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xiyuanjin
 */
public class SelectionTest {
    
    public SelectionTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testSelection() {
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setScale(100);
        ga.initPopulation();
        List<Chromosome> oldPopulation=new ArrayList<>();
        oldPopulation=ga.getPopulation();
        ga.select();
        int amount=0;
        for(Chromosome c:ga.getPopulation()){
            for(int i=0;i<oldPopulation.size();i++){
                if(isSame(c,oldPopulation.get(i))){
                    amount++;
                }
            }
        }
        double rate=amount*1.0/100;
        assertEquals(0.9, rate, 0.1);
    }
    
    public boolean isSame(Chromosome c1,Chromosome c2){
        for(int i=0;i<c1.getGenotype().length;i++){
            if(c1.getGenotype()[i]!=c2.getGenotype()[i])
                return false;
        }
        return true;
    }
}
