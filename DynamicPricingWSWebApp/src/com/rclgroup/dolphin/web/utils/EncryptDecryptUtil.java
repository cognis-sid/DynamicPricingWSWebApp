package com.rclgroup.dolphin.web.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class EncryptDecryptUtil{
    private Cipher ecipher;
    private Cipher dcipher;
    private String passPhrase;

    public void setPassPhrase(String passPhrase){
        this.passPhrase = passPhrase;
    }

    public String getPassPhrase(){
        return passPhrase;
    }
    
    public EncryptDecryptUtil(){
    }
    // Responsible for setting, initializing this object's encrypter and
    // decrypter Chipher instances

    /**
     * Constructor used to create this object.  Responsible for setting
     * and initializing this object's encrypter and decrypter Chipher instances
     * given a Secret Key and algorithm.
     * @param key        Secret Key used to initialize both the encrypter and
     *                   decrypter instances.
     * @param algorithm  Which algorithm to use for creating the encrypter and
     *                   decrypter instances.
     */
    public EncryptDecryptUtil(SecretKey key, String algorithm){
        try{
            ecipher = Cipher.getInstance(algorithm);
            dcipher = Cipher.getInstance(algorithm);

            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch(NoSuchPaddingException e){
            System.out.println("EXCEPTION: NoSuchPaddingException");
        } catch(NoSuchAlgorithmException e){
            System.out.println("EXCEPTION: NoSuchAlgorithmException");
        } catch(InvalidKeyException e){
            System.out.println("EXCEPTION: InvalidKeyException");
        }
    }

    public EncryptDecryptUtil(String passPhrase){
        KeySpec keySpec = null;
        SecretKey key = null;

        AlgorithmParameterSpec paramSpec = null;
        // 8-bytes Salt
        byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3, 
            (byte) 0x03 };

        // Iteration count
        int iterationCount = 19;
        try{
            // Generate a temporary key. In practice, you would save this key
            // Encrypting with DES Using a Pass Phrase
            if(passPhrase == null){
                passPhrase = getPassPhrase();
                if(passPhrase==null){
                    passPhrase = ""; 
                }
            }
            setPassPhrase(passPhrase);
            
            keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameters to the cipthers
            paramSpec = new PBEParameterSpec(salt, iterationCount);

            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch(InvalidAlgorithmParameterException e){
            System.out.println("EXCEPTION: InvalidAlgorithmParameterException");
        } catch(InvalidKeySpecException e){
            System.out.println("EXCEPTION: InvalidKeySpecException");
        } catch(NoSuchPaddingException e){
            System.out.println("EXCEPTION: NoSuchPaddingException");
        } catch(NoSuchAlgorithmException e){
            System.out.println("EXCEPTION: NoSuchAlgorithmException");
        } catch(InvalidKeyException e){
            System.out.println("EXCEPTION: InvalidKeyException");
        }
    }

    // Encrpt Password

    @SuppressWarnings("unused")
    public String encrypt(String str){
        String strEncoded = null;

        byte[] utf8 = null;
        byte[] enc = null;
        try{
            // Encode the string into bytes using utf-8
            utf8 = str.getBytes("UTF8");
            // Encrypt
            enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            strEncoded = new sun.misc.BASE64Encoder().encode(enc);
        } catch(BadPaddingException e){
            System.out.println("EXCEPTION: BadPaddingException");
        } catch(IllegalBlockSizeException e){
            System.out.println("EXCEPTION: IllegalBlockSizeException");
        } catch(UnsupportedEncodingException e){
            System.out.println("EXCEPTION: UnsupportedEncodingException");
        }
        return strEncoded;
    }

    // Decrpt password
    // To decrypt the encryted password

    public String decrypt(String str){
        KeySpec keySpec = null;
        SecretKey key = null;
        AlgorithmParameterSpec paramSpec = null;

        String passPhrase = null;
        String strDecoded = null;

        byte[] salt = null;
        byte[] dec = null;
        byte[] utf8 = null;
        int iterationCount = 19;
        try{
            salt = new byte[]{ 
                        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, 
                        (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 
                    };
        
            passPhrase = getPassPhrase();
            keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            dcipher = Cipher.getInstance(key.getAlgorithm());
            // Prepare the parameters to the cipthers
            paramSpec = new PBEParameterSpec(salt, iterationCount);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            

            // Decode base64 to get bytes
            dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            // Decrypt
            utf8 = dcipher.doFinal(dec);
            // Decode using utf-8
             strDecoded = new String(utf8, "UTF8");
        } catch(InvalidAlgorithmParameterException e){
            System.out.println("EXCEPTION: InvalidAlgorithmParameterException");
        } catch(InvalidKeySpecException e){
            System.out.println("EXCEPTION: InvalidKeySpecException");
        } catch(NoSuchPaddingException e){
            System.out.println("EXCEPTION: NoSuchPaddingException");
        } catch(NoSuchAlgorithmException e){
            System.out.println("EXCEPTION: NoSuchAlgorithmException");
        } catch(InvalidKeyException e){
            System.out.println("EXCEPTION: InvalidKeyException");
        }catch(BadPaddingException e){
            System.out.println("EXCEPTION: BadPaddingException");
        } catch(IllegalBlockSizeException e){
            System.out.println("EXCEPTION: IllegalBlockSizeException");
        } catch(UnsupportedEncodingException e){
            System.out.println("EXCEPTION: UnsupportedEncodingException");
        } catch(IOException e){
            System.out.println("EXCEPTION: InvaIOExceptionlidKeyException");
        }
        return strDecoded;
    }

    public static String convertStringToHex(String str) throws Exception{
        StringBuffer hex = null;
        
        char[] chars = null;
        try{    
            hex = new StringBuffer();
            
            chars = str.toCharArray();
            for(char character: chars){
                hex.append(Integer.toHexString((int) character));
            }
        }catch(Exception ex){
            throw ex;
        }
        return hex.toString();
    }
     
    public static String convertHexToString(String hex) throws Exception {
        StringBuilder sb = null;
        StringBuilder temp = null;
        
        String output = null;
        
        int decimal = 0;
        try{
            sb = new StringBuilder();
            temp = new StringBuilder();
            //49204c6f7665204a617661 split into two characters 49, 20, 4c...
            for(int i=0; i<hex.length()-1; i+=2 ){
                //grab the hex in pairs
                output = hex.substring(i, (i + 2));
                //convert hex to decimal
                decimal = Integer.parseInt(output, 16);
                //convert the decimal to character
                sb.append((char)decimal);
            
                temp.append(decimal);
            }
            System.out.println("Decimal : " + temp.toString());  
        }catch(Exception ex){
            throw ex;    
        }
        return sb.toString();
    }

    /**
     * The following method is used for testing the String Encrypter class.
     * This method is responsible for encrypting and decrypting a sample
     * String using several symmetric temporary Secret Keys.
     */
    public static void testUsingSecretKey(){
        try{

            System.out.println();
            System.out.println("+----------------------------------------+");
            System.out.println("|  -- Test Using Secret Key Method --    |");
            System.out.println("+----------------------------------------+");
            System.out.println();

            String secretString = "Attack at dawn!";

            // Generate a temporary key for this example. In practice, you would
            // save this key somewhere. Keep in mind that you can also use a 
            // Pass Phrase.
            SecretKey desKey = KeyGenerator.getInstance("DES").generateKey();
            SecretKey blowfishKey = KeyGenerator.getInstance("Blowfish").generateKey();
            SecretKey desedeKey = KeyGenerator.getInstance("DESede").generateKey();

            // Create encrypter/decrypter class
            EncryptDecryptUtil desEncrypter = new EncryptDecryptUtil(desKey, desKey.getAlgorithm());
            EncryptDecryptUtil blowfishEncrypter = new EncryptDecryptUtil(blowfishKey, blowfishKey.getAlgorithm());
            EncryptDecryptUtil desedeEncrypter = new EncryptDecryptUtil(desedeKey, desedeKey.getAlgorithm());

            // Encrypt the string
            String desEncrypted = desEncrypter.encrypt(secretString);
            String blowfishEncrypted = blowfishEncrypter.encrypt(secretString);
            String desedeEncrypted = desedeEncrypter.encrypt(secretString);

            // Decrypt the string
            String desDecrypted = desEncrypter.decrypt(desEncrypted);
            String blowfishDecrypted = blowfishEncrypter.decrypt(blowfishEncrypted);
            String desedeDecrypted = desedeEncrypter.decrypt(desedeEncrypted);

            // Print out values
            System.out.println(desKey.getAlgorithm()+" Encryption algorithm");
            System.out.println("    Original String  : "+secretString);
            System.out.println("    Encrypted String : "+desEncrypted);
            System.out.println("    Decrypted String : "+desDecrypted);
            System.out.println();

            System.out.println(blowfishKey.getAlgorithm()+" Encryption algorithm");
            System.out.println("    Original String  : "+secretString);
            System.out.println("    Encrypted String : "+blowfishEncrypted);
            System.out.println("    Decrypted String : "+blowfishDecrypted);
            System.out.println();

            System.out.println(desedeKey.getAlgorithm()+" Encryption algorithm");
            System.out.println("    Original String  : "+secretString);
            System.out.println("    Encrypted String : "+desedeEncrypted);
            System.out.println("    Decrypted String : "+desedeDecrypted);
            System.out.println();

        } catch(NoSuchAlgorithmException e){
        }
    }


    /**
     * The following method is used for testing the String Encrypter class.
     * This method is responsible for encrypting and decrypting a sample
     * String using using a Pass Phrase.
     */
    public static void testUsingPassPhrase(){

        System.out.println();
        System.out.println("+----------------------------------------+");
        System.out.println("|  -- Test Using Pass Phrase Method --   |");
        System.out.println("+----------------------------------------+");
        System.out.println();

        String secretString = "dolphin1";
        String passPhrase = "TEST";

        // Create encrypter/decrypter class
        EncryptDecryptUtil desEncrypter = new EncryptDecryptUtil(passPhrase);

        // Encrypt the string
        String desEncrypted = desEncrypter.encrypt(secretString);

        // Decrypt the string
        String desDecrypted = desEncrypter.decrypt(desEncrypted);

        // Print out values
        System.out.println("PBEWithMD5AndDES Encryption algorithm");
        System.out.println("    Original String  : "+secretString);
        System.out.println("    Encrypted String : "+desEncrypted);
        System.out.println("    Decrypted String : "+desDecrypted);
        System.out.println();

    }
    
    private static void testConvertStringToHex() throws Exception {
        String str = "DolpHin1";
        String hex = null;
        try{
            System.out.println("\n***** Convert ASCII to Hex *****");
            System.out.println("Original input : " + str);   
            
            hex = convertStringToHex(str);
            System.out.println("Hex : " + hex);      

            System.out.println("\n***** Convert Hex to ASCII *****");
            System.out.println("Hex : " + hex);
            System.out.println("ASCII : " + convertHexToString(hex));
        }catch(Exception ex){
            throw ex;
        }
    }

    /**
     * Sole entry point to the class and application used for testing the
     * String Encrypter class.
     * @param args Array of String arguments.
     */
    public static void main(String[] args){
//        testUsingSecretKey();
       // testUsingPassPhrase();
        try{
           // testConvertStringToHex();
            
            System.out.println("Result = "+convertHexToString("4E424850323032303131"));
        }catch(Exception ex){
            
        }
    }
}
