public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {


        RollingString patternRString = new RollingString(pattern, pattern.length());
        RollingString inputRString = new RollingString(input.substring(0,pattern.length()), pattern.length());


        if(inputRString.hashCode() == patternRString.hashCode()){
            if(inputRString.equals(patternRString))
                return 0;
        }

        int stringIdx = pattern.length();
        while(stringIdx < input.length()){
            inputRString.addChar(input.charAt(stringIdx));

            if(inputRString.hashCode() == patternRString.hashCode()){
                if(inputRString.equals(patternRString))
                    return stringIdx - pattern.length() + 1;
            }

            stringIdx += 1;
        }

        return -1;
    }

}
