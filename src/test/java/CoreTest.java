import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CoreTest
{
    Core core;
    List<String> sentenceA,sentenceB;
    @Before
    public void createCore()
    {
        core = new Core();
    }
    @Before
    public void createSentence()
    {
        sentenceA = new ArrayList<>();
        sentenceA.add("我喜欢分词");
        sentenceB =new ArrayList<>();
        sentenceB.add("分词讨厌我");
    }
    @Test
    public void testWordMapNotNull()
    {
        assertNotNull(core.wordMap);
    }
    @Test
    public void testTextSegment()
    {
        core.textSegment(sentenceA,0);
        core.textSegment(sentenceB,1);
        assertEquals(4,core.wordMap.size());
        assertTrue(core.wordMap.containsKey("我"));
        assertTrue(core.wordMap.containsKey("喜欢"));
        assertTrue(core.wordMap.containsKey("分词"));
        assertTrue(core.wordMap.containsKey("讨厌"));
    }
    @Test
    public void testSimilarity()
    {
        core.textSegment(sentenceA,0);
        core.textSegment(sentenceB,1);
        assertEquals(core.calculateCos(),core.calculateTop()/core.calculateBottom(),0.0);
    }
}