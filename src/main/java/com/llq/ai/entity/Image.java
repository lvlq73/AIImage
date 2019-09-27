package com.llq.ai.entity;

import java.util.Date;

/**
 * 存储图片实体类
 */
public class Image {
    //id
    public Integer imageID;
    //编码
    public String imageCode;
    //名称
    public String imageName;
    //路径
    public String imagePath;
    //百度存储图片的编码
    public String baiduCode;
    //阿里云存储图片的编码
    public String aliyunCode;
    //创建时间
    public Date createDate;
}
