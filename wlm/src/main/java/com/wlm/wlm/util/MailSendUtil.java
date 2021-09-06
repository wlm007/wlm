package com.wlm.wlm.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author wuliming
 * @date 2021/8/20 17:02
 */
@Slf4j
@Service
public class MailSendUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private final String subject = "you sb";

    private final String defaultCharset = "UTF-8";

    public void sendTextMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
        log.info("邮件发送成功");
    }

    public void sendTextMailByBoot(String to, String subject, String content) {
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding(defaultCharset);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject == null ? this.subject : subject);
        message.setText(content);
        mailSender.send(message);
        log.info("邮件发送成功");
    }

    public void sendMimeEmail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            message.setSubject(subject);
            messageHelper.setText(content,true);
            javaMailSender.send(message);
            log.info("邮件已经发送！");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常："+e);
        }
    }

    public void sendsAttachmentEmail(String to, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content,true);
            //携带附件
            FileSystemResource file = new FileSystemResource(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf("/"));
            messageHelper.addAttachment(fileName,file);

            javaMailSender.send(message);
            log.info("邮件加附件发送成功！");
        } catch (MessagingException e) {
            log.error("发送失败："+e);
        }
    }
}
