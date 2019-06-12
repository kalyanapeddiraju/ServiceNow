package Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

public class GenericFunctions {

    // Check the email format is valid

    public static boolean isStringLenghtValid = false;

    public boolean isValid(String email) {

        isStringLenghtValid = checkStringLength(email, 1, 50);
        boolean isEmailValid = false;

        if (isStringLenghtValid) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;

            isEmailValid = pat.matcher(email).matches();
        }
        return isEmailValid;
    }

    // Generate random Email Id
    Random rand = new Random();
    public String emailID = "bff_w1" + (rand.nextInt(1000000) + 1) + "@somedomain.com";

    // convert Json to String and return Json Key value as string - Testa Data use
    public String convertTestSetString(String testData_jsonAsString, String key) throws JSONException {
        JSONObject testJSON = new JSONObject(testData_jsonAsString);
        if (testJSON.has(key)) {
            return testJSON.getString(key);
        } else {
            System.out.println(">> Key " + key + " not found in test data object.");
            return null;
        }
    }

    // To Check if String is Lowercase
    public static boolean isLowerCase(String s) {

        boolean isCaseValidation = false;

        isStringLenghtValid = checkStringLength(s, 1, 50);
        if (isStringLenghtValid) {

            for (int i = 0; i < s.length(); i++) {
                if (!Character.isLowerCase(s.charAt(i))) {
                    isCaseValidation = false;
                } else {
                    isCaseValidation = true;
                }
            }
        }
        return isCaseValidation;
    }


    //To Check String Length

    public static boolean checkStringLength(String s, int min, int max) {

        boolean StringLenghtValid = false;
        if (!s.equals("")) {
            if (!(s.length() < min || s.length() > max)) {
                StringLenghtValid = true;
            }
        }
        return StringLenghtValid;
    }

    public boolean isPatternMatch(String s, String pattern, int min, int max) {

        boolean isStringPatternValid = false;
        isStringLenghtValid = checkStringLength(s, min, max);
        if (isStringLenghtValid) {
            isStringPatternValid = s.matches(pattern);
        }
        return isStringPatternValid;
    }

    public String DateFormatYYYYMMDD() {
        DateFormat DFTimeSec2 = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String dateTimeSec2 = DFTimeSec2.format(currentDate);
        System.out.println(dateTimeSec2);
        return dateTimeSec2;

    }

    public static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        String filePath =
                System.getProperty("user.dir")
                        + File.separator + "src" + File.separator + "test" + File.separator + "resources"
                        + File.separator + System.getProperty("env") + ".properties";
        InputStream input;
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            input = new FileInputStream(new File(filePath));
            properties.load(input);
        }else{
            System.out.println("Config File Missing :: " + filePath);
        }

        //Load base config if present
        filePath =
                System.getProperty("user.dir")
                        + File.separator + "src" + File.separator + "test" + File.separator + "resources"
                        + File.separator + "base.properties";
        f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            input = new FileInputStream(new File(filePath));
            properties.load(input);
        }

        //Load properties to System variables
        Enumeration e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            System.setProperty(key, properties.getProperty(key));
        }
        return properties;
    }
}
