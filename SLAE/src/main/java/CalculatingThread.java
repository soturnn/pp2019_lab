import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CalculatingThread extends Thread{

    private int bottomIndex;
    private int topIndex;
    private SLAE system;
    private CyclicBarrier barrier;

    CalculatingThread(int _bottomIndex, int _topIndex, SLAE _matr, CyclicBarrier _latch){
        try {
            system = _matr;
            if((_bottomIndex<0)||(_topIndex<0)||(_bottomIndex>system.matrix.length)||(_topIndex>system.matrix.length))
                throw new IllegalArgumentException();
            bottomIndex = _bottomIndex;
            topIndex = _topIndex;
            if(_latch==null)
                throw new NullPointerException();
            barrier =_latch;
        }
        catch (IllegalArgumentException|NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        for(int i=bottomIndex+1;i<=topIndex;i++)
        try {
            double coefficient=-system.matrix[i][i-1]/system.matrix[i-1][i-1];
            if(Double.isNaN(coefficient))
                throw new ArithmeticException("Деление на 0");
            for(int j=0;j<system.matrix.length;j++)
                system.matrix[i][j]+=coefficient*system.matrix[i-1][j];
            system.setRightSide(i,system.getRightSide(i)+coefficient*system.getRightSide(i-1));
        }
        catch(ArithmeticException e){
            e.printStackTrace();
        }

        for (int i = topIndex-1; i >=bottomIndex ; i--)
        try{
            double coefficient = -system.matrix[i][i+1]/system.matrix[i+1][i+1];
            if(Double.isNaN(coefficient))
                throw new ArithmeticException("Деление на 0");
            for(int j=0;j<system.matrix.length;j++)
                system.matrix[i][j]+=coefficient*system.matrix[i+1][j];
            system.setRightSide(i,system.getRightSide(i)+coefficient*system.getRightSide(i+1));
        }
        catch (ArithmeticException e){
            e.printStackTrace();
        }

        try {
          barrier.await();
        }
        catch (InterruptedException | BrokenBarrierException  e){
            e.printStackTrace();
        }
        int length = system.matrix.length;
        int width=system.getWidth();

        if (bottomIndex==0){
            for (int i = 1; i < topIndex-bottomIndex; i++) {
                system.setX(i, (system.getRightSide(i)-system.getX(width)*system.matrix[i][width])/system.matrix[i][i]);
            }

        }
        else if(topIndex==length-1){
            for (int i = topIndex-1; i >bottomIndex ; i--) {
                system.setX(i,(system.getRightSide(i)-system.matrix[i][bottomIndex-1]*system.getX(bottomIndex-1))/system.matrix[i][i]);
            }
        }
        else
            for (int i = bottomIndex+1; i < topIndex; i++)
                system.setX(i,(system.getRightSide(i)-system.matrix[i][bottomIndex-1]*system.getX(bottomIndex-1)-system.matrix[i][topIndex+1]*system.getX(topIndex+1))
                         / system.matrix[i][i]);

        try {
            barrier.await();
        }
        catch (InterruptedException| BrokenBarrierException e){
            e.printStackTrace();
        }
    }

}
