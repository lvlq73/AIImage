package com.llq.ai.aliyun;

import com.llq.ai.aliyun.utils.AliImageTYUtil;
import com.llq.ai.aliyun.utils.AliImageUtil;
import com.llq.ai.entity.Image;
import com.llq.ai.utils.FilesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：类描述
 * @Author ：llq
 * @CreateDate：2019/9/27 10:50
 */
public class AliTest {

    private static String  filePath = "D:/work/ai/图像样本库20190926/皮革图片库/无纹路组/";
    private static String  filePath2 = "D:/work/ai/图像样本库20190926/皮革图片库/有纹路组/";
    //单张照片
    private static String path = "D:/work/ai/图像样本库20190926/皮革图片库/167-有纹路样本/室内暗光4.jpg";

    public static void main(String[] args) throws Exception {
        AliTest test = new AliTest();
        //test.add();
        test.search();
    }

    public void add() throws Exception {
        List<String> files = new ArrayList<String>();
        FilesUtil.getAllFileName(filePath,files);
        FilesUtil.getAllFileName(filePath2,files);
        if(files!=null && files.size()>0){
            List<Image> insertList = new ArrayList<>();
            for(String file:files){
                String imageCode = "test";
                String imageName =file.substring(file.lastIndexOf("/")+1);
                //阿里云入库
                AliImageTYUtil.addImage(file,imageCode,imageName);
            }
        }
    }

    public void search() throws Exception {
        AliImageTYUtil.searchImage(path,0,3);
    }
}
