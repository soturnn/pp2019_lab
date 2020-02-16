import static org.junit.jupiter.api.Assertions.*;

class NormalDistributionTest {

    @org.junit.jupiter.api.Test
    void randomRef() {
        double result = NormalDistribution.randomRef();
        assertTrue(Math.abs(result)<3);
    }

}