//package com.billow.search.config;
//
//import com.billow.tools.utlis.StringUtils;
//import org.apache.http.HttpHost;
//import org.apache.http.client.config.RequestConfig;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//import java.util.Objects;
//
//@Configuration
//public class ElasticsearchRestClient {
//    /**
//     * 超时时间设为5分钟
//     */
//    private static final int TIME_OUT = 5 * 60 * 1000;
//    private static final int ADDRESS_LENGTH = 2;
//    private static final String HTTP_SCHEME = "http";
//
//    @Value("${elasticsearch.ip}")
//    String[] ipAddress;
//
//    @Bean
//    public RestClientBuilder restClientBuilder() {
//        System.err.println(ipAddress);
//        HttpHost[] hosts = Arrays.stream(ipAddress)
//                .map(this::makeHttpHost)
//                .filter(Objects::nonNull)
//                .toArray(HttpHost[]::new);
//        return RestClient.builder(hosts);
//    }
//
//    @Bean(name = "highLevelClient")
//    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
//        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setSocketTimeout(TIME_OUT));
//        //TODO 此处可以进行其它操作
//        return new RestHighLevelClient(restClientBuilder);
//    }
//
//
//    private HttpHost makeHttpHost(String s) {
//        assert StringUtils.isNotEmpty(s);
//        String[] address = s.split(":");
//        if (address.length == ADDRESS_LENGTH) {
//            String ip = address[0];
//            int port = Integer.parseInt(address[1]);
//            System.err.println(ip + "+" + port);
//            return new HttpHost(ip, port, HTTP_SCHEME);
//        } else {
//            return null;
//        }
//    }
//}