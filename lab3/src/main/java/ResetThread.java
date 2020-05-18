import java.util.ArrayList;

public class ResetThread extends Thread {
    private Radio radio;

    public ResetThread(Radio _radio){

        radio=_radio;
    }

    public void reset() throws InterruptedException {
        synchronized (radio){
            if(radio.getCurrentStation()!=radio.maxStation) {
                ArrayList<Double> stations = radio.getStations();
                do {
                    radio.setCurrentStation(radio.getCurrentStation()+0.1);
                    Thread.sleep(100);
                } while (!stations.contains(radio.getCurrentStation()));
            }
        }
    }

    @Override
    public void run() {
        super.run();

    }

}
