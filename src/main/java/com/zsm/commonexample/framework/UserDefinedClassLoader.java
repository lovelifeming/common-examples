package com.zsm.commonexample.framework;

import com.zsm.commonexample.util.CommonUtils;

import java.io.*;


/**
 * 自定义类加载器
 * ClassLoader.loadClass()：只做了一件事情就是将.class文件加载到jvm中，不会执行static中的内容,只有在newInstance才会去初始化类执行static块。
 * Class.forName()：将类的.class文件加载到jvm中之外，还会对类进行解析，初始化类执行类中的static块。
 * Class.forName(name, initialize, loader)带参函数也可控制是否加载static块，并且只有调用了newInstance()方法才调用构造函数，创建类的对象。
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/13.
 * @Modified By:
 */
public class UserDefinedClassLoader extends ClassLoader
{
    private String root;

    /**
     * 查找类并将类加载到jvm里面
     *
     * @param root      加载类的根目录
     * @param className 加载类的全限定性名称
     * @return
     * @throws ClassNotFoundException
     */
    public Class<?> findClass(String root, String className)
        throws ClassNotFoundException
    {
        this.root = root;
        return findClass(className);
    }

    /**
     * 查找类并将类加载到jvm里面
     *
     * @param className 加载类的全限定性名称
     * @return
     * @throws ClassNotFoundException
     */
    public Class<?> findClass(String className)
        throws ClassNotFoundException
    {
        byte[] classData = loadClassData(className);
        if (classData == null)
        {
            throw new ClassNotFoundException();
        }
        else
        {
            return defineClass(className, classData, 0, classData.length);
        }
    }

    private byte[] loadClassData(String className)
    {
        String fileName = root + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        InputStream ins = null;
        try
        {
            ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1)
            {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            CommonUtils.closeStream(ins);
        }
        return null;
    }

    public String getRoot()
    {
        return root;
    }

    /**
     * 设置加载类的根目录
     *
     * @param root 加载类的根目录
     */
    public void setRoot(String root)
    {
        this.root = root;
    }
}
