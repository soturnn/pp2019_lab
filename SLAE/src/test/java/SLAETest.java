import org.junit.jupiter.api.Test;
import org.junit.Assert;


class SLAETest {

    @Test
    public void rightSolveTest() throws InterruptedException {

        for (int i = 0; i < 15; i++) {
            int size = 100;
            double[][] doubles = new double[size][size];
            double[] rightSide = new double[size];
            int numberOfThreads = 4;

            for (int j = 0; j < doubles.length; j++) {
                if (j != 0)
                    doubles[j][j - 1] = Math.random() * 20 - 10;
                if (j != doubles.length - 1)
                    doubles[j][j + 1] = Math.random() * 20 - 10;
                doubles[j][j] = Math.random() * 10 + 20;
                rightSide[j] = Math.random() * 80 - 20;
            }

            SLAE system = new SLAE(doubles, rightSide, numberOfThreads);
            long time = System.currentTimeMillis();
            system.solve();
            time -= System.currentTimeMillis();
            for (int k = 0; k < doubles.length; k++) {
                double resultOfSubstitution = 0;
                for (int j = 0; j < doubles.length; j++) {
                    resultOfSubstitution += doubles[k][j] * system.getX(j);
                }
                Assert.assertEquals(resultOfSubstitution, rightSide[k], 0.1);
            }
            System.out.println(-time);
        }
    }


}