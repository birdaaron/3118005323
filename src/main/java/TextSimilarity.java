import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextSimilarity
{
    private List<String> listA;
    private List<String> listB;
    private Map<String,int[]> wordMap;
    private String pathA,pathB,outputPath;
    public TextSimilarity(String pathA,String pathB,String outputPath)
    {
        listA = new ArrayList<>();
        listB = new ArrayList<>();
        wordMap = new HashMap<>();
        this.pathA = pathA;
        this.pathB = pathB;
        this.outputPath = outputPath;
        initContent();
        textSegment();
        calculateCos();
    }
    private void initContent()
    {
        initContentList(pathA,listA);
        initContentList(pathB,listB);
    }
    private void initContentList(String path,List<String> list)
    {
        try
        {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line=br.readLine())!=null)
            {
                line = line.trim();
                if(!line.isEmpty())
                    list.add(line);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void putWordIntoMap(List<SegToken> segTokenList,int index)
    {
        for(SegToken segToken : segTokenList)
        {
            String word = segToken.word;
            if(wordMap.containsKey(word))
                wordMap.get(word)[index]++;
            else
            {
                int[] count = new int[2];
                count[0] = index==0 ? 1 : 0;
                count[1] = index==1 ? 1 : 0;
                wordMap.put(word,count);
            }
        }
    }
    private void textSegment()
    {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokenListA;
        for(String sentence : listA)
        {
            segTokenListA = segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
            putWordIntoMap(segTokenListA,0);
        }
        List<SegToken> segTokenListB;
        for(String sentence : listB)
        {
            segTokenListB = segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
            putWordIntoMap(segTokenListB,1);
        }
    }
    private void calculateCos()
    {
        double top = calculateTop();
        double bottom = calculateBottom();
        System.out.println(top/bottom);
    }
    private double calculateTop()
    {
        double top = 0.0;
        for(int[] count : wordMap.values())
            top += count[0]*count[1];
        return top;
    }
    private double calculateBottom()
    {
        double num1=0.0,num2=0.0;
        double sqrt1 =0.0,sqrt2=0.0;
        for(int [] count : wordMap.values())
        {
            num1 += count[0]*count[0];
            num2 += count[1]*count[1];
        }
        sqrt1 = Math.sqrt(num1);
        sqrt2 = Math.sqrt(num2);
        return sqrt1*sqrt2;
    }
    public static void main(String[] args)
    {
        TextSimilarity ts = new TextSimilarity("E:\\test\\orig.txt",
                "E:\\test\\orig_0.8_add.txt",null);
        TextSimilarity ts1 = new TextSimilarity("E:\\test\\orig.txt",
                "E:\\test\\orig_0.8_del.txt",null);
        TextSimilarity ts2 = new TextSimilarity("E:\\test\\orig.txt",
                "E:\\test\\orig_0.8_dis_1.txt",null);
        TextSimilarity ts3 = new TextSimilarity("E:\\test\\orig.txt",
                "E:\\test\\orig_0.8_dis_10.txt",null);
        TextSimilarity ts4 = new TextSimilarity("E:\\test\\orig.txt",
                "E:\\test\\orig_0.8_dis_15.txt",null);
    }
}
