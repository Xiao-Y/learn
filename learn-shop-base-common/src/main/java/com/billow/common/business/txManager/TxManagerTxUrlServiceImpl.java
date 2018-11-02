package com.billow.common.business.txManager;

import org.springframework.beans.factory.annotation.Value;

/**
 * tx-manager的访问地址设置(暂不使用)
 *
 * @author liuyongtao
 * @create 2018-04-27 14:28
 */
//@Component
//public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {
@Deprecated
public class TxManagerTxUrlServiceImpl {


    @Value("${config.public.tx.manager.url}")
    private String url;

    //    @Override
    public String getTxUrl() {
//        System.out.println("config.public.tx.manager.url >>>>>>>>> " + url);
        return url;
    }
}

