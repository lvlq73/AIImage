package com.llq.ai.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilesUtil {

    private static Logger logger = LoggerFactory.getLogger(FilesUtil.class);

    /**
     * 读取二进制文件
     * @param path 图片路径
     * @return
     */
    public static byte[] readFile(String path){
        byte[] bytes = null;
        FileInputStream fis = null;
        try{
            File file = new File(path);
            fis = new FileInputStream(file);
            bytes = new byte[(int) file.length()];
            fis.read(bytes);
        }catch(IOException e){
            logger.error(e.getMessage());
        }finally{
            try {
                fis.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return bytes;
    }

    /**
      * 获取一个文件夹下的所有文件全路径
      * @param path
      * @param listFileName
      */
    public static void getAllFileName(String path,List<String> listFileName){
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null){
            String [] completNames = new String[names.length];
            for(int i=0;i<names.length;i++){
            completNames[i]=path+names[i];
            }
            listFileName.addAll(Arrays.asList(completNames));
        }
        for(File a:files){
            if(a.isDirectory()){//如果文件夹下有子文件夹，获取子文件夹下的所有文件全路径。
                getAllFileName(a.getAbsolutePath()+"/",listFileName);
            }
        }
    }

    public static void main(String[] args){
        List<String> listFileName = new ArrayList<String>();
        getAllFileName("E:/otherWork/ai/图像样本库20190926/皮革图片库/无纹路组/",listFileName);
        getAllFileName("E:/otherWork/ai/图像样本库20190926/皮革图片库/有纹路组/",listFileName);
        for(String name:listFileName){
                System.out.println(name);
                System.out.println(name.substring(name.lastIndexOf("/")+1));
        }
        System.out.println(listFileName.size());
    }
}
