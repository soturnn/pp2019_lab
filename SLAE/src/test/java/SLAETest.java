import org.junit.jupiter.api.Test;
import org.junit.Assert;


class SLAETest {

    @Test
    public void rightSolveTest(){
        double[][] doubles= {
                 {-3,1,0,0,0,0},
                 {-1,-4,3,0,0,0},
                 {0,-1,5,-1,0,0},
                 {0,0,2,8,-1,0},
                 {0,0,0,2,10,-6},
                 {0,0,0,0,-1,7}};
        double[] rightSide={-1,2,1,3,6,9};
        SLAE system=new SLAE(doubles,rightSide,3);
        if(system.det(doubles)!=0)
            system.solve();
        else
            System.out.println("system hasn't solution");
        for (int i = 0; i < doubles.length; i++) {
            double resultOfSubstitution=0;
            for (int j = 0; j < doubles.length; j++)
                resultOfSubstitution+=doubles[i][j]*system.getX(j);
           // Assert.assertEquals(resultOfSubstitution,rightSide[i], 0.5);
            System.out.println(resultOfSubstitution-rightSide[i]);
        }
    }

    @Test
    public void modifiedMatr(){
        int num=2;
        double[][] doubles= {
                 {-3,1,0,0,0,0},
                 {-1,-4,3,0,0,0},
                 {0,-1,5,-1,0,0},
                 {0,0,2,8,-1,0},
                 {0,0,0,2,10,-6},
                 {0,0,0,0,-1,7}
                 /*
                 {2,1,0,0,0},
                 {6,13,2,0,0},
                 {0,5,10,1,0},
                 {0,0,10,21,5},
                 {0,0,0,40,50}

                  */
                 };


        double[] rightSide={-1,2,1,3,6,9};
        SLAE system=new SLAE(doubles,rightSide,num);
        if(system.det(doubles)!=0)
            system.solve();
        else
            System.out.println("system hasn't solution");

        int width=doubles.length/num;
        for(int i=0;i<num;i++){
            for(int j=i*width+1;j<=(i+1)*width-1;j++){
                double coefficient=-doubles[j][j-1]/doubles[j-1][j-1];
                for(int k=0;k<doubles.length;k++)
                    doubles[j][k]+=coefficient*doubles[j-1][k];
                rightSide[j]+=coefficient*rightSide[j-1];
            }

            for (int j = (i+1)*width-1-1; j >=i*width ; j--) {
                double coefficient = -doubles[j][j+1]/doubles[j+1][j+1];
                for(int k=0;k<doubles.length;k++)
                    doubles[j][k]+=coefficient*doubles[j+1][k];
                rightSide[j]+=coefficient*rightSide[j+1];
            }
        }

        for (int i = 0; i < doubles.length; i++) {
            for (int j = 0; j < doubles.length; j++) {
                System.out.format(/*doubles[i][j]- */" %.2f ",system.matrix[i][j]);
            }
            System.out.format(" %.2f",system.getRightSide(i));
            System.out.println();
        }
    }
}