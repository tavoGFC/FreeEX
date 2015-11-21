package PaqueteEncriptacion;

import cryptix.message.MessageException;

import cryptix.openpgp.PGPArmouredMessage;
import cryptix.openpgp.PGPKeyBundle;

import cryptix.pki.CertificateBuilder;
import cryptix.pki.PrincipalBuilder;
import cryptix.pki.PrincipalException;
import cryptix.pki.KeyBundle;
import cryptix.pki.KeyBundleException;
import cryptix.pki.KeyBundleFactory;

import java.io.FileOutputStream;
import java.io.IOException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;


/**
 * Detailed example for generating two PGP keys and writing them to disk.
 *
 * @author  Edwin Woudt <edwin@cryptix.org>
 * @version $Revision: 1.3 $
 */
public class GenerateAndWriteKey {


    //**************************************************************************
    // Variables where we save the keys.
    //
    // As you can see, OpenPGP 'keys' map to cryptix.pki.KeyBundle objects.
    //
    // It doesn't matter in this case if the key is a public or private (secret)
    // key.
    //**************************************************************************
    private static PGPKeyBundle simplePublicKey, simplePrivateKey;
    private static PGPKeyBundle complexPublicKey, complexPrivateKey;



    //**************************************************************************
    // Main method, will be run on startup.
    //**************************************************************************
    public static void main(String[] args) {
    
        //**********************************************************************
        // Dynamically register both the Cryptix JCE and Cryptix OpenPGP 
        // providers. 
        //
        // You can also register them statically by putting lines like the
        // following in your java.security file:
        //
        //   security.provider.1=cryptix.jce.provider.CryptixCrypto
        //   security.provider.2=cryptix.openpgp.provider.CryptixOpenPGP
        //**********************************************************************
        java.security.Security.addProvider(
            new cryptix.jce.provider.CryptixCrypto() );
        java.security.Security.addProvider(
            new cryptix.openpgp.provider.CryptixOpenPGP() );

    
        //**********************************************************************
        // Generate a simple key
        //**********************************************************************
        generateSimpleKey();


        //**********************************************************************
        // Generate a complex key
        //**********************************************************************
        generateComplexKey();


        //**********************************************************************
        // Write the keys to disk
        //**********************************************************************
        writeKeys();
        
    }
    

    public static void generateSimpleKey() {
        
        SecureRandom sr = new SecureRandom();

        //**********************************************************************
        // First get some empty KeyBundle objects. We'll fill them up later
        //**********************************************************************
        try {

            KeyBundleFactory kbf = 
                KeyBundleFactory.getInstance("OpenPGP");

            simplePublicKey = (PGPKeyBundle)kbf.generateEmptyKeyBundle();
            simplePrivateKey = (PGPKeyBundle)kbf.generateEmptyKeyBundle();

        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find the OpenPGP KeyBundleFactory. "+
                "This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        } catch (KeyBundleException kbe) {
            System.err.println("Generating an empty KeyBundle failed.");
            kbe.printStackTrace();
            System.exit(-1);
        }
        

        //**********************************************************************
        // Now generate the signing key.
        //
        // We'll use a 1024 bit DSA key here. You can use other algorithms if
        // you want, by using for example:
        //    kpg = KeyPairGenerator.getInstance("OpenPGP/Signing/RSA");
        //    kpg = KeyPairGenerator.getInstance("OpenPGP/Signing/ElGamal");
        //
        // (Note that ElGamal signature were not supported yet at the time of
        //  writing this example class.)
        //**********************************************************************
        KeyPairGenerator kpg = null;
        
        try {

            kpg = KeyPairGenerator.getInstance("OpenPGP/Signing/DSA");

        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find the OpenPGP KeyPairGenerator. "+
                "This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        }

        kpg.initialize(1024, sr);
        KeyPair kp = kpg.generateKeyPair();
        
        PublicKey pubkey = kp.getPublic();
        PrivateKey privkey = kp.getPrivate();
        

        //**********************************************************************
        // Now build the primary userid for this key.
        //**********************************************************************
        Principal userid = null;

        try {

            PrincipalBuilder princbuilder = 
                PrincipalBuilder.getInstance("OpenPGP/UserID");
        
            userid = princbuilder.build("Alice S. Imple <alice@simple.com>");
            
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find the OpenPGP PrincipalBuilder. "+
                "This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        } catch (PrincipalException pe) {
            System.err.println("Generating the user id failed.");
            pe.printStackTrace();
            System.exit(-1);
        }
        

        //**********************************************************************
        // We need to sign the generated user id with our key, which will 
        // bring us a so called 'certificate'.
        //
        // This btw is a self-signed certificate, you can also have certificates
        // signed by other people. See the ReadAndSignKey example for that.
        //**********************************************************************
        Certificate cert = null;

        try {
        
            CertificateBuilder certbuilder =
                CertificateBuilder.getInstance("OpenPGP/Self");
            
            cert = certbuilder.build(pubkey, userid, privkey, sr);
            
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find the OpenPGP CertificateBuilder. "+
                "This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        } catch (CertificateException ce) {
            System.err.println("Generating the self certification sig failed.");
            ce.printStackTrace();
            System.exit(-1);
        }
        
        
        //**********************************************************************
        // Building up the keybundle is easy now. We only need to add the 
        // certificate, as this will automagically add the public key and the
        // user id to the keybundle.
        //**********************************************************************
        try {

            simplePublicKey.addCertificate(cert);
            simplePrivateKey.addCertificate(cert);

        } catch (KeyBundleException kbe) {
            System.err.println(
                "Adding the self certificate to the keybundle failed.");
            kbe.printStackTrace();
            System.exit(-1);
        }
        
        
        //**********************************************************************
        // Of course we still need to add the private key to the private
        // keybundle, while encrypting it with a passphrase.
        //**********************************************************************
        try {

            simplePrivateKey.addPrivateKey(privkey, pubkey, 
                "TestingPassphrase".toCharArray(), sr);

        } catch (KeyBundleException kbe) {
            System.err.println(
                "Adding the private key to the keybundle failed.");
            kbe.printStackTrace();
            System.exit(-1);
        }
        
    }
    
    
    public static void generateComplexKey() {

        SecureRandom sr = new SecureRandom();

        //**********************************************************************
        // The first part of this example is basically the same as the 
        // generateSimpleKey method. It has therefore been copied without any 
        // comments.
        //**********************************************************************
        
        try {

            KeyBundleFactory kbf = 
                KeyBundleFactory.getInstance("OpenPGP");
            complexPublicKey = (PGPKeyBundle)kbf.generateEmptyKeyBundle();
            complexPrivateKey = (PGPKeyBundle)kbf.generateEmptyKeyBundle();
            KeyPairGenerator kpg = 
                KeyPairGenerator.getInstance("OpenPGP/Signing/DSA");
            kpg.initialize(1024, sr);
            KeyPair kp = kpg.generateKeyPair();
            PublicKey pubkey = kp.getPublic();
            PrivateKey privkey = kp.getPrivate();
            PrincipalBuilder princbuilder = 
                PrincipalBuilder.getInstance("OpenPGP/UserID");
            Principal userid = 
                princbuilder.build("Bob C. Omplex <bob@complex.com>");
            CertificateBuilder certbuilder =
                CertificateBuilder.getInstance("OpenPGP/Self");
            Certificate cert = certbuilder.build(pubkey, userid, privkey, sr);
            complexPublicKey.addCertificate(cert);
            complexPrivateKey.addCertificate(cert);
            complexPrivateKey.addPrivateKey(privkey, pubkey, 
                "TestingPassphrase".toCharArray(), sr);
        
        } catch (Exception e) {
            System.err.println("Whoops!!!");
            e.printStackTrace();
            System.exit(-1);
        }


        //**********************************************************************
        // Ok, now first we're going to add an encryption subkey.
        //
        // We'll use a 1024 bit ElGamal key here. You can use other algorithms
        // if you want, by using for example:
        //    kpg = KeyPairGenerator.getInstance("OpenPGP/Encryption/RSA");
        //**********************************************************************
        
        KeyPairGenerator kpg = null;
        
        try {

            kpg = KeyPairGenerator.getInstance("OpenPGP/Encryption/ElGamal");

        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find the OpenPGP KeyPairGenerator. "+
                "This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        }

        kpg.initialize(1024, sr);
        KeyPair kp = kpg.generateKeyPair();
        
        PublicKey pubsubkey = kp.getPublic();
        PrivateKey privsubkey = kp.getPrivate();
        
        
        //**********************************************************************
        // The subkey has to be 'binded' to the main key by generating a binding
        // signature. To do that, we first we have to get back the main private 
        // key.
        //**********************************************************************
        
        PublicKey pubmainkey = null;
        PrivateKey privmainkey = null;
        
        try {

            pubmainkey = (PublicKey)complexPrivateKey.getPublicKeys().next();
            privmainkey = complexPrivateKey.getPrivateKey(pubmainkey, 
                "TestingPassphrase".toCharArray());
        
        } catch (UnrecoverableKeyException uke) {
            System.err.println("Wrong passphrase!");
            uke.printStackTrace();
            System.exit(-1);
        }
        
        
        //**********************************************************************
        // Generating the binding signature is done by the keybundle.
        //**********************************************************************
        
        try {

            complexPublicKey.addPublicSubKey(pubsubkey, privmainkey);
        
        } catch (KeyBundleException kbe) {
            System.err.println("Adding the subkey and/or generating the "+
                "blinding signature failed.");
            kbe.printStackTrace();
            System.exit(-1);
        }
        

        //**********************************************************************
        // Now that we've added the public subkey to the public key, we need
        // to add both the public and private subkeys to the private keybundle.
        //
        // First we add the public subkey to the private keybundle.
        // 
        // This time we however do not need to recalculate the binding 
        // signature. We can 'borrow' the binding signature from another bundle,
        // in this case the public key.
        //**********************************************************************
        
        try {
        
            complexPrivateKey.addPublicSubKey(pubsubkey, complexPublicKey);
            
        } catch (KeyBundleException kbe) {
            System.err.println("Adding the public subkey failed.");
            kbe.printStackTrace();
            System.exit(-1);
        }
        
        
        //**********************************************************************
        // The only thing that is left now is to add the private sub key to
        // the private keybundle and encrypting it with a passphrase.
        //
        // The public key is also given as a parameter so the keybundle can 
        // figure out which public key and private key to couple together
        //**********************************************************************

        try {
        
            complexPrivateKey.addPrivateSubKey(privsubkey, pubsubkey,
                "TestingPassphrase".toCharArray(), sr);
            
        } catch (KeyBundleException kbe) {
            System.err.println("Adding the private subkey failed.");
            kbe.printStackTrace();
            System.exit(-1);
        }
        
        


        //**********************************************************************
        // 
        //**********************************************************************

        //**********************************************************************
        // And now something completely different: adding another UserID.
        //
        // Given that we already explained adding another userID in the
        // generateSimpleKey() method, there will not be many comments.
        //**********************************************************************
        
        try {

            PrincipalBuilder princbuilder = 
                PrincipalBuilder.getInstance("OpenPGP/UserID");
            Principal userid = 
                princbuilder.build("Bob C. Omplex <bob@work.com>");

            CertificateBuilder certbuilder =
                CertificateBuilder.getInstance("OpenPGP/Self");
            Certificate cert = certbuilder.build(pubmainkey, userid, 
                                                 privmainkey, sr);

            complexPublicKey.addCertificate(cert);
            complexPrivateKey.addCertificate(cert);

        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Cannot find one of the OpenPGP builders. "+
                "This usually means that the Cryptix OpenPGP provider is not "+
                "installed correctly.");
            nsae.printStackTrace();
            System.exit(-1);
        } catch (PrincipalException pe) {
            System.err.println("Generating the user id failed.");
            pe.printStackTrace();
            System.exit(-1);
        } catch (CertificateException ce) {
            System.err.println("Generating the self certification sig failed.");
            ce.printStackTrace();
            System.exit(-1);
        } catch (KeyBundleException kbe) {
            System.err.println("Adding the UserID failed.");
            kbe.printStackTrace();
            System.exit(-1);
        }
        
    }
    
    
    public static void writeKeys() {
    
        FileOutputStream out;
        
        //**********************************************************************
        // As you can see below, writing keybundles is fairly simple.
        //
        // Note that the code below will write the files in binary format.
        // See below for how to write keys in ascii armoured format.
        //
        // For an example on how to read these files again, see the 
        // ReadAndSignKey example.
        //**********************************************************************
        
        try {

            out = new FileOutputStream("alice-public.pgp");
            out.write(simplePublicKey.getEncoded());
            out.close();
        
            out = new FileOutputStream("alice-secret.pgp");
            out.write(simplePrivateKey.getEncoded());
            out.close();
            
        } catch (KeyBundleException kbe) {
            System.err.println("Writing the keybundles failed.");
            kbe.printStackTrace();
            System.exit(-1);
        } catch (IOException ioe) {
            System.err.println("Writing the keybundles failed.");
            ioe.printStackTrace();
            System.exit(-1);
        }
        
        //**********************************************************************
        // And now let's write some keys in ASCII armoured format.
        //**********************************************************************

        try {

            PGPArmouredMessage armoured;
        
            armoured = new PGPArmouredMessage(complexPublicKey);
            out = new FileOutputStream("bob-public.pgp.asc");
            out.write(armoured.getEncoded());
            out.close();
        
            armoured = new PGPArmouredMessage(complexPrivateKey);
            out = new FileOutputStream("bob-secret.pgp.asc");
            out.write(armoured.getEncoded());
            out.close();
        
        } catch (MessageException me) {
            System.err.println("Writing the keybundles failed.");
            me.printStackTrace();
            System.exit(-1);
        } catch (IOException ioe) {
            System.err.println("Writing the keybundles failed.");
            ioe.printStackTrace();
            System.exit(-1);
        }
        
    }
    
}

