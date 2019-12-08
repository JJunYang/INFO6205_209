/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GA;

import Model.Matrix;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author lhr
 */

public class GeneticAlgorithm {
    public List<Chromosome> population=new ArrayList<Chromosome>();//存放所有的种群基因
    private int popSize; //种群规模N
    private int mutationSize; //变异基因规模
    private int chromoLength;//每条染色体数目m；
    private double mutationRate=0.01;//基因突变概率pm
    private int FitnessByCellNum = 8;//每个存活细胞对fitness函数的贡献
    private int FitnessByMaxGeneration = 5;//最大存活代数对fitness函数的贡献


    private int generation;//种群代数t
    private int iterNum=1000;//最大迭代次数
   // private int stepmax=3;//最长步长


    private Chromosome nowGenome;
    private Chromosome bestFit; //最好适应度对应的染色体
    private Chromosome iterBestFit;//全局最优染色体
    private double bestFitness;//种群最大适应度
    private double worstFitness;//种群最坏适应度
    private double averageFitness;


    private double x1;
    private double x2;
    private double y=0;

    public Chromosome getNowGenome() {
        return nowGenome;
    }


    public void setNowGenome(Chromosome nowGenome) {
        this.nowGenome = nowGenome;
    }

    public Chromosome getIterBestFit() {
        return iterBestFit;
    }

    public void setIterBestFit(Chromosome iterBestFit) {
        this.iterBestFit = iterBestFit;
    }

    public Chromosome getBestFit() {
        return bestFit;
    }

    public void setBestFit(Chromosome bestFit) {
        this.bestFit = bestFit;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }

    public double getWorstFitness() {
        return worstFitness;
    }

    public void setWorstFitness(double worstFitness) {
        this.worstFitness = worstFitness;
    }

    public double getTotalFitness() {
        return totalFitness;
    }

    public void setTotalFitness(double totalFitness) {
        this.totalFitness = totalFitness;
    }

    private double totalFitness;//种群总适应度
    private Random random=new Random();



    //构造Getting方法

    public GeneticAlgorithm(int popSize){
        this.popSize=popSize;

    }
    /*
    初始化种群
     */
    public void init(){

        for(int i=0;i<popSize;i++){
            Chromosome g=new Chromosome(100);
            Matrix m = new Matrix(g.getHeight(), g.getWidth(), generation, g.getGene());
            g.setFitness(getFitness(m));
            population.add(g);
        }
        caculteFitness();


    }
    /*
    计算种群适应度
     */
    public void caculteFitness(){

        bestFitness=population.get(0).getFitness();
        worstFitness=population.get(0).getFitness();
        totalFitness=0;
        for (Chromosome g:population) {
            //changeGene(g);
            setNowGenome(g);
            if(g.getFitness()>bestFitness){
                setBestFitness(g.getFitness());
                if(y<bestFitness){
                    y=g.getFitness();
                }
                setIterBestFit(g);

            }
            if(g.getFitness()<worstFitness){
                worstFitness=g.getFitness();
            }
            totalFitness+=g.getFitness();

        }
        averageFitness = totalFitness / popSize;
        //因为精度问题导致的平均值大于最好值，将平均值设置成最好值
        averageFitness = averageFitness > bestFitness ? bestFitness : averageFitness;


    }

    /*
    轮盘赌选择算法
     */
    public Chromosome getChromoRoulette(){
        double db=random.nextDouble();
        double randomFitness=db*totalFitness;
        Chromosome choseOne=null;
        double sum=0.0;
        for(int i=0;i<population.size();i++){
            choseOne=population.get(i);
            sum+=choseOne.getFitness();
            if(sum>=randomFitness){
                break;
            }
        }
        return choseOne;

    }

    /*
    进化算法
     */
    public void evolve() {
        List<Chromosome> childrenGenome = new ArrayList<Chromosome>();

        for (int j = 0; j < popSize/2; j++) {
            Chromosome g = getChromoRoulette();
            g.mutation(mutationSize, mutationRate);
            Matrix m = new Matrix(g.getHeight(), g.getWidth(), generation, g.getGene());
            m.transform();
            g.setGene(m.getMatrix());
            g.setFitness(getFitness(m));
            childrenGenome.add(g);
        }


        List<Chromosome> temGen = new ArrayList<Chromosome>();
        population.clear();

        for (int i = 0; i < popSize*0.2; i++) {
            int max = 0;

            for (Chromosome tempG : childrenGenome) {
                if (tempG.getFitness() > max) {
                    max = tempG.getFitness();
                    setBestFit(tempG);
                }

            }
            temGen.add(getBestFit());
            childrenGenome.remove(getBestFit());
            setBestFit(null);

        }


        population = childrenGenome;
        caculteFitness();

        while (temGen.size() < popSize) {
            Chromosome tp1 = getChromoRoulette();
            temGen.add(tp1);
        }

        /*
        while (temGen.size()<popSize){
            Chromosome tp1=new Chromosome(50);
            temGen.add(tp1);
        }
        */

        population = temGen;


        //重新计算种群适应度
        caculteFitness();


    }
    
    /*
    遗传算法GA流程
     */
    public void geneticAlgorithProcess(){
        generation=1;
        init();
        while(generation<iterNum){
            evolve();
            print();

            generation++;

        }
    }

    public int getFitness(Matrix matrix){
        int rows = matrix.getHeight();
        int cols =  matrix.getWidth();
        int countCell = 0;
        int generation  = matrix.getGeneration();
        int m[][] = matrix.getMatrix();
        for (int i=0; i < rows; i++){
            for (int j=0; j < cols; j++){
                if (m[i][j] == 1) countCell++;
            }
        } 
        return countCell*FitnessByCellNum+generation*FitnessByMaxGeneration;
    }
    public void print(){
        System.out.println("this is generation "+generation);
        System.out.println("the best fitness is "+y);
        System.out.println("-----------------------------------------------");
        if(generation==iterNum-1){
            for(int i=0;i<100;i++){
                for (int j=0;j<100;j++)
                if(iterBestFit.gene[i][j]==1){
                    System.out.printf("1");
                }
                else{
                    System.out.printf("0");
                }
                System.out.printf(" ");
            }
            System.out.println("");
        }
    }   
}
