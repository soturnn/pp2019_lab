import org.junit.Assert;
import org.junit.Test;


public class RadioTest {

    @Test
    public void  doubleScanTest() throws InterruptedException {
        Radio radio = new Radio();
        radio.on();

        radio.scan();
        Thread.sleep(200);
        radio.scan();
        Assert.assertTrue(((Double) 107.5 < radio.getCurrentStation())
                 && (radio.getCurrentStation() < (Double) 108.0));

        radio.off();
    }

    @Test
    public void resetAfterScanTest() throws InterruptedException {

        Radio radio = new Radio();
        radio.on();
        for (int i = 0; i < 3; i++) {
            radio.scan();
            Thread.sleep(600);
        }
        radio.scan();
        Thread.sleep(300);
        radio.reset();
        Thread.sleep(600);

        Assert.assertEquals((Double) 106.5,
                            radio.getCurrentStation());

        radio.off();

    }

}