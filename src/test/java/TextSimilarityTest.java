import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextSimilarityTest
{
    TextSimilarity ts ;
    @Before
    public void createTestSimilarity()
    {
        ts = new TextSimilarity("E:\\test\\orig.txt",
                "E:\\test\\orig_0.8_dis_1.txt","E:\\test\\orig_output.txt");
    }
    @Test
    public void testArticleA()
    {
        assertTrue(ts.articleA.size()!=0);
        assertEquals(ts.articleA.get(0),"活着前言");
    }
    @Test
    public void testArticleB()
    {
        assertTrue(ts.articleB.size()!=0);
        assertEquals(ts.articleB.get(0),"的活着前言");
    }
}