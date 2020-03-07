import org.junit.Assert;
import org.junit.Test;

class NormalDistributionTest {

    @Test
   public void randomRef() {
        double result = NormalDistribution.randomRef();
        Assert.assertTrue(Math.abs(result)<4);
    }

}