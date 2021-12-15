package com.billow.notice.ding.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SendRequestParam implements Serializable
{
    private Actioncard actionCard;
    private At at;
    private Feedcard feedCard;
    private Link link;
    private Markdown markdown;
    private Text text;
    private String msgtype;

    @Data
    public static class Text implements Serializable
    {
        private static final long serialVersionUID = 2112411828946494293L;

        private String content;
    }

    @Data
    public static class Link implements Serializable
    {
        private static final long serialVersionUID = 7833398226941254374L;
        private String messageUrl;
        private String picUrl;
        private String text;
        private String title;
    }

    @Data
    public static class Markdown implements Serializable
    {
        private static final long serialVersionUID = 4697553615692276546L;
        private String text;
        private String title;
    }

    @Data
    public static class At implements Serializable
    {
        private static final long serialVersionUID = 6328897894299426499L;
        private List<String> atMobiles;
        private List<String> atUserIds;
        private Boolean isAtAll;
    }


    @Data
    public static class Actioncard implements Serializable
    {
        private static final long serialVersionUID = 1553944892187227581L;
        private String btnOrientation;
        private List<Btns> btns;
        private String hideAvatar;
        private String singleTitle;
        private String singleURL;
        private String text;
        private String title;
    }

    @Data
    public static class Feedcard implements Serializable
    {
        private static final long serialVersionUID = 6578287939968676945L;
        private List<Links> links;
    }

    @Data
    public static class Btns implements Serializable
    {
        private static final long serialVersionUID = 4435422339746837645L;
        private String actionURL;
        private String title;
    }

    @Data
    public static class Links implements Serializable
    {
        private static final long serialVersionUID = 7496318843524412877L;
        private String messageURL;
        private String picURL;
        private String title;
    }
}
