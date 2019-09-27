package com.llq.ai.sql;

import com.llq.ai.entity.Image;
import com.llq.ai.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class SqlUtil {

    private static Logger logger = LoggerFactory.getLogger(SqlUtil.class);

    private static Connection conn = null;

    static {
        conn = getConn();
    }
    public static Connection getConn(){
        if(conn == null){
            try {
                Class.forName(Constant.driver);
                conn = DriverManager.getConnection(Constant.url,Constant.user,Constant.password);
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage());
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return conn;
    }

    /**
     * 插入操作
     * @param list
     */
    public static void insert(List<Image> list){
        //conn = getConn();
        PreparedStatement st = null;
        try {
            if(list!=null && list.size() > 0){
                String sql = "insert ImageInfo(imageCode,imageName,imagePath,baiduCode,aliyunCode,createDate)  values(?,?,?,?,?,getdate())";
                st = conn.prepareStatement(sql);
                if(list.size() > 1){
                        int count=0;//记录条数
                        for(Image obj:list){
                            st.setString(1,obj.imageCode);
                            st.setString(2,obj.imageName);
                            st.setString(3,obj.imagePath);
                            st.setString(4,obj.baiduCode);
                            st.setString(5,obj.aliyunCode);
                            st.addBatch();
                            count++;
                            //200条执行一次
                            if(count%200==0){
                                st.executeBatch();
                                st.clearBatch();
                            }
                        }
                        st.executeBatch();
                }else{
                    Image image = list.get(0);
                    st.setString(1,image.imageCode);
                    st.setString(2,image.imageName);
                    st.setString(3,image.imagePath);
                    st.setString(4,image.baiduCode);
                    st.setString(5,image.aliyunCode);
                    st.executeUpdate();
                }
            }
        } catch (SQLException e) {
           logger.error(e.getMessage());
        }finally {
            close(st,null);
            logger.info("执行成功");
        }
    }

    public static void update(List<Image> list){
        PreparedStatement st = null;
    }

    /**
     * 关闭资源
     * @param st
     * @param rs
     */
    public static void close(Statement st, ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch (Exception e) {
                logger.error(e.getMessage());
            }
            rs = null;
        }
        if(st!=null){
            try{
                st.close();
            }catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
