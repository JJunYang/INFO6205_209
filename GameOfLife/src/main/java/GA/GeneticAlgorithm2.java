/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GA;

import Model.Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author lhr
 */
public class GeneticAlgorithm2 {
    
    private Random random = new Random(); //random generator
 
    private int len=40; // chromosome length
 
    private int cellFitness=8; // the contribution of each survival cell to fitness
    private int generationFitness=20; //due
    
    private int scale; //population scale
    private int maxGeneration=200; 
    private float irate;//not needed
    private double mutationRate1=0.3; //mutation rate（the possibility of mutation of individual）
    private double mutationRate2=0.2; //mutation rate (the possibility of mutation of each gene of specific individual)
 
    private List<Chromosome> population=new ArrayList<Chromosome>();//last generation of population
    private double[] fitness = null; //fitness
    
    private double bestFitness=-1; //best fitness
    private Chromosome bestChromosome;// best chromosome

	class SortFitness implements Comparable<SortFitness> {
		int index;
		double fitness;

		public int compareTo(SortFitness c) {
			double cfitness = c.fitness;
			if (fitness > cfitness) {
				return -1;
			} else if (fitness < cfitness) {
				return 1;
			} else {
				return 0;
			}
		}
	}
    
    public void evolve(int scale,Random random) {
        this.scale = scale;
		this.random = random;
		initPopulation();
		for (int i = 0; i < maxGeneration; i++) {
			// calculate fitness
			calcFitness();
			// record best fitness
			recordBestFit();
			// population selecting
			select();
			// intersect process
			// intersect();
			// mutation
			mutation();
			// cells transforation
			transform();
			// output
			print(i);
		}

	}
    
    public void initPopulation(){
        fitness = new double[scale];
        for (int i=0; i<scale;i++){
            Chromosome g = new Chromosome(len,random);
            population.add(g);
        }
    }
    
    public void calcFitness(){
        int i=0;
        for (Chromosome g : population) {
            fitness[i++] = evaluate(g);
        }
    }
    
    public double evaluate(Chromosome g){

        int[] genotype=g.getGenotype();
        int cell=0;
        for(int i=0;i<genotype.length;i++) {
        	cell+=genotype[i];
        }
        if(cell==0)return 0;
        else if(g.isDead())return 0;
        else return cell*cellFitness+g.getGeneration()*generationFitness;
    }
    public void recordBestFit(){
        int i = 0;
        double totalFitness = 0.0;
        for (Chromosome g : population) {
            if (fitness[i] > bestFitness){
                bestFitness = fitness[i];
                bestChromosome = g;
                totalFitness+=fitness[i];
            }
            i++;
        }    
    }
    
	public void select() {
		SortFitness[] sortFitness = new SortFitness[scale];
		for (int i = 0; i < scale; i++) {
			sortFitness[i] = new SortFitness();
			sortFitness[i].index = i;
			sortFitness[i].fitness = fitness[i];
		}
		Arrays.sort(sortFitness);

		List<Chromosome> tmpPopulation = new ArrayList<Chromosome>();

		// reserve top 10% individuals
		int reserve = (int) (scale * 0.1);
		for (int i = 0; i < reserve; i++) {
			tmpPopulation.add(population.get(sortFitness[i].index));
			// randomize the later individuals joined in
			population.set(sortFitness[i].index, new Chromosome(len,random));
		}

		// then randomize 90% individuals
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < scale; i++) {
			list.add(i);
		}
		for (int i = reserve; i < scale; i++) {
			int selectid = list.remove((int) (list.size() * Math.random()));
			tmpPopulation.add(population.get(selectid));
		}
		population = tmpPopulation;
	}
    
    public void mutation(){
        for (Chromosome g : population){
            if(random.nextDouble()<mutationRate1) g.mutation(len, mutationRate2);
        }
    }
    
    public void transform(){
        for (Chromosome g : population){
            Matrix m = new Matrix(len, len, g.getGeneration(), g.getGene());
            m.transform();
            g.setGene(m.getMatrix());
            g.setGeneration(g.getGeneration()+1);
        }
    }
    
	public void print(int generation) {
		System.out.println("This is generation" + generation);
		System.out.println("The best fitness is" + bestFitness);
		System.out.print("The genotype is: ");
		for(int i=0;i<bestChromosome.getGenotype().length;i++) {
			System.out.print(bestChromosome.getGenotype()[i]);
		}
	}
    
    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    public int getLen() {
        return len;
    }

    
    
}
