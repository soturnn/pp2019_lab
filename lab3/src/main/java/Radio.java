import java.util.ArrayList;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Radio {

    private volatile float currentStation;
    public final float maxStation=108;
    public final float minStation=88;
    private ArrayList<Float> stations;
    private Thread scanButton;
    private Thread resetButton;
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

    private void setCurrentStation(float _currentStation) {
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

        scanButton=new Thread(){
            @Override
            public void run() {
                super.run();
                do{ setCurrentStation( getCurrentStation()-(float)0.01);}
                while ((!stations.contains(getCurrentStation()))&&(getCurrentStation()>maxStation));
            }
        };
        radioSetting.lock();
        try{
            if(isOn()) {
                scanButton.start();
                scanButton.run();
            }
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

        resetButton=new Thread(){
            @Override
            public void run() {
                super.run();
                do{setCurrentStation( getCurrentStation()+(float)0.01);}
                while ((!stations.contains(getCurrentStation()))&&(getCurrentStation()<maxStation));
            }
        };
        radioSetting.lock();
        try{
            if(isOn()) {
                resetButton.start();
                resetButton.run();
            }
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


    }

}
