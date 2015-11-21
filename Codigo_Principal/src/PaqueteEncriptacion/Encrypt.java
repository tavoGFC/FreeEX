package PaqueteEncriptacion;

import cryptix.message.EncryptedMessageBuilder;
import cryptix.message.KeyBundleMessage;
import cryptix.message.LiteralMessage;
import cryptix.message.LiteralMessageBuilder;
import cryptix.message.Message;
import cryptix.message.MessageException;
import cryptix.message.MessageFactory;

import cryptix.openpgp.PGPArmouredMessage;

import cryptix.pki.KeyBundle;
import cryptix.pki.KeyBundleException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;

import java.util.Collection;



/**
 * Detailed example for encrypting a message.
 * This example assumes that the GenerateAndWriteKey example has been run first.
 *
 * @author  Edwin Woudt <edwin@cryptix.org>
 * @version $Revision: 1.2 $
 */
public class Encrypt {

    public static void main (String[] args) {

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
        
        try {

            FileInputStream in = new FileInputStream("bob-public.pgp.asc");

            MessageFactory mf = MessageFactory.getInstance("OpenPGP");
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
        //  Build the literal message.
        //**********************************************************************
        LiteralMessage litmsg = null;
        try {
            String msg = "This is a test message.\n" +
                         "This is another line.\n";
            LiteralMessageBuilder lmb = 
                LiteralMessageBuilder.getInstance("OpenPGP");
            lmb.init(msg);
            litmsg = (LiteralMessage)lmb.build();
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find the OpenPGP LiteralMessageBuilder."+
                " This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        } catch (MessageException me) {
            System.err.println("Creating the literal message failed.");
            me.printStackTrace();
            System.exit(-1);
        }
        

        //**********************************************************************
        // Encrypt the message.
        //**********************************************************************
        Message msg = null;
        try {
            
            EncryptedMessageBuilder emb = 
                EncryptedMessageBuilder.getInstance("OpenPGP");
            emb.init(litmsg);
            emb.addRecipient(bundle);
            msg = emb.build();
        
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find the OpenPGP "+
                "EncryptedMessageBuilder. "+
                "This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        } catch (MessageException me) {
            System.err.println("Creating the encrypted message failed.");
            me.printStackTrace();
            System.exit(-1);
        }
        

        //**********************************************************************
        // Armour the message and write it to disk
        //**********************************************************************
        try {
            
            PGPArmouredMessage armoured;
        
            armoured = new PGPArmouredMessage(msg);
            FileOutputStream out = new FileOutputStream("encrypted-to-bob.asc");
            out.write(armoured.getEncoded());
            out.close();
        
        } catch (MessageException me) {
            System.err.println("Writing the encrypted message failed.");
            me.printStackTrace();
            System.exit(-1);
        } catch (IOException ioe) {
            System.err.println("Writing the encrypted message failed.");
            ioe.printStackTrace();
            System.exit(-1);
        }
        
    }

}
