import org.junit.Assert;
import org.junit.jupiter.api.Test;


class RadioTest {

    @Test
    void  raceTest(){
        Radio radio=new Radio();
        ScanThread scanButton= new ScanThread(radio);
        ResetThread resetButton=new ResetThread(radio);
        scanButton.start();
        resetButton.start();


        radio.on();
        for (int i = 0; i < 35 ; i++) {
            scanButton.scan();
            resetButton.reset();
            scanButton.scan();
            scanButton.scan();
            resetButton.reset();
        }

        Assert.assertTrue(radio.getCurrentStation().equals(radio.getStations().get(radio.getStations().size() - 36)));
    }

}