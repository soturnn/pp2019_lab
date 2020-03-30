import org.junit.jupiter.api.Test;
import org.junit.Assert;


class SLAETest {

    @Test
    public void rightSolveTest(){

        for (int i = 0; i < 10; i++) {


            double[][] doubles = new double[6][6];
            double[] rightSide = new double[6];
            int numberOfThreads=2;

            for (int j = 0; j < doubles.length; j++) {
                if(j!=0)
                    doubles[j][j-1]=Math.random()*20-10;
                if(j!=doubles.length-1)
                    doubles[j][j+1]=Math.random()*20-10;
                doubles[j][j]= Math.random()*10+20;
                rightSide[j]=Math.random()*80-20;
            }

            System.out.println("  Система №"+i);

            for (int j = 0; j < doubles.length; j++) {
                for (int k = 0; k < doubles.length; k++) {
                    System.out.printf(" %.2f ",doubles[j][k]);
                }
                System.out.println();
            }

            SLAE system = new SLAE(doubles, rightSide, numberOfThreads);
            if (system.det(doubles) != 0) {
                system.solve();
                for (int k = 0; k < doubles.length; k++) {
                    double resultOfSubstitution = -rightSide[k];
                    for (int j = 0; j < doubles.length; j++)
                        resultOfSubstitution += doubles[k][j] * system.getX(j);
                    boolean cond=true;
                    if (Math.abs(resultOfSubstitution)>0.1)
                        cond=false;
                    //Assert.assertEquals(resultOfSubstitution,0, 0.1);
                    Assert.assertTrue(cond);

                }
                System.out.println("  Система №"+i+" решена");
            }
            else
                System.out.println("system hasn't solution");

        }
    }


}