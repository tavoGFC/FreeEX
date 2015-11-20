package com.send;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sendemail.R.layout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends Activity implements OnClickListener{

	Session session = null;
	ProgressDialog pdialog = null;
	Context context = null;
	EditText reciep, sub, msg;
	String rec, subject, textMessage;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        
        context = getApplicationContext();
        
        reciep = (EditText) findViewById(com.sendemail.R.id.et_to);
        sub = (EditText) findViewById(com.sendemail.R.id.et_sub);
        msg = (EditText) findViewById(com.sendemail.R.id.et_text);
   
    }

	public void onClickButtonLogin(View v) {
		rec = reciep.getText().toString();
		subject = sub.getText().toString();
		textMessage = msg.getText().toString();
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("testfrom354@gmail.com", "p1234p1234");
			}
		});
		
		pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);
		
		RetreiveFeedTask task = new RetreiveFeedTask();
		task.execute();
	}
	
	class RetreiveFeedTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			
			try{
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("testfrom354@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
				message.setSubject(subject);
				message.setContent(textMessage, "text/html; charset=utf-8");
				Transport.send(message);
			} catch(MessagingException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			pdialog.dismiss();
			reciep.setText("");
			msg.setText("");
			sub.setText("");
			Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
