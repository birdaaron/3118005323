import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TextSimilarity
{
    protected List<String> articleA;//源文章
    protected List<String> articleB;//对比文章
    private String pathA;//源文章目录
    private String pathB;//对比文章目录
    private String outputPath;//输出文件目录
    private static final Logger logger = Logger.getLogger("TextSimilarity.class");
    public TextSimilarity(String pathA,String pathB,String outputPath)
    {
        articleA = new ArrayList<>();
        articleB = new ArrayList<>();
        this.pathA = pathA;
        this.pathB = pathB;
        this.outputPath = outputPath;

        initContentList(pathA, articleA);
        initContentList(pathB, articleB);

        Core core = new Core();
        core.textSegment(articleA,0);
        core.textSegment(articleB,1);

        createOutputFile(getSimilarity(core.calculateCos()));

    }

    /**
     * 将文章从文件读取到List中
     * @param path 文件路径
     * @param list 存放的List
     */
    private void initContentList(String path,List<String> list)
    {
        try(FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);)
        {
            String line;
            while((line=br.readLine())!=null)
            {
                line = filterSentence(line);
                if(!line.isEmpty()) //过滤空格后如果!isEmpty()说明不是空行，可以添加进list
                {
                    list.add(line);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 过滤字符串
     * @param sentence 待过滤字符串
     * @return 过滤后的字符串
     */
    protected String filterSentence(String sentence)
    {
        sentence = sentence.trim();  //过滤句子前后空格
        return sentence;
    }

    /**
     * 将文章相似度取到小数点后两位
     * @param cos 文章相似度
     * @return 文章相似度
     */
    public String getSimilarity(Double cos)
    {

        return "0"+ new DecimalFormat("#.0000")
                .format(cos)
                .substring(0,3);
    }

    /**
     * 创造输出文件
     * @param similarity 文章相似度
     */
    protected void createOutputFile(String similarity)
    {
        File outPutFile = new File(outputPath);
        try(FileOutputStream fos = new FileOutputStream(outPutFile);
            PrintWriter pw = new PrintWriter(fos);)
        {
            pw.write("源文件："+pathA+"\r\n");
            pw.write("对比文件："+pathB+"\r\n");
            pw.write("文本相似度："+similarity);
            pw.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        new TextSimilarity(args[0], args[1],args[2]);
    }

}
