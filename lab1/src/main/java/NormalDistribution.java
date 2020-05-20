

public class NormalDistribution {

    private static int m=0;

    public static double getSigma() {
        return sigma;
    }

    private static double sigma=1.0/6;
    public static double randomRef(){
        double sum=-6;
        for (int i = 0; i <12 ; i++)
            sum+= Math.random();
        return (sigma*sum+m)/6;
    }

}
