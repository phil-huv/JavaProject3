package matrixmultiplication;

import java.util.concurrent.*;
import java.util.ArrayList;

public class MatrixMultiplication {
  public static void main(String[] args) {
    // Create a list
    final int N = 2000;
    double[][] matrix1 = new double[N][N];
    for (int i = 0; i < matrix1.length; i++)
      for (int j = 0; j < matrix1[i].length; j++)
        matrix1[i][j] = 1;

    double[][] matrix2 = new double[N][N];
    for (int i = 0; i < matrix2.length; i++)
      for (int j = 0; j < matrix2[i].length; j++)
        matrix2[i][j] = 1;
    
    long startTime = System.currentTimeMillis();
    double[][] result = multiplyMatrix(matrix1, matrix2);
    long endTime = System.currentTimeMillis();
    System.out.println("Sequential time is " + (endTime - startTime) 
      + " milliseconds"); 

  }
  
  public static double[][] multiplyMatrix(double[][] a, double[][] b) {
    double[][] result = new double[a.length][b[0].length];
    for (int i = 0; i < result.length; i++)
      for (int j = 0; j < result[0].length; j++) 
        for (int k = 0; k < a[0].length; k++)
          result[i][j] += a[i][k] * b[k][j];
    
    return result;
  }
  
}
