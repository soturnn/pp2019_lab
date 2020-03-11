
public class ScanThread extends Thread {
    private Radio radio;

    ScanThread(Radio _radio){

        radio=_radio;
    }

    public void scan(){
        synchronized (radio) {
            if(radio.getCurrentStation()!=radio.minStation)
                radio.setCurrentStation(radio.getStations().get(radio.getStations().indexOf(radio.getCurrentStation())-1));
        }
    }

    @Override
    public void run() {
        super.run();

    }

}