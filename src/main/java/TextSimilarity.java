import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TextSimilarity
{
    protected List<String> articleA;
    protected List<String> articleB;
    private String pathA,pathB,outputPath;

    public static void main(String[] args)
    {
        TextSimilarity textSimilarity =  new TextSimilarity(args[0],
            args[1],args[2]);
    }
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
        String similarity = "0"+
                new DecimalFormat("#.0000")
                .format(core.calculateCos())
                .substring(0,3);
        createOutputFile(similarity);
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

    private void createOutputFile(String similarity)
    {
        try
        {
            File outPutFile = new File(outputPath);
            FileOutputStream fos = new FileOutputStream(outPutFile);
            PrintWriter pw = new PrintWriter(fos);
            pw.write("源文件："+pathA+"\r\n");
            pw.write("对比文件："+pathB+"\r\n");
            pw.write("文本相似度："+similarity);
            pw.flush();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

}
