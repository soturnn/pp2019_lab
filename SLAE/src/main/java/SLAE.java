import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SLAE {
    public volatile static double[][] matrix;
    private volatile static double[] rightSideOfEquation;
    private volatile static double[] x;
    private int numberOfThreads=3;
    private int width;
    private CyclicBarrier barrier;

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
            if(i!=0)
                matrix[i][i-1]=Math.random()*20-10;
            if(i!=n-1)
                matrix[i][i+1]=Math.random()*20-10;
            matrix[i][i]= Math.random()*10+20;
            rightSideOfEquation[i]=Math.random()*80-20;
        }

        width = matrix.length/numberOfThreads;
        barrier=new CyclicBarrier(numberOfThreads+1);
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
       // rightSideOfEquation=coef.clone();
        rightSideOfEquation=new double[coef.length];
        for (int i = 0; i < coef.length; i++) {
            rightSideOfEquation[i]=coef[i];
        }
        width = matrix.length/numberOfThreads;
        barrier=new CyclicBarrier(numberOfThreads+1);
        x=new double[matrix.length];
    }

    public void sweepMethod(){

        double[] bottomDiagonal=new double[2*numberOfThreads];
        double[] topDiagonal=new double[2*numberOfThreads];
        double[] mainDiagonal=new double[2*numberOfThreads];
        double[] rightSide=new double[2*numberOfThreads];

        topDiagonal[2*numberOfThreads-1]=topDiagonal[2*numberOfThreads-2]=0;
        bottomDiagonal[0]=bottomDiagonal[1]=0;
        rightSide[0]=rightSideOfEquation[0];
        rightSide[1]=rightSideOfEquation[width-1];
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
            if(i==numberOfThreads-1){
                rightSide[2*numberOfThreads-2]=rightSideOfEquation[i*width];
                rightSide[2*numberOfThreads-1]=rightSideOfEquation[rightSideOfEquation.length-1];
            }
            else {
                rightSide[2 * i] = rightSideOfEquation[width * i];
                rightSide[2 * i + 1] = rightSideOfEquation[width * (i + 1) - 1];
            }

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


        for (int i = 1; i < partSolution.length-2; i+=2) {
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

    public void solve() throws InterruptedException {
        for (int i = 0; i < numberOfThreads; i++) {
            CalculatingThread task;
            if(i!=numberOfThreads-1)
                task=new CalculatingThread(i*width, (i+1)*width-1, this,barrier);
            else
                task=new CalculatingThread(i*width, matrix.length-1,this,barrier);

            task.start();
        }

        while (barrier.getNumberWaiting()!=numberOfThreads){}

        sweepMethod();

        try {
            barrier.await();
            barrier.reset();
            barrier.await();
        }
        catch (BrokenBarrierException e){
            e.printStackTrace();
        }



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

}
