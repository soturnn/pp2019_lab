import org.junit.*;


public class RadioTest {

    @Test
    public void  raceTest() throws InterruptedException {
        Radio radio=new Radio();
        ScanThread scanButton= new ScanThread(radio);
        ResetThread resetButton=new ResetThread(radio);
        scanButton.start();
        resetButton.start();


        radio.on();
        for (int i = 0; i < 5 ; i++) {
            scanButton.scan();
            resetButton.reset();
            scanButton.scan();
            scanButton.scan();
            resetButton.reset();
        }

        Assert.assertEquals(radio.getCurrentStation(),
                            radio.getStations().get(radio.getStations().size() - 6));
        radio.off();
    }

}