package homework.section1.test2;

import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void testClassNumber() {
        Assert.assertTrue("Возвращаемое число < 45", getClassNumber() > 45);
    }
}
