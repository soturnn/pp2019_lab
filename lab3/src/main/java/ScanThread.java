import java.util.ArrayList;

public class ScanThread extends Thread {
    private Radio radio;

    public ScanThread(Radio _radio){

        radio=_radio;
    }

    public void scan() throws InterruptedException {
        synchronized (radio) {
            if(radio.getCurrentStation()!=radio.minStation){
                ArrayList<Double> stations = radio.getStations();
                do {
                    radio.setCurrentStation(radio.getCurrentStation()-0.1);
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