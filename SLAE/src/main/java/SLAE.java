import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class SLAE {
    public static double[][] matrix;
    private static double[] rightSideOfEquation;
    private static double[] x;
    private int numberOfThreads=3;
    private int width;
    private CountDownLatch countDownLatch;

    public double getX(int index) {
        try {
            if ((index >= x.length) || (index < 0))
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return x[index];
    }

    public void setRightSide(int index, double value) {
        try {
            if ((index >= rightSideOfEquation.length) || (index < 0))
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        rightSideOfEquation[index] = value;
    }

    public double getRightSide(int index) {
        try {
            if ((index >= rightSideOfEquation.length) || (index < 0))
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return rightSideOfEquation[index];
    }

    public void setX(int index, double value) {
        try {
            if ((index >= x.length) || (index < 0))
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        x[index] = value;
    }


    public int getWidth() {
        return width;
    }

    SLAE(){
        int n=12;
        matrix = new double [n][n];
        rightSideOfEquation=new double[n];
        for (int i = 0; i < n; i++) {
            matrix[i][i] = 20 * Math.random()+0.1;
            rightSideOfEquation[i]=20 * Math.random();
            if (i != 0)
                matrix[i][i - 1] = 20 * Math.random()+0.1;
            if(i!=n-1)
                matrix[i][i + 1] = 20 * Math.random()+0.1;
        }

        width = matrix.length/numberOfThreads;
        countDownLatch=new CountDownLatch(numberOfThreads+1);
        x=new double[matrix.length];
    }

    SLAE(double[][] _matrix, double[] coef, int _numberOfThreads){
        try{
            if((_numberOfThreads<0)||(_numberOfThreads>_matrix.length/2))
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException|NullPointerException e){
            e.printStackTrace();
        }
        matrix=new double[_matrix.length][_matrix.length];
        for (int i = 0; i < _matrix.length ; i++)
            for (int j = 0; j < _matrix.length; j++)
                matrix[i][j]=_matrix[i][j];
        numberOfThreads=_numberOfThreads;
        rightSideOfEquation=coef.clone();
        width = matrix.length/numberOfThreads;
        countDownLatch=new CountDownLatch(numberOfThreads+1);
        x=new double[matrix.length];
    }

    SLAE(String fileName){

        File file=new File(fileName);
        try {
            Scanner scan=new Scanner(file);
            int n = scan.nextInt();
            matrix = new double [n][n];
            rightSideOfEquation=new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n && scan.hasNextDouble(); j++)
                    matrix[i][j] = scan.nextDouble();
                rightSideOfEquation[i]=scan.nextDouble();
            }
        }catch (FileNotFoundException|NullPointerException e){
            e.printStackTrace();
        }
        if(matrix.length/3<3)
            numberOfThreads=2;

        width = matrix.length/numberOfThreads;
        countDownLatch=new CountDownLatch(numberOfThreads+1);
        x=new double[matrix.length];

    }

    public void sweepMethod(double[][] matrix, int numberOfThreads){

        double[] bottomDiagonal=new double[2*numberOfThreads];
        double[] topDiagonal=new double[2*numberOfThreads];
        double[] mainDiagonal=new double[2*numberOfThreads];
        double[] rightSide=new double[2*numberOfThreads];

        double[][] intermediateMatr=new double[2*numberOfThreads][2*numberOfThreads];
        for(int i=0;i<numberOfThreads;i++){
            int width=matrix.length/numberOfThreads;
            if(i==0){
                rightSide[0]=rightSideOfEquation[0];
                rightSide[1]=rightSideOfEquation[width-1];
                bottomDiagonal[0]=bottomDiagonal[1]=0;
                intermediateMatr[0][0]=mainDiagonal[0]=matrix[0][0];
                intermediateMatr[0][1]=topDiagonal[0]=matrix[0][width];

                intermediateMatr[1][1]=mainDiagonal[1]=matrix[width-1][width-1];
                intermediateMatr[1][2]=topDiagonal[1]=matrix[width-1][width];

            }
            else if(i==numberOfThreads-1){
                rightSide[2*numberOfThreads-2]=rightSideOfEquation[i*width];
                rightSide[2*numberOfThreads-1]=rightSideOfEquation[rightSideOfEquation.length-1];
                topDiagonal[2*numberOfThreads-2]=topDiagonal[2*numberOfThreads-1]=0;
                intermediateMatr[2*numberOfThreads-2][2*numberOfThreads-3]=bottomDiagonal[2*numberOfThreads-2]=
                         matrix[width*(numberOfThreads-1)][width*(numberOfThreads-1)-1];
                intermediateMatr[2*numberOfThreads-2][2*numberOfThreads-2]=mainDiagonal[2*numberOfThreads-2]=
                         matrix[width*(numberOfThreads-1)][width*(numberOfThreads-1)];

                intermediateMatr[2*numberOfThreads-1][2*numberOfThreads-2]=bottomDiagonal[2*numberOfThreads-1]=
                         matrix[matrix.length-1][width*(numberOfThreads-1)-1];
                intermediateMatr[2*numberOfThreads-1][2*numberOfThreads-1]=mainDiagonal[2*numberOfThreads-1]=
                         matrix[matrix.length-1][matrix.length-1];
            }
            else{
                rightSide[2*i]=rightSideOfEquation[width*i];
                rightSide[2*i+1]=rightSideOfEquation[width*(i+1)-1];

                intermediateMatr[2*i][2*i-1]=bottomDiagonal[2*i]=matrix[width*i][width*i-1];
                intermediateMatr[2*i][2*i]=mainDiagonal[2*i]=matrix[width*i][width*i];
                intermediateMatr[2*i][2*i+1]=topDiagonal[2*i]=matrix[width*i][width*(i+1)];

                intermediateMatr[2*i+1][2*i]=bottomDiagonal[2*i+1]=matrix[width*(i+1)-1][width*i-1];
                intermediateMatr[2*i+1][2*i+1]=mainDiagonal[2*i+1]=matrix[width*(i+1)-1][width*(i+1)-1];
                intermediateMatr[2*i+1][2*i+2]=topDiagonal[2*i+1]=matrix[width*(i+1)-1][width*(i+1)];
            }
        }

        topDiagonal[2*numberOfThreads-1]=topDiagonal[2*numberOfThreads-2]=0;
        bottomDiagonal[0]=bottomDiagonal[1]=0;
        for (int i = 0; i < numberOfThreads-1; i++) {
            topDiagonal[2*i]=matrix[width*i][width*(i+1)];
            topDiagonal[2*i+1]=matrix[width*(i+1)-1][width*(i+1)-1];
        }
        for (int i = 0; i < numberOfThreads; i++) {
           mainDiagonal[2*i]=(i==0) ? matrix[0][0] : matrix[width*i][width*i-1];
           if (i==numberOfThreads-1)
               mainDiagonal[2*i+1]=matrix[matrix.length-1][matrix.length-1];
           else
               mainDiagonal[2*i+1]=matrix[width*(i+1)-1][width*(i+1)];
        }
        for (int i = 1; i < numberOfThreads; i++) {
            bottomDiagonal[2*i]=matrix[width*i][width*i];
            bottomDiagonal[2*i+1]=(i==numberOfThreads-1)? matrix[matrix.length-1][width*i-1]
                                                        : matrix[width*(i+1)-1][width*i-1] ;
        }



        double[] alpha=new double[2*numberOfThreads];
        double[] beta= new double[2*numberOfThreads];
        double[] partSolution=new  double[2*numberOfThreads];

        alpha[0]=0; beta[0]=0;
        alpha[1]=-topDiagonal[0]/mainDiagonal[0];
        beta[1]=rightSide[0]/mainDiagonal[0];
        for (int i = 1; i < 2*numberOfThreads-1 ; i++) {
            final double denominator = bottomDiagonal[i] * alpha[i] + mainDiagonal[i];
            alpha[i+1]=-topDiagonal[i]/ denominator;
            beta[i+1]=(rightSide[i]-bottomDiagonal[i]*beta[i])/denominator;
        }

        partSolution[2*numberOfThreads-1]=
                 (rightSide[2*numberOfThreads-1]-bottomDiagonal[2*numberOfThreads-1]*beta[2*numberOfThreads-1])/
                          (bottomDiagonal[2*numberOfThreads-1]*alpha[2*numberOfThreads-1]+mainDiagonal[2*numberOfThreads-1]);

        for (int i = 2*numberOfThreads-2; i >=0 ; i--) {
            partSolution[i]=alpha[i+1]*partSolution[i+1]+beta[i+1];
        }

        for (int i = 1; i < partSolution.length-2; i++) {
            double temp=partSolution[i];
            partSolution[i]=partSolution[i+1];
            partSolution[i+1]=temp;
        }

        for (int i = 0; i < numberOfThreads-1; i++) {
                x[i*width]=partSolution[2*i];
                x[(i+1)*width-1]=partSolution[2*i+1];
        }
        x[(numberOfThreads-1)*width]=partSolution[2*(numberOfThreads-1)];
        x[matrix.length-1]=partSolution[2*numberOfThreads-1];



    }

    public void solve(){
        double[] solution = new double[matrix.length];
        for (int i = 0; i < numberOfThreads; i++) {
            CalculatingThread task;
            if(i!=numberOfThreads-1)
                task=new CalculatingThread(i*width, (i+1)*width-1, this,countDownLatch);
            else
                task=new CalculatingThread(i*width, matrix.length-1,this,countDownLatch);

            task.start();
        }

        while (countDownLatch.getCount()!=1){}

        sweepMethod(matrix,numberOfThreads);
        countDownLatch.countDown();

    }

    public static double det(double[][] matrix) {
        double result=0;
        if (matrix.length == 1)
            return matrix[0][0];
        if (matrix.length == 2)
            return matrix[0][0]*matrix[1][1]-matrix[0][1]*matrix[1][0];

        for (int i = 0; i < matrix.length; i++)
            if(matrix[0][i]!=0)
            {
                double[][] minor= new double[matrix.length-1][matrix.length-1];
                for (int j = 1; j < matrix.length; j++)
                    for (int k = 0; k < matrix.length; k++)
                        if (k != i)
                            minor[j-1][(k>i) ? k-1 : k] = matrix[j][k];

                result += matrix[0][i] * det(minor)*Math.pow(-1,i);
            }
        return result;
    }

    public static void main(String[] args) {


        double[][] matrix= {
                 {-2,1,0,0,0,0,0,0,0,0,0,0},
                 {-1,-11,1,0,0,0,0,0,0,0,0,0},
                 {0,-1,21,-1,0,0,0,0,0,0,0,0},
                 {0,0,3,-10,-1,0,0,0,0,0,0,0},
                 {0,0,0,4,49,-6,0,0,0,0,0,0},
                 {0,0,0,0,-11,19,7,0,0,0,0,0},
                 {0,0,0,0,0,-2,4,-2,0,0,0,0},
                 {0,0,0,0,0,0,-1,2,-1,0,0,0},
                 {0,0,0,0,0,0,0,1,-12,10,0,0},
                 {0,0,0,0,0,0,0,0,10,22,-10,0},
                 {0,0,0,0,0,0,0,0,0,1,10,-1},
                 {0,0,0,0,0,0,0,0,0,0,10,11}};


       double[] rightSide={0,0,0,0,12,-30,0,0,-90,-80,0,0};
     /*   double[][] matrix= {
                 {-2,1,0,0,0,0},
                 {-1,-3,1,0,0,0},
                 {0,-1,2,-1,0,0},
                 {0,0,3,5,-1,0},
                 {0,0,0,4,11,-6},
                 {0,0,0,0,-11,12}};
        double[] rightSide={-3,2,10,13,-16,-29};
        */
        SLAE system=new SLAE(matrix,rightSide,2);
        system.solve();

    }
}
