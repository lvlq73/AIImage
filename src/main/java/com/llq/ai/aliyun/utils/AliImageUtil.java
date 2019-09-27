package com.llq.ai.aliyun.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 阿里云图片识别工具类（布料）
 */
public class AliImageUtil {

    private static Logger logger = LoggerFactory.getLogger(AliImageUtil.class);

    private final static String CHARSET_UTF8 = "utf8";
    private final static String ALGORITHM = "UTF-8";

    private final static String accessKeyId = "你的accessKeyId";
    private final static String keySecrect = "你的keySecrect";
    //图像搜索实例名称
    private final static String instanceName = "你的实例名称";
    //请求地址
    private final static String domain = "imagesearch.cn-shanghai.aliyuncs.com";

    /**
     * 新增图片
     * @param file
     * @param productId 商品id，最多支持 512个字符。说明： 一个商品可有多张图片。
     * @param picName 图片名称，最多支持 512个字符。
     *                                  说明：
                                        1. ProductId + PicName唯一确定一张图片。
                                        2. 如果多次添加图片具有相同的ProductId + PicName，以最后一次添加为准，前面添加的的图片将被覆盖。
     * @throws Exception
     */
    public static void addImage(String file,String productId,String picName) throws Exception {
        byte[] bytes2 = getBytes(file);
        Base64 base64 = new Base64();

        List<NameValuePair> params = new ArrayList<>(4);
        params.add(new BasicNameValuePair("InstanceName", instanceName));
        params.add(new BasicNameValuePair("PicName", picName));
        params.add(new BasicNameValuePair("ProductId", productId));
        params.add(new BasicNameValuePair("PicContent", base64.encodeToString(bytes2)));

        HttpEntity entity = new UrlEncodedFormEntity(params, CHARSET_UTF8);
        String content = convert(entity.getContent(), Charset.forName(CHARSET_UTF8));

        Map<String, String> headers = new HashMap<>();
        String signatureStr = buildSignatureStr("add", content, headers);

        byte[] signBytes = hmacSHA1Signature(keySecrect, signatureStr);
        String signature = newStringByBase64(signBytes);
        String authorization = "acs " + accessKeyId + ":" + signature;
        headers.put("authorization", authorization);

        String url = "http://" + domain + "/v2/image/add";
        HttpPost httpPost = new HttpPost(url);
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }

        httpPost.setEntity(entity);
        String result = access(httpPost);
        //System.out.println(result);
        logger.info(result);
    }

    /**
     * 查询图片
     * @param file 文件途径
     * @throws Exception
     */
    public static void searchImage(String file) throws Exception {
            searchImage(file,null,null);
    }

    /**
     * 查询图片
     * @param file 文件途径
     * @param start 返回结果的起始位置。取值范围：0-499。默认值：0。
     * @param num 返回结果的数目。取值范围：1-100。默认值：10。
     * @throws Exception
     */
    public static void searchImage(String file,Integer start,Integer num) throws Exception {
        byte[] bytes2 = getBytes(file);
        Base64 base64 = new Base64();

        List<NameValuePair> params = new ArrayList<>(4);
        params.add(new BasicNameValuePair("InstanceName", instanceName));
        params.add(new BasicNameValuePair("PicContent", base64.encodeToString(bytes2)));
        if(num!=null){
            params.add(new BasicNameValuePair("Num", num.toString()));
        }
        if(start!=null){
            params.add(new BasicNameValuePair("Start", start.toString()));
        }

        HttpEntity entity = new UrlEncodedFormEntity(params, CHARSET_UTF8);
        String content = convert(entity.getContent(), Charset.forName(CHARSET_UTF8));

        Map<String, String> headers = new HashMap<>();
        String signatureStr = buildSignatureStr("search", content, headers);

        byte[] signBytes = hmacSHA1Signature(keySecrect, signatureStr);
        String signature = newStringByBase64(signBytes);
        String authorization = "acs " + accessKeyId + ":" + signature;
        headers.put("authorization", authorization);

        String url = "http://" + domain + "/v2/image/search";
        HttpPost httpPost = new HttpPost(url);
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }

        httpPost.setEntity(entity);
        String result = access(httpPost);
        logger.info(result);
    }

    /**
     * 删除图片
     * @param productId 删除商品id下所有图片
     * @throws Exception
     */
    public static void deleteImage(String productId) throws Exception {
        deleteImage(productId,null);
    }

    /**
     * 删除图片
     * @param productId 商品id
     * @param picName 非必填，如果填了，删除图片根据 productId+picName来删除
     * @throws Exception
     */
    public static void deleteImage(String productId,String picName) throws Exception {
        List<NameValuePair> params = new ArrayList<>(3);
        params.add(new BasicNameValuePair("InstanceName", instanceName));
        params.add(new BasicNameValuePair("ProductId", productId));
        if(picName!=null){
            params.add(new BasicNameValuePair("PicName", picName));
        }

        HttpEntity entity = new UrlEncodedFormEntity(params, CHARSET_UTF8);
        String content = convert(entity.getContent(), Charset.forName(CHARSET_UTF8));

        Map<String, String> headers = new HashMap<>();
        String signatureStr = buildSignatureStr("delete", content, headers);

        byte[] signBytes = hmacSHA1Signature(keySecrect, signatureStr);
        String signature = newStringByBase64(signBytes);
        String authorization = "acs " + accessKeyId + ":" + signature;
        headers.put("authorization", authorization);

        String url = "http://" + domain + "/v2/image/delete";
        HttpPost httpPost = new HttpPost(url);
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }

        httpPost.setEntity(entity);
        String result = access(httpPost);
        logger.info(result);
    }

    private static String getMd5(String body) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        return base64en.encode(md5.digest(body.getBytes(CHARSET_UTF8)));
    }

    private static String getGMT() {
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(cd.getTime());
    }


    private static String generateSignatureNonce() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static String buildSignatureStr(String operation, String postContent, Map<String, String> headers) {
        String data = "POST\n";
        String accept = "application/json";
        data += accept + "\n";

        String contentMd5;
        try {
            contentMd5 = getMd5(postContent);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
        data += contentMd5 + "\n";

        String contentType = "application/x-www-form-urlencoded; charset=" + ALGORITHM;
        data += contentType + "\n";

        String gmt = getGMT();
        data += gmt + "\n";

        String method = "HMAC-SHA1";
        data += "x-acs-signature-method:" + method + "\n";

        String signatureNonce = generateSignatureNonce();
        data += "x-acs-signature-nonce:" + signatureNonce + "\n";

        String apiVersion = "2019-03-25";
        data += "x-acs-version:" + apiVersion + "\n";
        data += "/v2/image/" + operation;

        headers.put("x-acs-version", apiVersion);
        headers.put("x-acs-signature-method", method);
        headers.put("x-acs-signature-nonce", signatureNonce);

        headers.put("accept", accept);
        headers.put("content-md5", contentMd5);
        headers.put("content-type", contentType);
        headers.put("date", gmt);

        return data;
    }

    private static byte[] hmacSHA1Signature(String secret, String baseString) throws Exception {
        if (secret == null || secret.length() == 0) {
            throw new IOException("secret can not be empty");
        }
        if (baseString == null || baseString.length() == 0) {
            return null;
        }
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), ALGORITHM);
        mac.init(keySpec);
        return mac.doFinal(baseString.getBytes(CHARSET_UTF8));
    }

    private static String newStringByBase64(byte[] bytes)
            throws UnsupportedEncodingException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(Base64.encodeBase64(bytes, false), CHARSET_UTF8);
    }

    private static String access(HttpRequestBase httpRequest) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse response;
        String result;
        try {
            httpRequest.setHeader("accept-encoding", "");
            response = client.execute(httpRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity);
            } else {
                System.out.println(response.getStatusLine().getStatusCode());
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity);
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    private static String convert(InputStream inputStream, Charset charset) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    private static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            // picture max size is 2MB
            ByteArrayOutputStream bos = new ByteArrayOutputStream(2000 * 1024);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
