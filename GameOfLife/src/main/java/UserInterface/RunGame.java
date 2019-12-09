/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import GA.Chromosome;
import GA.GeneticAlgorithm;
import Model.Matrix;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.*;
/**
 *
 * @author lhr
 */
public class RunGame {
    private static int maxGeneration = 1000;
    private static Matrix cellMatrix;
    private static JPanel gridPanel = new JPanel();
    private static JTextField[][] textMatrix;
    private static JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
    
    public static void main(String[] args){
        

        GameOfLifeJFrame jf = new GameOfLifeJFrame();
        jf.setSize(1000,1200);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//        buttonPanel.add(new JButton("Beging"));
//        buttonPanel.add(new JButton("Beging"));
//        buttonPanel.add(new JButton("Beging"));
//        buttonPanel.add(new JButton("Beging"));
        JTextField textField=new JTextField();
        

                
        buttonPanel.setBackground(Color.WHITE);
        jf.getContentPane().add("North",textField);
        
        //使用GA生成初始pattern
        GeneticAlgorithm ga = new GeneticAlgorithm();
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        try{
            String path = "./"+seed+".txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            String s = String.valueOf(seed+"L");
            bw.write(s);
            bw.close();
        }catch (Exception e){
            System.out.println("Have problems with saving seed");
        }
        ga.evolve(100,random);
        Chromosome g = ga.getBestChromosome();
        cellMatrix = new Matrix(ga.getLen(),ga.getLen(),0,g.getStartGene());
        //随机生成初始pattern 
        //cellMatrix = new Matrix();
        jf.setCellMatrix(cellMatrix);
        initGridLayout(jf);
        jf.setVisible(true);
        while (cellMatrix.getGeneration() < maxGeneration){
            cellMatrix.transform();
            showMatrix(jf);
            textField.setText("This is generation "+cellMatrix.getGeneration()+" !");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(RunGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            jf.setVisible(true);
        }
    }
    
    public static void initGridLayout(GameOfLifeJFrame jf) {
        int rows = cellMatrix.getHeight();
        int cols = cellMatrix.getWidth();
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                textMatrix[y][x] = text;
                gridPanel.add(text);
            }
        }
        jf.getContentPane().add("Center",gridPanel);
        //jf.setContentPane(gridPanel);
    }
    
    private static void showMatrix(GameOfLifeJFrame jf) {
        
        int rows = cellMatrix.getHeight();
        int cols = cellMatrix.getWidth();
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        int[][] matrix = cellMatrix.getMatrix();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                if (matrix[y][x] == 1) text.setBackground(Color.BLACK);
                    else text.setBackground(Color.WHITE);
                gridPanel.add(text);
            }
        }
        jf.getContentPane().add("Center",gridPanel);
        //jf.setContentPane(gridPanel);
    }
}
