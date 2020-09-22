import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TextSimilarityTest
{
    TextSimilarity ts;
    String pathA;
    String pathB;
    String outputPath;
    String firstSentence;

    @Parameterized.Parameters
    public static Collection initData()
    {
        //各个测试txt的目录和第一句话
        Object[][] objects = {{"E:\\test\\orig_0.8_add.txt","活着前言"},
                              {"E:\\test\\orig_0.8_del.txt","活着言"},
                              {"E:\\test\\orig_0.8_dis_1.txt","活着言前"},
                              {"E:\\test\\orig_0.8_dis_10.txt","活着真正"},
                              {"E:\\test\\orig_0.8_dis_15.txt","的活着前言"}};
        return Arrays.asList(objects);
    }

    public TextSimilarityTest(String pathB,String firstSentence)
    {
        this.pathA = "E:\\test\\orig.txt";
        this.pathB = pathB;
        this.outputPath = "E:\\test\\orig_output.txt";
        this.firstSentence = firstSentence;
    }
    @Before
    public void createTestSimilarity()
    {
        ts = new TextSimilarity(pathA, pathB,outputPath);
    }
    @Test
    public void testArticleAFirstSentence()
    {
        assertNotEquals(0,ts.articleA.size());
        assertEquals("活着前言",ts.articleA.get(0));
    }
    @Test
    public void testArticleBFirstSentence()
    {
        assertNotEquals(0,ts.articleB.size());
        assertEquals(firstSentence,ts.articleB.get(0));
    }
    @Test
    public void testSimilarity()
    {
        double similarity = 0.333333333;
        assertEquals("0.33",ts.getSimilarity(similarity));
    }
    @Test
    public void testFilterSentence()
    {
        String str = "     我是一个句子      ";
        assertEquals("我是一个句子",ts.filterSentence(str));
    }
}