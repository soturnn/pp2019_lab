import org.junit.Assert;
import org.junit.Test;


public class NormalDistributionTest {

    @Test
    public void randomRef() {

        for (int i = 0; i < 1000; i++) {
            Assert.assertTrue(Math.abs(NormalDistribution.randomRef())<4*NormalDistribution.getSigma());
        }
    }

}