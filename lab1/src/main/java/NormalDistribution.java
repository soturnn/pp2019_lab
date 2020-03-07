public class NormalDistribution {

    private static int m=0;
    private static int sigma=1;

    public static double randomRef(){
        double sum=-6;
        for (int i = 0; i <12 ; i++) 
            sum+= Math.random();
        return sigma*sum+m;
    }


    public static void main(String[] args){
        double m=0;
        for (int i = 0; i < 10 ; i++) {
           m+= NormalDistribution.randomRef();
        }

    }
}
