import java.util.ArrayList;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Radio {

    private volatile float currentStation;
    public final float maxStation=108;
    public final float minStation=88;

    public ArrayList<Float> getStations() {
        return stations;
    }

    private ArrayList<Float> stations;
    private ScanThread scanButton;
    private ResetThread resetButton;
    private boolean on=false;
    private ReentrantLock radioSetting;

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public float getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(float _currentStation) {
        if((_currentStation<=maxStation)&&(_currentStation>=minStation))
            this.currentStation = _currentStation;
    }

    public void on(){
        if(!isOn()) {
            setCurrentStation(108);
            setOn(true);
        }
    }

    public void off(){
        setOn(false);
    }

    public float scan(){


        radioSetting.lock();
        try{
            if(isOn())
                scanButton.setDone(false);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            radioSetting.unlock();
        }

        return getCurrentStation();
    }

    public float reset(){

        radioSetting.lock();
        try{
            if(isOn())
               resetButton.setDone(false);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            radioSetting.unlock();
        }

        return getCurrentStation();
    }

    public Radio(){
        setCurrentStation(108);
        stations=new ArrayList<Float>(20);
        for (int i =0;i<15;i++) {
           float s=(float)(int)(Math.random()*(maxStation-minStation+1)+minStation)+
                     (float)((int)(Math.random()*99))/100;
            stations.add(s);
        }
        radioSetting=new ReentrantLock();

        resetButton= new ResetThread(this);
        scanButton= new ScanThread(this);
        resetButton.start();
        scanButton.start();


    }

}
