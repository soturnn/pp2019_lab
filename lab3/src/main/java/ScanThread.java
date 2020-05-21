import java.util.ArrayList;

public class ScanThread extends Thread {
    private Radio radio;


    public void setScanDirection(int scanDirection) {
        this.scanDirection = scanDirection;
    }

    private int scanDirection;

    ScanThread(Radio _radio){

        radio=_radio;
        scanDirection=-1;
    }

    @Override
    public void run() {
        super.run();
        ArrayList<Double> stations = radio.getStations();
        radio.setScanning(true);
        do{
            if(scanDirection==1) {
                if (radio.getCurrentStation() != radio.maxStation) {
                    radio.setCurrentStation(radio.getCurrentStation() + 0.1);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else
            if(radio.getCurrentStation()!=radio.minStation) {
                radio.setCurrentStation(radio.getCurrentStation() - 0.1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while ((!stations.contains(radio.getCurrentStation()))&&!this.isInterrupted());
        radio.setScanning(false);
    }

    }