package com.example.gusfc_000.freeex;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * Created by ricardo 18/11/2015.
 */
public class SendMail  extends AsyncTask<Void,Void,Void> {


    private Context context;
    private Session session;
    private String email;
    private String contraseña;
    private String toEmail;
    private String subject;
    private String message;
    private ProgressDialog progressDialog;

    public SendMail(Context context, String email, String contraseña, String toEmail, String subject, String message){
        this.context = context;
        this.email = email;
        this.contraseña = contraseña;
        this.toEmail = toEmail;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
        Toast.makeText(context, "Enviando mensaje", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context, "Mensaje Enviado", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, contraseña );
                    }
                });


        try {
            MimeMessage mm = new MimeMessage(session);

            mm.setFrom(new InternetAddress(email));

            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            mm.setSubject(subject);

            mm.setText(message);

            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}