package Modal;

import java.util.Random;

public class Captcha {
    public static String taoCaptcha() {
        return String.valueOf(new Random().nextInt(9000) + 1000);
    }
}