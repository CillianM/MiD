package ie.mid.identityengine.util;

public class Random {

    private static final String POSSIBLE_CHARACTERS="AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";

    public static String getRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int character = (int)(Math.random()*POSSIBLE_CHARACTERS.length());
            builder.append(POSSIBLE_CHARACTERS.charAt(character));
        }
        return builder.toString();
    }
}
