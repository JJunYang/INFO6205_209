/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.Matrix;
import java.util.Random;

/**
 *
 * @author lhr
 */
public class IntialNewMatrix {
    private static Matrix createNewMatrix(){
        Random random = new Random();
        double generateRate = 0.02;
        int rows = 1 + random.nextInt(100);
        int cols = 1 + random.nextInt(100);

        int num = 300;
        int matrix[][] = new int[rows][cols];
        
        for (int i=0; i < rows; i++){
            for (int j=0; j < cols; j++){
                if (random.nextDouble() > generateRate) matrix[i][j] = 1;
                        else matrix[i][j] = 0;
            }
        }
        
        Matrix cellMatrix = new Matrix(rows, cols, 0, matrix);
        return cellMatrix;
    }
}
