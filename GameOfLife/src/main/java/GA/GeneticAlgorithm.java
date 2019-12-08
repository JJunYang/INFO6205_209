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
    private int popSize; //population size
    private int mutationSize; //mutation size
    private int chromoLength;//chromosome length
    private double mutationRate=0.01;//mutation rate
    private int FitnessByCellNum = 8;//the contribution of each survival cell to fitness
    private int FitnessByMaxGeneration = 5;//the contribution of max generation to fitness


    private int generation;
    private int iterNum=1000;//max iterator number
   // private int stepmax=3;//longest step


    private Chromosome nowGenome;
    private Chromosome bestFit; //chromosome that owns best fitness
    private Chromosome iterBestFit;//best chromosome
    private double bestFitness;
    private double worstFitness;
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

    private double totalFitness;
    private Random random=new Random();



    //Constructor 

    public GeneticAlgorithm(int popSize){
        this.popSize=popSize;

    }
    /*
    initialize population
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
    calculate fitness
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
        //if average value is greater than best value due to accuracy problem, then set average as best
        averageFitness = averageFitness > bestFitness ? bestFitness : averageFitness;


    }

    /*
     Roulette Algorithm
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
    evolution
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


        //recalculate fitness
        caculteFitness();


    }
    
    /*
    GA flow
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
