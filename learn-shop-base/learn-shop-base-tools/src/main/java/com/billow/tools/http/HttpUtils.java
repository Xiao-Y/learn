//package com.billow.tools.http;
//
//import com.billow.tools.enums.ResCodeEnum;
//import com.billow.tools.resData.BaseResponse;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.security.GeneralSecurityException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * http处理工具
// *
// * @author liuyongtao
// * @create 2018-11-06 9:50
// */
//public class HttpUtils {
//
//    /**
//     * 获取返回对象中的返回值
//     *
//     * @param baseResponse 返回对象
//     * @return T 返回值
//     * @author LiuYongTao
//     * @date 2018/11/6 10:02
//     */
//    public static <T> T getResData(BaseResponse<T> baseResponse) {
//        if (baseResponse != null) {
//            String resCode = baseResponse.getResCode();
//            if (!ResCodeEnum.OK.equals(resCode)) {
//                throw new RuntimeException(baseResponse.toString());
//            } else {
//                return baseResponse.getResData();
//            }
//        }
//        return null;
//    }
//
//    private static PoolingHttpClientConnectionManager connMgr;
//    private static RequestConfig requestConfig;
//    private static final int MAX_TIMEOUT = 7000;
//
//    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
//
//    static {
//        // 设置连接池
//        connMgr = new PoolingHttpClientConnectionManager();
//        // 设置连接池大小
//        connMgr.setMaxTotal(100);
//        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
//        // Validate connections after 1 sec of inactivity
//        connMgr.setValidateAfterInactivity(1000);
//        RequestConfig.Builder configBuilder = RequestConfig.custom();
//        // 设置连接超时
//        configBuilder.setConnectTimeout(MAX_TIMEOUT);
//        // 设置读取超时
//        configBuilder.setSocketTimeout(MAX_TIMEOUT);
//        // 设置从连接池获取连接实例的超时
//        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
//
//        requestConfig = configBuilder.build();
//    }
//
//    /**
//     * 发送 GET 请求（HTTP），不带输入数据
//     *
//     * @param url
//     * @return
//     */
//    public static String doGet(String url) {
//        return doGet(url, new HashMap<>());
//    }
//
//    /**
//     * 发送 GET 请求（HTTP），K-V形式
//     *
//     * @param url
//     * @param params
//     * @return
//     */
//    public static String doGet(String url, Map<String, Object> params) {
//        String apiUrl = url;
//        StringBuffer param = new StringBuffer();
//        int i = 0;
//        for (String key : params.keySet()) {
//            if (i == 0)
//                param.append("?");
//            else
//                param.append("&");
//            param.append(key).append("=").append(params.get(key));
//            i++;
//        }
//        apiUrl += param;
//        String result = null;
//        HttpClient httpClient;
//        if (apiUrl.startsWith("https")) {
//            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
//                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
//        } else {
//            httpClient = HttpClients.createDefault();
//        }
//        try {
//            HttpGet httpGet = new HttpGet(apiUrl);
//            HttpResponse response = httpClient.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                InputStream instream = entity.getContent();
//                result = IOUtils.toString(instream, "UTF-8");
//            }
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//        return result;
//    }
//
//    /**
//     * 发送 POST 请求（HTTP），不带输入数据
//     *
//     * @param apiUrl
//     * @return
//     */
//    public static String doPost(String apiUrl) {
//        return doPost(apiUrl, new HashMap<>());
//    }
//
//    /**
//     * 发送 POST 请求，K-V形式
//     *
//     * @param apiUrl API接口URL
//     * @param params 参数map
//     * @return String
//     */
//    public static String doPost(String apiUrl, Map<String, Object> params) {
//        CloseableHttpClient httpClient;
//        if (apiUrl.startsWith("https")) {
//            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
//                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
//        } else {
//            httpClient = HttpClients.createDefault();
//        }
//        String httpStr;
//        HttpPost httpPost = new HttpPost(apiUrl);
//        CloseableHttpResponse response = null;
//
//        try {
//            httpPost.setConfig(requestConfig);
//            List<NameValuePair> pairList = new ArrayList<>(params.size());
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
//                pairList.add(pair);
//            }
//            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
//            response = httpClient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            httpStr = EntityUtils.toString(entity, "UTF-8");
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        } finally {
//            if (response != null) {
//                try {
//                    EntityUtils.consume(response.getEntity());
//                } catch (IOException e) {
//                    logger.error(e.getMessage());
//                    throw new RuntimeException(e.getMessage());
//                }
//            }
//        }
//        return httpStr;
//    }
//
//    /**
//     * 发送 POST 请求，JSON形式
//     *
//     * @param apiUrl
//     * @param json   json对象
//     * @return String
//     */
//    public static String doPost(String apiUrl, Object json) {
//        CloseableHttpClient httpClient;
//        if (apiUrl.startsWith("https")) {
//            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
//                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
//        } else {
//            httpClient = HttpClients.createDefault();
//        }
//        String httpStr;
//        HttpPost httpPost = new HttpPost(apiUrl);
//        CloseableHttpResponse response = null;
//
//        try {
//            httpPost.setConfig(requestConfig);
//            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
//            stringEntity.setContentEncoding("UTF-8");
//            stringEntity.setContentType("application/json");
//            httpPost.setEntity(stringEntity);
//            response = httpClient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            httpStr = EntityUtils.toString(entity, "UTF-8");
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        } finally {
//            if (response != null) {
//                try {
//                    EntityUtils.consume(response.getEntity());
//                } catch (IOException e) {
//                    logger.error(e.getMessage());
//                    throw new RuntimeException(e.getMessage());
//                }
//            }
//        }
//        return httpStr;
//    }
//
//    /**
//     * 创建SSL安全连接
//     *
//     * @return
//     */
//    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
//        SSLConnectionSocketFactory sslsf;
//        try {
////            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
////
////                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
////                    return true;
////                }
////            }).build();
////            sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
////
////                @Override
////                public boolean verify(String arg0, SSLSession arg1) {
////                    return true;
////                }
////            });
//
//            SSLContext sslContext = new SSLContextBuilder()
//                    .loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true)
//                    .build();
//            sslsf = new SSLConnectionSocketFactory(sslContext, (String arg0, SSLSession arg1) -> true);
//        } catch (GeneralSecurityException e) {
//            logger.error(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//        return sslsf;
//    }
//}
