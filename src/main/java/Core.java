import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Core
{
    protected Map<String,int[]> wordMap;    //分词map，int[0]为articleA的分词频次，int[1]为articleB的分词频次
    public Core()
    {
        wordMap = new HashMap<>();
    }

    /**
     * 文章分词
     * @param article 文章
     * @param index 0代表articleA 1代表articleB
     */
    protected void textSegment(List<String> article,int index)
    {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokenList;
        for(String sentence : article)
        {
            segTokenList = segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
            putWordIntoMap(segTokenList,index);
        }
    }

    /**
     * 将分词防入map中
     * @param segTokenList SegTokenList
     * @param index 0代表articleA 1代表articleB
     */
    protected void putWordIntoMap(List<SegToken> segTokenList, int index)
    {
        for(SegToken segToken : segTokenList)
        {
            String word = segToken.word;
            if(wordMap.containsKey(word))   //如果存在分词，就将分词频数加一
                wordMap.get(word)[index]++;
            else
            {
                int[] count = new int[2];
                count[0] = index==0 ? 1 : 0;   //如果不存在，就将相应文章的分词频数置1/0，并将分词放入map中
                count[1] = index==1 ? 1 : 0;
                wordMap.put(word,count);
            }
        }
    }

    /**
     * 计算文章相似度
     * @return 文章相似度
     */
    protected double calculateCos()
    {
        double top = calculateTop();
        double bottom = calculateBottom();
        return top/bottom;
    }

    /**
     * 计算余弦值的分子
     * @return a0*b0 + a1*b1 + ··· +an*bn
     */
    protected double calculateTop()
    {
        double top = 0.0;
        for(int[] count : wordMap.values())
            top += count[0]*count[1];
        return top;
    }

    /**
     * 计算余弦值分母
     * @return Sqrt(a0^2 + ··· + an^2)*Sqrt(b0^2 + ··· + bn^2)
     */
    protected double calculateBottom()
    {
        double num1=0.0;
        double num2=0.0;
        double sqrt1 =0.0;
        double sqrt2=0.0;
        for(int [] count : wordMap.values())
        {
            num1 += count[0]*count[0];
            num2 += count[1]*count[1];
        }
        sqrt1 = Math.sqrt(num1);
        sqrt2 = Math.sqrt(num2);
        return sqrt1*sqrt2;
    }
    public void finish()
    {
        wordMap = null;
    }
}
