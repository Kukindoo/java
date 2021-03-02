import org.junit.Test;

import static org.junit.Assert.*;

public class StringHelperTest {

    StringHelper helper = new StringHelper();
    @Test
    public void testStringWith2CharsIsReversed(){
        assertEquals("BA",helper.swapLastTwoChars("AB"));
        assertEquals("DC",helper.swapLastTwoChars("CD"));
    }
    @Test
    public void testStringWith3CharsIsReversed(){
        assertEquals("ACB",helper.swapLastTwoChars("ABC"));
        assertEquals("CED",helper.swapLastTwoChars("CDE"));
    }
    @Test
    public void testStringWith10CharsIsReversed(){
        assertEquals("ABCDEFGHJI",helper.swapLastTwoChars("ABCDEFGHIJ"));
        assertEquals("CED",helper.swapLastTwoChars("CDE"));
    }
    @Test
    public void testStringWith1CharsIsReversed(){
        assertEquals("A",helper.swapLastTwoChars("A"));
    }
    @Test
    public void testStringWith0CharsIsReversed(){
        assertEquals("",helper.swapLastTwoChars(""));
    }
}
