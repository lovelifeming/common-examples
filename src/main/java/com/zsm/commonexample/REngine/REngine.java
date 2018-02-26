package com.zsm.commonexample.REngine;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/23 17:13.
 * @Modified By:
 */
public class REngine
{

    /**
     * 求一串数字的平均值，标准差和样本量，并将它们转成数据框返回
     * R语言函数实现：
     * getMeanSDN<-function(x){
     * data.frame(mean=mean(x),sd=sd(x),N=length(x))
     * }
     * <p>
     * 返回值数据框：
     * mean     sd      N
     * x.x      x.x      x.x
     *
     * @param filePath
     * @return
     */
    public static List calAverageAndStandardDeviationAndSampleSize(String filePath, int... arr)
        throws RserveException, REXPMismatchException
    {
        ArrayList<String> result = new ArrayList<>();
        RConnection rc = new RConnection();

        //直接路径是D:/test.R,变量路径是‘"D:\\test.R"’,文件名不能带中文,否则报：eval failed, request status: error code: 127
        //rc.eval("source('D:/test.R')");
        rc.assign("filePath", filePath);
        rc.eval("source(filePath)");

        StringBuilder sb = new StringBuilder("c(");
        int length = arr.length;
        for (int i = 0; i < length; i++)
        {
            sb.append(arr[i]);
            if (i != length - 1)
            {
                sb.append(",");
            }
        }
        sb.append(")");
        String data = sb.toString();//"c(3,5,6,8,9)";
        REXP rexp = rc.eval("getMeanSDN(" + data + ")");

        RList rList = rexp.asList();
        //获取所有的key值，[mean, sd, N]
        String keys = Arrays.toString(rList.keys());
        //获取所有的value值
        List list = new ArrayList<String>();
        Iterator iterator = rList.values().iterator();
        while (iterator.hasNext())
        {
            REXP temp = (REXP)iterator.next();
            list.add(temp.asString());
        }

        //根据索引位置获取key值
        REXP key = rList.at(0);
        double db = key.asDouble();

        //根据key获取均值mean
        REXP rexpMean = rList.at("mean");
        double mean = rexpMean.asDouble();

        //根据key获取标准差sd
        double sd = rList.at("sd").asDouble();

        //根据key获取样本量N
        int num = rList.at("N").asInteger();

        rc.close();
        result.add(keys);
        result.add(Arrays.toString(list.toArray()));
        return result;
    }

    /**
     * 调用自定义求和函数，R语言求和函数：
     * getSum<-function(x,y,z){
     * m = x + y +z
     * return(m)
     * }
     *
     * @param filePath
     * @return
     * @throws RserveException
     * @throws REXPMismatchException
     */
    public static int getThreeNumberSum(String filePath, int x, int y, int z)
        throws RserveException, REXPMismatchException
    {
        RConnection rc = new RConnection();
        rc.assign("file", filePath);
        rc.eval("source(file)");
        int sum = rc.eval("getSum(" + x + "," + y + "," + z + ")").asInteger();
        rc.close();
        return sum;
    }

    /**
     * 获取R语言版本信息
     *
     * @return
     * @throws RserveException
     * @throws REXPMismatchException
     */
    public static String getVersion()
        throws RserveException, REXPMismatchException
    {
        RConnection rc = new RConnection();
        String version = rc.eval("R.version.string").asString();
        System.out.println(version);
        //关闭RConnection连接
        rc.close();

        //关闭Rserve服务
        //rc.shutdown();
        return version;
    }

    /**
     * k-means算法,Java调用R里面的k-means算法
     *
     * @throws RserveException
     * @throws REXPMismatchException
     */
    public static void KMeans()
        throws RserveException, REXPMismatchException
    {
        RConnection rc = new RConnection();
        rc.eval("a=c(3,2,4,5,6,4,5,7,5,9,6,5,6,8)");
        //生成测试数据矩阵
        REXP mrex = rc.eval("x=matrix(a,byrow=T,nrow=7)");
        double[][] matrix = mrex.asDoubleMatrix();
        //执行k-means算法
        rc.eval("t=kmeans(x,2)");
        //得到中心
        REXP center_rex = rc.eval("t$centers");
        double[][] centers = center_rex.asDoubleMatrix();
        //得到cluster
        REXP cluster_rex = rc.eval("t$cluster");
        int[] cluster = cluster_rex.asIntegers();

        String yin = "\"";
        String code = "jpeg(file=" + yin + "myplot.jpeg" + yin + ")";
        System.out.print(code);
        //保存图片
        rc.eval(code);

        rc.eval("plot(x,col=t$cluster)");
        rc.eval("points(t$centers,pch=8,col=t$cluster)");//绘制图片
        rc.eval("dev.off()");

        for (int i = 0; i < centers.length; i++)
        {
            for (int j = 0; j < centers[0].length; j++)
            {
                System.out.print(centers[i][j] + " ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
        for (int i = 0; i < cluster.length; i++)
        {
            System.out.print(cluster[i] + "  ");
        }
        rc.close();
    }
}
