// Credits: https://stackoverflow.com/a/41762

package knp.MouShare;

import java.util.UUID;

public class RandomStringGenerator {
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "").substring(0, 6).toUpperCase();
    }
}


