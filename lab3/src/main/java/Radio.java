import java.util.ArrayList;

public class Radio {

    private volatile Double currentStation;
    public final Double maxStation=108.0;
    public final Double minStation=88.0;

    public ArrayList<Double> getStations() {
        return stations;
    }

    private ArrayList<Double> stations;
    private boolean on=false;

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public Double getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(Double _currentStation) {
        if((_currentStation<=maxStation)&&(_currentStation>=minStation))
            this.currentStation = _currentStation;
    }

    public void on(){
        if(!isOn()) {
            setCurrentStation(108.0);
            setOn(true);
        }
    }

    public void off(){
        setOn(false);
    }

    public Radio(){
        setCurrentStation(108.0);
        stations=new ArrayList<Double>(41);
        for (double i = 88.0; i <=108.0 ; i+=0.5)
            stations.add(i);
    }

}

