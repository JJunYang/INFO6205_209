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
     * 矩阵高度
     */
    private int height;

    /**
     * 矩阵宽度
     */
    private int width;


    /**
     * 总的变化次数
     */
    private int generation=0;

    /**
     * 矩阵状态，1表示活，0表示死
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
     * 上一个状态到下一个状态的转移
     * 根据规则可以总结得出两条规则:
     * 1. 对于周围活着的细胞为3的情况,下一个状态该细胞总是为活
     * 2. 对于周围活着的细胞为2的情况,下一个状态与上一状态相同
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
     * 统计每个细胞周围活着的个数
     * @param x 横坐标
     * @param y 纵坐标
     * @return
     */
    public int findLifedNum(int y, int x){
        int num=0;
        //左边
        if(x!=0){
            num+=matrix[y][x-1];
        }
        //左上角
        if(x!=0&&y!=0){
            num+=matrix[y-1][x-1];
        }
        //上边
        if(y!=0){
            num+=matrix[y-1][x];
        }
        //右上
        if(x!=width-1&&y!=0){
            num+=matrix[y-1][x+1];
        }
        //右边
        if(x!=width-1){
            num+=matrix[y][x+1];
        }
        //右下
        if(x!=width-1&&y!=height-1){
            num+=matrix[y+1][x+1];
        }
        //下边
        if(y!=height-1){
            num+=matrix[y+1][x];
        }
        //左下
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
