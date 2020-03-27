import javax.print.attribute.standard.MediaSize;
import java.util.concurrent.CountDownLatch;

class CalculatingThread extends Thread{

    private int bottomIndex;
    private int topIndex;
    private SLAE matr;
    private CountDownLatch latch;

    CalculatingThread(int _bottomIndex, int _topIndex, SLAE _matr, CountDownLatch _latch){
        try {
            matr = _matr;
            if((_bottomIndex<0)||(_topIndex<0)||(_bottomIndex>matr.matrix.length)||(_topIndex>matr.matrix.length))
                throw new IllegalArgumentException();
            bottomIndex = _bottomIndex;
            topIndex = _topIndex;
            if(_latch==null)
                throw new NullPointerException();
            latch=_latch;
        }
        catch (IllegalArgumentException|NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        for(int i=bottomIndex+1;i<=topIndex;i++)
        try {
            double coefficient=-matr.matrix[i][i-1]/matr.matrix[i-1][i-1];
            if(Double.isNaN(coefficient))
                throw new ArithmeticException("Деление на 0");
            for(int j=0;j<matr.matrix.length;j++)
                matr.matrix[i][j]+=coefficient*matr.matrix[i-1][j];
            matr.setRightSide(i,matr.getRightSide(i)+coefficient*matr.getRightSide(i-1));
        }
        catch(ArithmeticException e){
            e.printStackTrace();
        }

        for (int i = topIndex-1; i >=bottomIndex ; i--)
        try{
            double coefficient = -matr.matrix[i][i+1]/matr.matrix[i+1][i+1];
            if(Double.isNaN(coefficient))
                throw new ArithmeticException("Деление на 0");
            for(int j=0;j<matr.matrix.length;j++)
                matr.matrix[i][j]+=coefficient*matr.matrix[i+1][j];
            matr.setRightSide(i,matr.getRightSide(i)+coefficient*matr.getRightSide(i+1));
        }
        catch (ArithmeticException e){
            e.printStackTrace();
        }

        latch.countDown();
        try {
          latch.await();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        int length = matr.matrix.length;
        int width=matr.getWidth();

        if (bottomIndex==0){
            for (int i = 1; i < topIndex-bottomIndex+1; i++) {
                matr.setX(i, (matr.getRightSide(i)-matr.getX(width)*matr.matrix[i][width])/matr.matrix[i][i]);
            }

        }
        else if(topIndex==length-1){
            for (int i = topIndex-1; i >=bottomIndex ; i--) {
                matr.setX(i,(matr.getRightSide(i)-matr.matrix[i][length-1 -width]*matr.getX(length-1 -width))/matr.matrix[i][i]);
            }
        }
        else
            for (int i = bottomIndex+1; i < topIndex; i++)
                matr.setX(i,(matr.getRightSide(i)-matr.matrix[i][bottomIndex-1]*matr.getX(bottomIndex-1)-matr.matrix[i][topIndex+1]*matr.getX(topIndex+1))
                         / matr.matrix[i][i]);

    }

}
