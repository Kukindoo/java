package test.java.com.ritmus.string;

import main.java.com.ritmus.string.StringHelper;
import org.junit.Test;

public class StringHelperTest {
    @Test
    public void testStringWith2CharsIsReversed(){
        StringHelper helper = new StringHelper();
        assertEquals("BA",helper.swapLastTwoChars("AB"));
    }
}
