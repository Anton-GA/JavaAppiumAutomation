package homework.section1.test3;

import com.sun.xml.internal.ws.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void testGetClassString() {
        Assert.assertTrue("Искомая подстрока не найдена", getClassString().contains("Hello"));
    }
}
