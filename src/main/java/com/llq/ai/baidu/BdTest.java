package com.llq.ai.baidu;

import com.llq.ai.baidu.config.BdConstant;
import com.llq.ai.baidu.utils.AipImageClassifyUtil;
import com.llq.ai.baidu.utils.AipImageSearchUtil;
import org.json.JSONObject;

public class BdTest {

    public static void main(String[] args) {

        BdTest t = new BdTest();
        //主实体识别
        t.subjectImage();
        //入库
        //t.add();
        //检索
        //t.search();
        //更新
        //t.update();
        //删除
        //t.delete();
    }

    public void subjectImage(){
        String path = "D:/work/ai/images/微信图片_20190927091825.jpg";
        AipImageClassifyUtil.subjectImage(path,BdConstant.BYTEIMAGE);
    }


    public  void add(){
        JSONObject brief = new JSONObject();
        //图片1
        brief.put("id","1");
        brief.put("name","测试");
        String image1 = "D:/Desktop/图片/timg5.jpg";
        brief.put("path",image1);
        //System.out.println(brief.toString());
        AipImageSearchUtil.add(image1,brief.toString(), BdConstant.BYTEIMAGE);

        //图片2
        brief.put("id","2");
        brief.put("name","测试2");
        String image2 = "D:/Desktop/图片/500219111_banner.jpg";
        brief.put("path",image2);
        //System.out.println(brief.toString());
        AipImageSearchUtil.add(image2,brief.toString(), BdConstant.BYTEIMAGE);

        //图片3
        brief.put("id","3");
        brief.put("name","测试3");
        String image3 = "D:/Desktop/图片/SLAMDUNK2.jpg";
        brief.put("path",image3);
        //System.out.println(brief.toString());
        AipImageSearchUtil.add(image3,brief.toString(), BdConstant.BYTEIMAGE);

        //图片4
        brief.put("id","4");
        brief.put("name","测试4");
        String image4 = "D:/Desktop/头像/icon.jpg";
        brief.put("path",image4);
        //System.out.println(brief.toString());
        AipImageSearchUtil.add(image4,brief.toString(), BdConstant.BYTEIMAGE);
    }

    public void search(){
        String path = "D:/work/ai/images/微信图片_20190927091825.jpg";
        AipImageSearchUtil.search(path, BdConstant.BYTEIMAGE);
    }

    public void update(){

    }

    public void delete(){
        AipImageSearchUtil.delete("2551948783,4216291306");
    }
}
