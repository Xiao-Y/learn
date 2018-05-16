package com.billow.common.TxManager;

import org.springframework.beans.factory.annotation.Value;

/**
 * tx-manager的访问地址设置
 *
 * @author liuyongtao
 * @create 2018-04-27 14:28
 */
//@Component
//public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {
public class TxManagerTxUrlServiceImpl {


    @Value("${config.public.tx.manager.url}")
    private String url;

//    @Override
    public String getTxUrl() {
//        System.out.println("config.public.tx.manager.url >>>>>>>>> " + url);
        return url;
    }
}

