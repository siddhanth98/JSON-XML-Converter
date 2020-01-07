public class EuphoniousWord {
    private static int[] vowelAscii = {97, 101, 105, 111, 117, 121};

    public static void main(String[] args) {
        System.out.println(euphonious("iiiaaa"));
    }

    private static int euphonious(String str) {
        int i = 0, count = 0;
        if(str.length() >= 3) {
            while (i <= (str.length() - 3)) {
                if (isVowel(str.charAt(i)) && isVowel(str.charAt(i + 1)) && isVowel(str.charAt(i + 2))) {
                    str = str.substring(0, i+2) + "b" + str.substring(i+2);
                    count++;
                    i += 3;
                }
                else if (!isVowel(str.charAt(i)) && !isVowel(str.charAt(i + 1)) && !isVowel(str.charAt(i + 2))) {
                    str = str.substring(0, i+2) + "a" + str.substring(i+2);
                    count++;
                    i += 3;
                }
                else
                    i++;
            }

            return count;
        }
        else
            return 0;
    }

    private static boolean isVowel(char c) {
        int left = 0, right = 5, mid, charAscii = (int) c;
        while(left <= right) {
            mid = (left + right) / 2;
            if(vowelAscii[mid] == charAscii)
                return true;
            else if (vowelAscii[mid] < charAscii)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return false;
    }
}
