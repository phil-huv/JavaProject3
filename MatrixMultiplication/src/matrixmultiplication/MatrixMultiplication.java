package matrixmultiplication;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
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
    
    long startTimeSequential = System.currentTimeMillis();
    double[][] result = multiplyMatrix(matrix1, matrix2);
    long endTimeSequential = System.currentTimeMillis();
    System.out.println("Sequential time is " + (endTimeSequential - startTimeSequential) 
      + " milliseconds"); 
    long startTimeParallel = System.currentTimeMillis();
    multiplyMatrixParallel(a, b); 
    long endTimeParallel = System.currentTimeMillis();
    System.out.println("Parallel time is " + (endTimeParallel - startTimeParallel) 
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


  public static void multiplyMatrixParallel(double[][] a, double[][] b) {
	double[][] c = new double[a.length][b[0].length];
	RecursiveAction mainTask = new multiplyTask(a, b, c);
	ForkJoinPool pool = new ForkJoinPool();
	pool.invoke(mainTask);
	
  }

  public static class multiplyTask extends RecursiveAction {
	static priavte double[][] a;	
	static priavte double[][] b;	
	static priavte double[][] c;	
	multiplyTask(double[][] a, double[][] b, double[][] c) {
		this.a = Arrays.copyOf(a, a.length);
		this.b = Arrays.copyOf(b, b.length);
		this.c = Arrays.copyOf(c, c.length);


	}
	List<RecursiveAction> list = new ArrayList<>();
	@Override
	protected void compute() {
		for(int i = 0; i < c.length; i++) {
			for(int j = 0; j < c[i].length; j++) {
				MultiplyIH multiplyIJ = new MultiplyIJ(i, j);
				list.add(multiplyIJ);
			}

		}
		invokeAll(list);
	}

	private static class MultiplyIJ extends RecursiveAction {
		private int row, col;
		MultiplyIJ(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		protected void compute() {
			int sum = 0;
			for(int i = 0, j = 0; i < row; i++, j++) {
				sum += a[row][i] * b[j][col];
			}
			c[row][col] = sum;
		}
	}


  }




  
}
