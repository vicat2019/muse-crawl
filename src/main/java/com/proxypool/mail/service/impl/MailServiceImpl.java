package com.proxypool.mail.service.impl;

import com.proxypool.mail.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: muse-crawl
 * @description: 邮件服务类
 * @author: Vic
 * @create: 2019-09-25 01:35
 **/
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Resource
    private MailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);//收信人
        message.setSubject(subject);//主题
        message.setText(content);//内容
        message.setFrom(from);//发信人

        mailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {

    }

    @Override
    public void sendAttachmentMail(String to, String subject, String content, String filePath) {

    }

    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

    }
}
