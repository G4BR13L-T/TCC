package com.tcc.TCC.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class S_EMailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailSimples (String destinatario, String assunto, String corpo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);
        mensagem.setFrom("notreserve2025@gmail.com");

        mailSender.send(mensagem);
    }

    public void enviarEmailComHtml(String destinatario, String assunto, String html) throws MessagingException {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(html, true);
        helper.setFrom("notreserve2025@gmail.com");

        mailSender.send(mensagem);
    }
}
