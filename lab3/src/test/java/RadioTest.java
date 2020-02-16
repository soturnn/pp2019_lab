import static org.junit.jupiter.api.Assertions.*;

class RadioTest {

    @org.junit.jupiter.api.Test
    void scan() {
        Radio r=new Radio();
        for (int i = 0; i <10 ; i++) {
            if ((Math.random() > 0.5))
                r.scan();
            else
                r.reset();
        }
        assertEquals(r.getCurrentStation(),108);
        r.on();
        for (int i = 0; i <10 ; i++) {
            if ((Math.random() > 0.5))
                r.scan();
            else
                r.reset();
        }
    }

    @org.junit.jupiter.api.Test
    void reset() {
    }
}