package com.example.gusfc_000.freeex;

import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;

public class ReadingEmail {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props);
            Store store = session.getStore();
            store.connect("imap.gmail.com", "fankashai@gmail.com", "Madremia01");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = inbox.getMessage(inbox.getMessageCount()-1);
            Address[] in = msg.getFrom();
            
            System.out.println("FROM:" + in[0]);
            
            
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);
            
            System.out.println("ASUNTO: "+msg.getSubject());
            System.out.println("CONTENIDO: "+msg.getContent());
            
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
}