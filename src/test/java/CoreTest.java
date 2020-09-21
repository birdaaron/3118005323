import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoreTest
{
    Core core;

    @Before
    public void createCore()
    {
        core = new Core();
    }
    @Test
    public void testWordMapNotNull()
    {
        assertNotNull(core.wordMap);
    }
    @Test
    public void testMTextSegment()
    {
        List<String> sentence = new ArrayList<>();
        sentence.add("我喜欢分词");
        core.textSegment(sentence,0);
        assertEquals(core.wordMap.size(),3);
        assertTrue(core.wordMap.containsKey("我"));
        assertTrue(core.wordMap.containsKey("喜欢"));
        assertTrue(core.wordMap.containsKey("分词"));
    }
    @Test
    public void testSimilarity()
    {
        double top = core.calculateTop();
        double bottom = core.calculateBottom();
        assertEquals(top / bottom, core.calculateCos(), 0.0);
    }
}