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
    
    private Random random = new Random(); //随机数生成器
 
    private int len=40; // 染色体长度
 
    private int cellFitness=8; // 每一存活细胞的fitness贡献
    private int generationFitness=20; //细胞存活代数对fitness贡献
    
    private int scale; //种群规模
    private int maxGeneration=200; //最大代数
    private float irate; //交叉率（所有的个体都需要相互交叉的，这里的交叉率指交叉时每位交叉发生交叉的可能性）
    private double mutationRate1=0.3; //变异率（某个个体发生变异的可能性）
    private double mutationRate2=0.2; //对于确定发生变异的个体每位发生变异的可能性
 
    private List<Chromosome> population=new ArrayList<Chromosome>();//上一代种群
    private double[] fitness = null; //种群的适应度
 
    private double bestFitness=-1; //最优个体的价值
	private Chromosome bestChromosome;// 最优个体

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
    
    public void evolve(int scale) {
        this.scale = scale;
        initPopulation();
        for(int i = 0; i < maxGeneration; i++) {
            //计算种群适应度值
            calcFitness();
            //记录最优个体
            recordBestFit();
	   //进行种群选择
            select();
	   //进行交叉
	   //intersect();
	   //发生变异
            mutation();
            //细胞进行变化
            transform();
            //输出
            print(i);
        }
		
    }
    
    public void initPopulation(){
        fitness = new double[scale];
        for (int i=0; i<scale;i++){
            Chromosome g = new Chromosome(len);
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
//		int gene[][] = g.getGene();
//		int countCell = 0;
//		for (int i = 0; i < len; i++)
//			for (int j = 0; j < len; j++)
//				countCell += gene[i][j];
//		if (countCell == 0)
//			return 0;
//		else if (g.isDead())
//			return 0;
//		else
//			return countCell * cellFitness + g.getGeneration() * generationFitness;

        int[] genotype=g.getGenotype();
        int cell=0;
        for(int i=0;i<genotype.length;i++) {
        	cell+=genotype[i];
        }
        if(cell==0)return 0;
        else if(g.isDead())return 0;
        else return cell*cellFitness+g.getFitness()*generationFitness;
    }
    public void recordBestFit(){
        int i = 0;
        for (Chromosome g : population) {
            if (fitness[i] > bestFitness){
                bestFitness = fitness[i];
                bestChromosome = g;
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

		// 保留前10%的个体
		int reserve = (int) (scale * 0.1);
		for (int i = 0; i < reserve; i++) {
			tmpPopulation.add(population.get(sortFitness[i].index));
			// 将加入后的个体随机化
			population.set(sortFitness[i].index, new Chromosome(len));
		}

		// 再随机90%的个体出来
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
//		for (int i = 0; i < len; i++) {
//			for (int j = 0; j < len; j++)
//				if (bestChromosome.gene[i][j] == 1) {
//					System.out.printf("1");
//				} else {
//					System.out.printf("0");
//				}
//			System.out.printf(" ");
//		}
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
