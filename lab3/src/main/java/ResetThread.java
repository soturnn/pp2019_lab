public class ResetThread extends Thread {
    private boolean isDone=true;
    private Radio radio=null;

    ResetThread(Radio _radio){

        radio=_radio;
    }


    @Override
    public void run() {
        super.run();
        while (!Thread.currentThread().isInterrupted()) {
            while (isDone) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

                do
                    radio.setCurrentStation(radio.getCurrentStation() + (float) 0.01);
                while ((!radio.getStations().contains(radio.getCurrentStation())) && (radio.getCurrentStation() < radio.maxStation));

            isDone=true;
        }
    }

    public boolean isDone () {
        return isDone;
    }

    public void setDone ( boolean done){
        isDone = done;
    }
}
