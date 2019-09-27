package com.llq.ai.baidu.utils;

import com.baidu.aip.imagesearch.AipImageSearch;
import com.llq.ai.baidu.config.BdConstant;
import com.llq.ai.baidu.factory.AipImageFactory;
import com.llq.ai.utils.FilesUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 相似图片工具类
 */
public class AipImageSearchUtil {

    private static Logger logger = LoggerFactory.getLogger(AipImageSearchUtil.class);

    private static AipImageSearch client = (AipImageSearch) AipImageFactory.getAipClient(BdConstant.AipImageSearch);

    /**
     * 入库
     * @param path 图片路径    type等于1和2时写图片路径，等于3时写url地址
     * @param brief 图片信息 "{\"name\":\"周杰伦\", \"id\":\"666\"}"
     * @param type  图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static JSONObject add(String path,String brief,Integer type){
        return add( path, brief, null,type);
    }

    /**
     * 入库
     * @param path 图片路径 type等于1和2时写图片路径，等于3时写url地址
     * @param brief 图片信息 "{\"name\":\"周杰伦\", \"id\":\"666\"}"
     * @param tags 标签 1 - 65535范围内的整数 样例："100,11" ；检索时可圈定分类维度进行检索，类似分类
     * @param type 图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static JSONObject add(String path,String brief,String tags,Integer type) {
        JSONObject res = null;
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("brief", brief);//"{\"name\":\"周杰伦\", \"id\":\"666\"}"
        if(tags != null){
            options.put("tags", tags);//"100,11"
        }
        if(type == BdConstant.IMAGEPATH){
            // 参数为本地路径
            res = client.similarAdd(path, options);
        }else if(type == BdConstant.BYTEIMAGE){
            // 参数为二进制数组
            byte[] file = FilesUtil.readFile(path);
            res = client.similarAdd(file, options);
        }else if(type == BdConstant.URLIMAGE){
            // 相似图检索—入库, 图片参数为远程url图片
            res = client.similarAddUrl(path, options);
        }else{
            logger.error("未找到相应处理类型");
            res = new JSONObject("未找到相应处理类型");
        }
        logger.info(res.toString(2));
        //System.out.println(res.toString(2));
        return res;
    }

    /**
     * 检索
     * @param path 图片路径 type等于1和2时写图片路径，等于3时写url地址
     * @param type 图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static void search(String path, Integer type){
        search(path,null,null,null,null,type);
    }
    /**
     * 检索
     * @param path 图片路径 type等于1和2时写图片路径，等于3时写url地址
     * @param pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，
     *                       例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     * @param rn 分页功能，截取条数，例：250
     * @param type 图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static void search(String path,String pn,String rn, Integer type){
        search(path,null,null,pn,rn,type);
    }
    /**
     *  检索
     * @param path 图片路径 type等于1和2时写图片路径，等于3时写url地址
     * @param tags 标签 1 - 65535范围内的整数 样例："100,11" ；检索时可圈定分类维度进行检索，类似分类
     * @param tag_logic 检索时tag之间的逻辑， 0：逻辑and，1：逻辑or
     * @param pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，
     *                       例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     * @param rn 分页功能，截取条数，例：250
     * @param type 图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static void search(String path,String tags,String tag_logic,String pn,String rn, Integer type){
        JSONObject res = null;
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        if(tags!=null){
            options.put("tags", tags);
        }
        if(tag_logic!=null){
            options.put("tag_logic", tag_logic);
        }
        if(pn!=null){
            options.put("pn", pn);
        }
        if(rn!=null){
            options.put("rn", rn);
        }

        if(type == BdConstant.IMAGEPATH){
            // 参数为本地路径
            res = client.similarSearch(path, options);
        }else if(type == BdConstant.BYTEIMAGE){
            // 参数为二进制数组
            byte[] file = FilesUtil.readFile(path);
            res = client.similarSearch(file, options);
        }else if(type == BdConstant.URLIMAGE){
            // 相似图检索—入库, 图片参数为远程url图片
            res = client.similarSearchUrl(path, options);
        }else{
            logger.error("未找到相应处理类型");
            res = new JSONObject("未找到相应处理类型");
        }
        logger.info(res.toString(2));
        //System.out.println(res.toString(2));
    }


    /**
     * 更新
     * @param path 图片路径    type等于1和2时写图片路径，等于3时写url地址
     * @param brief  更新的图片信息 "{\"name\":\"周杰伦\", \"id\":\"666\"}"
     * @param type  图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static void update(String path,String brief,Integer type){
        add( path, brief, null,type);
    }

    /**
     * 更新
     * @param path 图片路径 type等于1和2时写图片路径，等于3时写url地址
     * @param brief 更新的图片信息 "{\"name\":\"周杰伦\", \"id\":\"666\"}"
     * @param tags 更新的图片标签 1 - 65535范围内的整数 样例："100,11" ；检索时可圈定分类维度进行检索，类似分类
     * @param type 图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static void update(String path,String brief,String tags,Integer type) {
        JSONObject res = null;
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("brief", brief);//"{\"name\":\"周杰伦\", \"id\":\"666\"}"
        if(tags != null){
            options.put("tags", tags);//"100,11"
        }
        if(type == BdConstant.IMAGEPATH){
            // 参数为本地路径
            res = client.similarUpdate(path, options);
        }else if(type == BdConstant.BYTEIMAGE){
            // 参数为二进制数组
            byte[] file = FilesUtil.readFile(path);
            res = client.similarUpdate(file, options);
        }else if(type == BdConstant.URLIMAGE){
            // 相似图检索—入库, 图片参数为远程url图片
            res = client.similarUpdateUrl(path, options);
        }else{
            logger.error("未找到相应处理类型");
            res = new JSONObject("未找到相应处理类型");
        }
        logger.info(res.toString(2));
        //System.out.println(res.toString(2));
    }

    /**
     *  删除
     * @param contSign 图片签名，入库时有返回
     */
    public static void delete(String contSign) {
        delete(null,contSign,null);
    }
    /**
     *  删除
     * @param path 图片路径 type等于1和2时写图片路径，等于3时写url地址
     * @param type 图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static void delete(String path,Integer type) {
        delete(path,null,type);
    }
    /**
     * 删除
     * @param path 图片路径 type等于1和2时写图片路径，等于3时写url地址
     * @param contSign 图片签名，入库时有返回
     * @param type 图片处理类型  1：本地路径 2：二进制数组 3：远程url图片
     */
    public static void delete(String path,String contSign,Integer type) {
        JSONObject res = null;
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        if(contSign!=null){
            res = client.similarDeleteBySign(contSign, options);
        }else{
            if(type == BdConstant.IMAGEPATH){
                // 参数为本地路径
                res = client.similarDeleteByImage(path, options);
            }else if(type == BdConstant.BYTEIMAGE){
                // 参数为二进制数组
                byte[] file = FilesUtil.readFile(path);
                res = client.similarDeleteByImage(file, options);
            }else if(type == BdConstant.URLIMAGE){
                // 相似图检索—入库, 图片参数为远程url图片
                res = client.similarDeleteByUrl(path, options);
            }else{
                logger.error("未找到相应处理类型");
                res = new JSONObject("未找到相应处理类型");
            }
        }
        logger.info(res.toString(2));
        //System.out.println(res.toString(2));
    }

}
