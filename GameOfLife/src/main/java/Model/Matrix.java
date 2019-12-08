/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author lhr
 */
public class Matrix {
    /**
     * 
     */
    private int height;

    /**
     * 
     */
    private int width;


    /**
     * total change times
     */
    private int generation=0;

    /**
     * status，1 for survived，0 for died
     */
    private int[][] matrix;
    
    public Matrix(){
        Random random = new Random();
        double generateRate = 0.2;
        int rows = 100;
        int cols = 100;
        int num = 300;
        int matrix[][] = new int[rows][cols];
        
        for (int i=0; i < rows; i++){
            for (int j=0; j < cols; j++){
                if (random.nextDouble() < generateRate) matrix[i][j] = 1;
                        else matrix[i][j] = 0;
            }
        }        
        
        this.height = rows;
        this.width = cols;
        this.generation = 0;
        this.matrix = matrix;
    }
    public Matrix(int height, int width, int generation, int[][] matrix) {
        this.height = height;
        this.width = width;
        this.generation = generation;
        this.matrix = matrix;
    }

    /**
     * change from last status to the next status
     * two rules:
     * 1. Next to 3 cells, the next status is survived
     * 2. Next to 2 cells, the next status doesn't change
     */
    public void transform(){
        int[][] nextMatrix=new int[height][width];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                nextMatrix[y][x]=0;
                int nearNum= findLifedNum(y,x);
                //等于3，则下一状态总是活
                if(nearNum==3){
                    nextMatrix[y][x]=1;
                }
                //等于2，则与上一状态一样
                else if(nearNum==2){
                    nextMatrix[y][x]=matrix[y][x];
                }
            }
        }
        matrix=nextMatrix;
        generation++;
    }



    /**
     * count survived cells surrounding
     * @param x 
     * @param y 
     * @return
     */
    public int findLifedNum(int y, int x){
        int num=0;
        //left
        if(x!=0){
            num+=matrix[y][x-1];
        }
        //top left
        if(x!=0&&y!=0){
            num+=matrix[y-1][x-1];
        }
        //top
        if(y!=0){
            num+=matrix[y-1][x];
        }
        //top right
        if(x!=width-1&&y!=0){
            num+=matrix[y-1][x+1];
        }
        //right
        if(x!=width-1){
            num+=matrix[y][x+1];
        }
        //bottom right
        if(x!=width-1&&y!=height-1){
            num+=matrix[y+1][x+1];
        }
        //bottom
        if(y!=height-1){
            num+=matrix[y+1][x];
        }
        //bottom left
        if(x!=0&&y!=height-1){
            num+=matrix[y+1][x-1];
        }
        return num;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            sb.append(Arrays.toString(matrix[i]) + "\n");
        }
        return sb.toString();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getGeneration() {
        return generation;
    }
 
}
