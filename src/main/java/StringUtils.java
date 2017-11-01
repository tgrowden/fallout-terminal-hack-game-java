import java.util.Arrays;

public class StringUtils {
    /**
     * Determines the number of common characters between two strings
     * @param s1 The first string
     * @param s2 The second string
     * @return int The number of common characters between `s1` and `s2`
     */
    public static int findCommonCharacters(String s1, String s2) {
        int count = 0;
        for(int i = 0; i < s1.length() && i < s2.length(); i++) {
            if(s1.charAt(i) == s2.charAt(i)){
                count++;
            }
        }

        return count;
    }

    /**
     * Left-pads a string with `padding` padding using char `padder`
     * @param text The text to pad
     * @param padding The number of characters to pad the string
     * @param padder The character used for padding
     * @return String The left-padded string
     */
    public static String leftPad(String text, int padding, char padder) {
        char[] chars = new char[padding];
        Arrays.fill(chars, padder);
        String padded = new String(chars);
        return padded.substring(text.length()) + text;
    }

    /**
     * leftPad() with default padder of ' '
     * @param text
     * @param padding
     * @return String The padded string
     */
    public static String leftPad(String text, int padding) {
        return leftPad(text, padding, ' ');
    }
}
