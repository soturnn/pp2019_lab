public class ResetThread extends Thread {
    private Radio radio;

    ResetThread(Radio _radio){

        radio=_radio;
    }

    public void reset(){
        synchronized (radio){
            if(radio.getCurrentStation()!=radio.maxStation)
                radio.setCurrentStation(radio.getStations().get(radio.getStations().indexOf(radio.getCurrentStation())+1));
        }
    }

    @Override
    public void run() {
        super.run();

    }

}
