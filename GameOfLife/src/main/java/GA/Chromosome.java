/*
  * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GA;

import Model.Matrix;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author lhr
 */
public class Chromosome {
    public int[][] gene;
    private int fitness;
    private int generation;
    private int height;
    private int width;
    private double rate = 0.01;
    private int[][] startGene;
    public int[]genotype;
    public int[]startgenotype;


	public int[] getGenotype() {
		return genotype;
	}

	public void setGenotype(int[] genotype) {
		this.genotype = genotype;
	}

	public int[] getStartgenotype() {
		return startgenotype;
	}

	public void setStartgenotype(int[] startgenotype) {
		this.startgenotype = startgenotype;
	}

	public int getFitness() {

		return fitness;
	}

    public void setFitness(int fitness) {

        this.fitness = fitness;
    }

    public int[][] getGene() {
        return gene;
    }

    public void setGene(int[][] gene) {
        this.gene = gene;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public int[][] getStartGene() {
        return startGene;
    }
    
    
    
    

	/*
    Construct chromosome
     */
    public Chromosome(int n){
        if (n<0){
            return;
        }
        initSize(n);
        Random random = new Random();
        for(int i=0;i<n;i++){
            for (int j=0; j<n;j++) {
                gene[i][j]=random.nextInt(2);
                startGene[i][j] = gene[i][j];
                genotype[i*n+j]=gene[i][j];
                startgenotype[i*n+j]=gene[i][j];
            }
        }
        
    }
    public Chromosome(){

    }

    public void initSize(int n){
        if (n<0){
            return;
        }
        this.width = n;
        this.height = n;
        this.gene=new int[n][n];
        this.startGene = new int[n][n];
        this.genotype=new int[n*n];
        this.startgenotype=new int[n*n];
    }

    /*
    mutation
     */
    
    public void mutation(int size,double rate){
        Random random=new Random();
        for(int i=0;i<size;i++){
        	for (int j=0;j<size;j++) {
        		if(random.nextDouble()<rate){
        			if (gene[i][j] == 0) gene[i][j] = 1;
        			else gene[i][j] = 0;
        		}
            }
        }
    }
    
//    public void print(){
//     for(int i=0;i<width;i++){
//             for (int j=0;j<height;j++)
//                if(gene[i][j]==1){
//                    System.out.printf("1");
//                }
//                else{
//                    System.out.printf("0");
//                }
//                System.out.printf(" ");
//            }    
//         System.out.println();
//    }
    
	public void print() {
		System.out.print(genotype);
	}
    
    public boolean isDead(){
        int [][]tmpGene = new int[height][width];
        for (int i=0; i<gene.length; i++)
            for (int j=0; j<gene[i].length;j++)
            tmpGene[i][j] = gene[i][j];
        Matrix m = new Matrix(height,width,generation,tmpGene);
        m.transform();
        tmpGene = m.getMatrix();
        try{
            for (int i=0; i<gene.length; i++)
                for (int j=0; j<gene[i].length;j++)
                    if (gene[i][j]!=tmpGene[i][j]) return false;
            return true;
        }catch (Exception e){}
        return false;
    }
}
