
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;


/**
 * 
 * @author Ricardo
 *
 * Esta clase es la responsable de buscar y leer los emails enviados desde la
 * aplicacion FreeEX
 */
public class ReadingEmail {

	public static String contenido;
	public static Message msg;

	
	public static void main(String[] args){
		leer("fankashai@gmail.com", "Madremia01");
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return el contenido si es que hay un email reciente desde FreeEX
	 * 			sino simplemente termina el proceso y retorna null
	 */
    public static String leer(String username, String password) {
    	
    	// añadido de propiedades
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props);
            Store store = session.getStore();
            store.connect("imap.gmail.com", username, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            msg = inbox.getMessage(inbox.getMessageCount());
            
            Address[] in = msg.getFrom();
            String from = in[0].toString();
            
            String asunto = msg.getSubject();
            
            //while que revisa los primeros 5 mensajes
            int i=1;
            boolean gotMail=true;
            while ( ! (msg.getSubject().startsWith("[FreeEX]")) ){
            	if (i==5){
            		gotMail=false;
            		break;
            	}
            	msg=inbox.getMessage(inbox.getMessageCount()-i);
            	i++;
            }
            
            if (gotMail==false){
            	System.out.println("No habia mensajes desde FreeEX");
            	return null;
            }
            
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);
            
            contenido = msg.getContent().toString();
           
            
            System.out.println("FROM: " + from);
            System.out.println("ASUNTO: "+asunto);
            System.out.println("CONTENIDO: "+contenido);
            
            return contenido;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return contenido;
    }
    
}