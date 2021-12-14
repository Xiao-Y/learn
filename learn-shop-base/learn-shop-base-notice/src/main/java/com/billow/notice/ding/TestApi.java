package com.billow.notice.ding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestApi
{

    @Autowired
    private SendDingService sendDingService;

    @GetMapping("/sendText")
    public String sendText()
    {
        SendRequestParam.Text text = new SendRequestParam.Text();
        text.setContent("我就是我, @XXX 是不一样的烟火");

//        // at 指定人
//        at.setAtMobiles(Arrays.asList("185XXXXXXXX"));
//        // at 所有人
//        at.setIsAtAll(true);
//        return sendDingService.sendText(text).send(at);

        return sendDingService.sendText(text).send();
    }

    @GetMapping("/sendLink")
    public String sendLink()
    {
        SendRequestParam.Link link = new SendRequestParam.Link();
        link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
        link.setTitle("时代的火车向前开");
        link.setPicUrl("https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png");
        link.setMessageUrl("https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");
        return sendDingService.sendLink(link).send();
    }

    @GetMapping("/sendMarkdown")
    public String sendMarkdown()
    {
        SendRequestParam.Markdown markdown = new SendRequestParam.Markdown();
        markdown.setTitle("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
        markdown.setText("#### 杭州天气 @150XXXXXXXX \n > 9度，西北风1级，空气良89，相对温度73%\n > ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n > ###### 10点20分发布 [天气](https://www.dingtalk.com) \n");
        return sendDingService.sendMarkdown(markdown).send();
    }

    @GetMapping("/sendActionCard")
    public String sendActionCard()
    {
        SendRequestParam.Actioncard actioncard = new SendRequestParam.Actioncard();
        actioncard.setTitle("乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身");
        actioncard.setText("![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) \n" +
                " ### 乔布斯 20 年前想打造的苹果咖啡厅 \n" +
                " Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划");
//        actioncard.setSingleTitle("阅读全文");
//        actioncard.setSingleURL("https://www.dingtalk.com/");
        // 0：按钮竖直排列 1：按钮横向排列
        actioncard.setBtnOrientation("1");
        // 添加按钮
        List<SendRequestParam.Btns> btns = new ArrayList<>();
        SendRequestParam.Btns btn1 = new SendRequestParam.Btns();
        btn1.setTitle("查看");
        btn1.setActionURL("");
        btns.add(btn1);
        SendRequestParam.Btns btn2 = new SendRequestParam.Btns();
        btn2.setTitle("取消");
        btns.add(btn2);
        btn1.setActionURL("");

        actioncard.setBtns(btns);
        return sendDingService.sendActionCard(actioncard);
    }

    @GetMapping("/sendFeedcard")
    public String sendFeedcard()
    {
        SendRequestParam.Feedcard feedcard = new SendRequestParam.Feedcard();

        List<SendRequestParam.Links> links = new ArrayList<>();
        SendRequestParam.Links links1 = new SendRequestParam.Links();
        links1.setTitle("时代的火车向前开1");
        links1.setMessageURL("https://www.dingtalk.com/");
        links1.setPicURL("https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png");
        links.add(links1);

        SendRequestParam.Links links2 = new SendRequestParam.Links();
        links2.setTitle("时代的火车向前开2");
        links2.setMessageURL("https://www.dingtalk.com/");
        links2.setPicURL("https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png");
        links.add(links2);

        feedcard.setLinks(links);

        return sendDingService.sendFeedcard(feedcard);
    }
}
