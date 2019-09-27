package com.llq.ai.service;

import com.llq.ai.aliyun.utils.AliImageUtil;
import com.llq.ai.baidu.config.BdConstant;
import com.llq.ai.baidu.utils.AipImageSearchUtil;
import com.llq.ai.entity.Image;
import com.llq.ai.sql.SqlUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImageService {

    public void add(List<String> files) throws Exception {
        if(files!=null && files.size()>0){
            List<Image> insertList = new ArrayList<>();
            int index=0;
            for(String file:files){
                Image image = new Image();
                image.imageCode = "test"+index;
                image.imageName =file.substring(file.lastIndexOf("/")+1);
                image.imagePath = file;
                //百度入库
                JSONObject res = AipImageSearchUtil.add(file,image.imageCode, BdConstant.BYTEIMAGE);
                image.baiduCode = res.getString("cont_sign");
                //阿里云入库
                AliImageUtil.addImage(file,image.imageCode,image.imageName);
                image.aliyunCode = image.imageCode;
                insertList.add(image);
                index++;
            }
            SqlUtil.insert(insertList);
        }
    }
}
