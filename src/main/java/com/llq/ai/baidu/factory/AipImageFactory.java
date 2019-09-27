package com.llq.ai.baidu.factory;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.imagesearch.AipImageSearch;
import com.llq.ai.baidu.config.BdConstant;

/**
 * 获取AipImage图像搜索Java客户端工厂
 */
public class AipImageFactory {

        private static BaseClient client = null;
        //建立连接的超时时间
        private static final int connectionTimeout = 2000;
        //通过打开的连接传输数据的超时时间
        private static final int socketTimeout = 60000;
        /**
         * 获取封装的AipImage
         * @return
         */
        public static BaseClient getAipClient(Integer type){
                if(client ==null){
                    if(type == BdConstant.AipImageSearch){
                        client = new AipImageSearch(BdConstant.APP_ID, BdConstant.API_KEY, BdConstant.SECRET_KEY);
                    }else if(type == BdConstant.AipImageClassify){
                        client = new AipImageClassify(BdConstant.CAPP_ID, BdConstant.CAPI_KEY, BdConstant.CSECRET_KEY);
                    }
                    // 可选：设置网络连接参数
                    client.setConnectionTimeoutInMillis(connectionTimeout);
                    client.setSocketTimeoutInMillis(socketTimeout);
                    // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
                    //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
                    //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

                    // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
                    // 也可以直接通过jvm启动参数设置此环境变量
                    System.setProperty("aip.log4j.conf", "log4j.properties");
                }
                return client;
        }
}
