package PaqueteEncriptacion;

import cryptix.message.EncryptedMessage;
import cryptix.message.KeyBundleMessage;
import cryptix.message.LiteralMessage;
import cryptix.message.Message;
import cryptix.message.MessageException;
import cryptix.message.MessageFactory;
import cryptix.message.NotEncryptedToParameterException;

import cryptix.pki.KeyBundle;
import cryptix.pki.KeyBundleException;

import java.io.FileInputStream;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import java.util.Collection;


/**
 * Detailed example for decrypting some text.
 * Assumes that the GenerateAndWriteKey and Encrypt examples has been run.
 *
 * @author  Edwin Woudt <edwin@cryptix.org>
 * @version $Revision: 1.2 $
 */
public class Decrypt {


    //**************************************************************************
    // Main method, will be run on startup.
    //**************************************************************************
    public static void main(String[] args) {
    
        //**********************************************************************
        // Dynamically register both the Cryptix JCE and Cryptix OpenPGP 
        // providers. 
        //**********************************************************************
        java.security.Security.addProvider(
            new cryptix.jce.provider.CryptixCrypto() );
        java.security.Security.addProvider(
            new cryptix.openpgp.provider.CryptixOpenPGP() );


        //**********************************************************************
        // First read the key. 
        //**********************************************************************
        KeyBundle bundle = null;
        MessageFactory mf = null;
        
        try {

            FileInputStream in = new FileInputStream("bob-secret.pgp.asc");

            mf = MessageFactory.getInstance("OpenPGP");
            Collection msgs = mf.generateMessages(in);
            
            KeyBundleMessage kbm = (KeyBundleMessage)msgs.iterator().next();
            
            bundle = kbm.getKeyBundle();

            in.close();
        
        } catch (IOException ioe) {
            System.err.println("IOException... You did remember to run the "+
                "GenerateAndWriteKey example first, right?");
            ioe.printStackTrace();
            System.exit(-1);
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find the OpenPGP MessageFactory. "+
                "This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        } catch (MessageException me) {
            System.err.println("Reading keybundle failed.");
            me.printStackTrace();
            System.exit(-1);
        }


        //**********************************************************************
        //  Read the message.
        //**********************************************************************
        EncryptedMessage em = null;
        
        try {

            FileInputStream in = new FileInputStream("encrypted-to-bob.asc");

            Collection msgs = mf.generateMessages(in);
            em = (EncryptedMessage)msgs.iterator().next();

            in.close();
        
        } catch (IOException ioe) {
            System.err.println("IOException... You did remember to run the "+
                "Encrypt example first, right?");
            ioe.printStackTrace();
            System.exit(-1);
        } catch (MessageException me) {
            System.err.println("Reading message failed.");
            me.printStackTrace();
            System.exit(-1);
        }
        

        //**********************************************************************
        // Decrypt the message.
        //**********************************************************************
        try {

            Message msg = em.decrypt(bundle, "TestingPassphrase".toCharArray());
            System.out.println(((LiteralMessage)msg).getTextData());
        
        } catch (NotEncryptedToParameterException netpe) {
            System.err.println("Not encrypted to this key.");
            netpe.printStackTrace();
            System.exit(-1);
        } catch (UnrecoverableKeyException uke) {
            System.err.println("Invalid passphrase.");
            uke.printStackTrace();
            System.exit(-1);
        } catch (MessageException me) {
            System.err.println("Decrypting message failed.");
            me.printStackTrace();
            System.exit(-1);
        }
        
    }
    
}
