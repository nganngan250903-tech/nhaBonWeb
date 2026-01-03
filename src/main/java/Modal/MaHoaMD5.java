package Modal;

import java.security.MessageDigest;

public class MaHoaMD5 {
    public static String md5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] arr = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : arr) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
