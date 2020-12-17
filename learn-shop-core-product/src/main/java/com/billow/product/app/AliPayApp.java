//package com.billow.product.app;
//
//import com.billow.alipay.scan.service.AliPayScanService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author liuyongtao
// * @create 2019-12-27 22:23
// */
//public class AliPayApp {
//
//    @Autowired
//    private AliPayScanService aliPayScanService;
//
//
//    @PostMapping("/notify")
//    public void notify(HttpServletRequest request) {
//        System.out.println("支付通知、、、、、start");
//        aliPayScanService.notifyUrl(request, null);
//        System.out.println("支付通知、、、、、end");
//
//    }
//}
