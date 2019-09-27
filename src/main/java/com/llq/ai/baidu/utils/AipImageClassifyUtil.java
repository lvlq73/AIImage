package com.llq.ai.baidu.utils;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.llq.ai.baidu.config.BdConstant;
import com.llq.ai.baidu.factory.AipImageFactory;
import com.llq.ai.utils.FilesUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @Description：图像主体识别工具类
 * @Author ：llq
 * @CreateDate：2019/9/27 9:34
 */
public class AipImageClassifyUtil {

    private static Logger logger = LoggerFactory.getLogger(AipImageClassifyUtil.class);

    private static AipImageClassify client = (AipImageClassify) AipImageFactory.getAipClient(BdConstant.AipImageClassify);

    /**
     * 主实体识别
     * @param filePath 图片路径
     * @param type 图片处理类型  1：本地路径 2：二进制数组
     */
    public static void subjectImage(String filePath,Integer type) {
        subjectImage( filePath,null, type);
    }

    /**
     *  主实体识别
     * @param filePath 图片路径
     * @param with_face  0 - 不带人脸区域 1 - 带人脸区域
     *  如果检测主体是人，主体区域是否带上人脸部分，0-不带人脸区域，其他-带人脸区域，裁剪类需求推荐带人脸，检索/识别类需求推荐不带人脸。默认取1，带人脸。
     * @param type 图片处理类型  1：本地路径 2：二进制数组
     */
    public static void subjectImage(String filePath,String with_face,Integer type) {
        JSONObject res = null;
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        if(with_face != null){
            options.put("with_face", with_face);
        }
        if(type == BdConstant.IMAGEPATH){
            // 参数为本地路径
            res = client.objectDetect(filePath, options);
        }else if(type == BdConstant.BYTEIMAGE){
            // 参数为二进制数组
            byte[] file = FilesUtil.readFile(filePath);
            res = client.objectDetect(file, options);
        }else{
            logger.error("未找到相应处理类型");
            res = new JSONObject("未找到相应处理类型");
        }
        logger.info(res.toString(2));
    }
}
