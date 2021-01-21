//package test;
//
//import com.billow.email.service.MailService;
//import com.billow.system.AdminSystemApp;
//import com.billow.system.properties.CustomProperties;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {AdminSystemApp.class}) // 指定启动类
//public class MailTests {
//
//    @Autowired
//    private JavaMailSender mailSender; // 自动注入的Bean
//
//    @Value("${spring.mail.username}")
//    private String Sender; // 读取配置文件中的参数
//
//    @Autowired
//    private MailService mailService;
//    @Autowired
//    private CustomProperties customProperties;
//
//    @Test
//    public void sendSimpleMail() throws Exception {
//        SimpleMailMessage message = new SimpleMailMessage();
//        System.out.println("test hello Sender " + Sender);
//        message.setFrom(Sender);
//        message.setTo(Sender); // 自己给自己发送邮件
//        message.setSubject("主题：简单邮件");
//        message.setText("测试邮件内容");
//        mailSender.send(message);
//    }
//
//    // 参数模板
//    @Test
//    public void sendTemplateEmailParamFreeMarker() throws Exception {
//        String from = customProperties.getMail().getFrom();
//        String to = from;
//        String sub = "测试模板";
//        String mailCode = "messageParam-FreeMarker";
//        Map<String, String> parameter = new HashMap<>();
//        parameter.put("messageCode", "messageParam-FreeMarker");
//        parameter.put("messageStatus", "200");
//        parameter.put("cause", "causecausecause");
//        mailService.sendTemplateMail(from, to, sub, mailCode, parameter);
//
//        Thread.sleep(50000);
//    }
//
//    // 单选sql 模板
//    @Test
//    public void sendTemplateEmailSQLFreeMarker() throws Exception {
//        String from = customProperties.getMail().getFrom();
//        String to = from;
//        String sub = "测试模板";
//        String mailCode = "messageSQL-FreeMarker";
//        Map<String, String> parameter = new HashMap<>();
//        parameter.put("mailCode", "messageSQL-FreeMarker");
//        mailService.sendTemplateMail(from, to, sub, mailCode, parameter);
//
//        Thread.sleep(50000);
//    }
//
//    // 多行sql模板
//    @Test
//    public void sendTemplateEmailSQLFreeMarker2() throws Exception {
//        String from = customProperties.getMail().getFrom();
//        String to = from;
//        String sub = "测试模板2";
//        String mailCode = "messageSQL2-FreeMarker";
//        Map<String, String> parameter = new HashMap<>();
//        mailService.sendTemplateMail(from, to, sub, mailCode, parameter);
//
//        Thread.sleep(50000);
//    }
//
//    // 单行sql 混合模板
//    @Test
//    public void sendTemplateEmailParamSQLFreeMarker() throws Exception {
//        String from = customProperties.getMail().getFrom();
//        String to = from;
//        String sub = "测试模板";
//        String mailCode = "messageParamSQL-FreeMarker";
//        Map<String, String> parameter = new HashMap<>();
//        parameter.put("mailCode", "messageParamSQL-FreeMarker");
//        parameter.put("messageCode", "messageParamSQL");
//        parameter.put("messageStatus", "200");
//        mailService.sendTemplateMail(from, to, sub, mailCode, parameter);
//
//        Thread.sleep(50000);
//    }
//
//    // 多行sql 混合模板
//    @Test
//    public void sendTemplateEmailParamSQLFreeMarker2() throws Exception {
//        String from = customProperties.getMail().getFrom();
//        String to = from;
//        String sub = "测试模板";
//        String mailCode = "messageParamSQL2-FreeMarker";
//        Map<String, String> parameter = new HashMap<>();
//        parameter.put("mailCode", "messageParamSQL-FreeMarker");
//        parameter.put("messageCode", "messageParamSQL");
//        parameter.put("messageStatus", "200");
//        mailService.sendTemplateMail(from, to, sub, mailCode, parameter);
//
//        Thread.sleep(50000);
//    }
//
//    // 参数模板
//    @Test
//    public void sendTemplateEmailParamThymeleaf() throws Exception {
//        String from = customProperties.getMail().getFrom();
//        String to = from;
//        String sub = "测试模板";
//        String mailCode = "messageParam-Thymeleaf";
//        Map<String, String> parameter = new HashMap<>();
//        parameter.put("messageCode", "messageParam-Thymeleaf");
//        parameter.put("messageStatus", "200");
//        parameter.put("cause", "causecausecause");
//        mailService.sendTemplateMail(from, to, sub, mailCode, parameter);
//
//        Thread.sleep(50000);
//    }
//}