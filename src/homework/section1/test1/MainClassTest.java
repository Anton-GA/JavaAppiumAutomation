package homework.section1.test1;

import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{

    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("Возвращаемое значение != 14", 14, getLocalNumber());
    }
}
