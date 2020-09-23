import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
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
        //参数为各个对比文章的绝对地址和文章的第一句话
        Object[][] objects = {{"E:\\test\\orig_0.8_add.txt","活着前言"},
                              {"E:\\test\\orig_0.8_del.txt","活着言"},
                              {"E:\\test\\orig_0.8_dis_1.txt","活着言前"},
                              {"E:\\test\\orig_0.8_dis_10.txt","活着真正"},
                              {"E:\\test\\orig_0.8_dis_15.txt","的活着前言"}};
        return Arrays.asList(objects);
    }
    public TextSimilarityTest(String pathB,String firstSentence)
    {
        this.pathA = "E:\\test\\orig.txt";//源文章地址
        this.pathB = pathB;               //对比文章地址
        this.outputPath = "E:\\test\\orig_output.txt";//输出文件地址
        this.firstSentence = firstSentence;//对比文章的第一句话
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
    @Test
    public void testCreateOutputFile()
    {
        String similarity = "0.96";
        ts.createOutputFile(similarity);
        File file = new File(this.outputPath);
        assertTrue(file.exists());
    }

}