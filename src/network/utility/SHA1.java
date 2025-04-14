package network.utility;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
public class SHA1 {
    public static String getSHA1String(String input){
        //https://docs.oracle.com/en/java/javase/22/docs/specs/security/standard-names.html#messagedigest-algorithms
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        
    }
    public static void main(String[] args) {
        try {
            String input = "login:pass";
            //https://docs.oracle.com/en/java/javase/22/docs/specs/security/standard-names.html#messagedigest-algorithms
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            for (Provider string : Security.getProviders()) {
                System.out.println(string.toString());
            }
            byte[] hash = md.digest(input.getBytes());

            // Convert the byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            System.out.println(sb.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
