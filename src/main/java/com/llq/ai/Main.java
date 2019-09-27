package com.llq.ai;

import com.llq.ai.aliyun.utils.AliImageUtil;
import com.llq.ai.baidu.config.BdConstant;
import com.llq.ai.baidu.utils.AipImageSearchUtil;
import com.llq.ai.service.ImageService;
import com.llq.ai.utils.FilesUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String  filePath = "E:/otherWork/ai/图像样本库20190926/皮革图片库/无纹路组/";
    private static String  filePath2 = "E:/otherWork/ai/图像样本库20190926/皮革图片库/有纹路组/";
    //单张照片
    private static String path = "E:/otherWork/ai/图像样本库20190926/皮革图片库/167-有纹路样本/室外3.jpg";

    public static void main(String[] args) {
        //add();
        search();
    }

    public static void search(){
        //百度
        AipImageSearchUtil.search(path,"0","3", BdConstant.BYTEIMAGE);
        //阿里
        try {
            AliImageUtil.searchImage(path,0,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void add(){
        List<String> listFileName = new ArrayList<String>();
        FilesUtil.getAllFileName(filePath,listFileName);
        FilesUtil.getAllFileName(filePath2,listFileName);
        //入库
        ImageService service = new ImageService();
        try {
            service.add(listFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
