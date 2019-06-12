package data;

import Utils.GenericFunctions;

import java.util.ArrayList;

public class TestData {

    GenericFunctions genericFunctions =new GenericFunctions();
    public String adminLoginTestData(){
        String testData_jsonAsString = "{" +
                "'userName': 'admin'," +
                "'password': 'admin'" +
                "}";
        return  testData_jsonAsString;
    }

    public String newUserRegistrationTestData(){
        String testData_jsonAsString = "{" +
                "'userName': 'adminnew'," +
                "'password': 'password1'," +
                "'email': '" + new GenericFunctions ( ).emailID + "'," +
                "'confirmpassword': 'password1'" +
                "}";
        return  testData_jsonAsString;
    }

    public String[][] newBranchAsArray() {
        String[][] newBranches = {
                {"branchcincinati", "OH123"},
                {"branchdayton", "OH124"},
                {"branchchicgo", "IL123"}
        };
        return newBranches;
    }
    public String[][] newStaffAsArray() {
        String[][] newBranches = {
                {"Staffcincinnati", "branchcincinati"},
                {"Staffdayton", "branchdayton"},
                {"StaffChicago", "branchchicgo"}
        };
        return newBranches;
    }

    public String newBranch(){
        String testData_jsonAsString = "{" +
                "'branchName1': 'branchcincinati'," +
                "'code1': 'OH123'," +
                "'staffName1': 'Staffcincinnati'," +
                "'branchName2': 'branchdayton'," +
                "'code2': 'OH124'," +
                "'staffName2': 'Staffdayton'," +
                "'branchName3': 'branchchicgo'," +
                "'code3': 'IL123'," +
                "'staffName3': 'StaffChicago'," +
                "}";

        return  testData_jsonAsString;
    }




    public String fieldErrorMessages(){
        String testData_jsonAsString = "{" +
              //  "'loginMinLength': 'Your login is required to be at least 1 character.'," +
                "'loginMaxLength': 'Your login cannot be longer than 50 characters.'," +
                "'loginPattern': 'Your login can only contain lower-case letters and digits.'," +
                "'loginRequired': 'Your login is required.'," +
                "'emailRequired': 'Your e-mail is required.'," +
                "'emailInvalid': 'Your e-mail is invalid.'," +
                "'emailMinLength': 'Your e-mail is required to be at least 5 characters.'," +
               // "'emailMaxLength': 'Your e-mail cannot be longer than 50 characters.'," +
                "'newpasswordRequired': 'Your password is required.'," +
                "'newpasswordMinLength': 'Your password is required to be at least 5 characters.'," +
                "'newpasswordMaxLength': 'Your password cannot be longer than 50 characters.'," +
               // "'newpasswordPattern': 'Password should begin with a alphabet and should contain a number and a special character.'," +
                "'confirmPasswordRequired': 'Your confirmation password is required.'," +
                "'confirmPasswordMinLength': 'Your confirmation password is required to be at least 5 characters.'," +
                "'confirmPasswordMaxLength': 'Your confirmation password cannot be longer than 50 characters.'," +
                "}";

        return  testData_jsonAsString;
    }

    public String fieldErrorValues(){
        String testData_jsonAsString = "{" +
                //"'loginMinLength': ''," +
                "'loginMaxLength': 'abcdefghijklmnopqrstuvwabcdefghijklmnopqrstuvwabcde'," +
                "'loginPattern': 'Abc12345@'," +
                "'loginRequired': ''," +
                "'emailRequired': ''," +
                "'emailInvalid': 'invalidmailgmail.com'," +
                "'emailMinLength': 'a@co'," +
              //  "'emailMaxLength': 'abcdefghijklmnopqrstuvwabcdefghijklmnopqrstuvwabcde@gmail.com'," +
                "'newpasswordRequired': ''," +
                "'newpasswordMinLength': 'a@12'," +
                "'newpasswordMaxLength': 'a12$@defghijklmnopqrstuvwabcdefghijklmnopqrstuvwabcde'," +
               // "'newpasswordPattern': '12@aBc'," +
                "'confirmPasswordRequired': ''," +
                "'confirmPasswordMinLength': 'abc'," +
                "'confirmPasswordMaxLength': 'defghijklmnopqrstuvwabcdefghijklmnopqrstuvwabcdefgh'," +
                "}";

        return  testData_jsonAsString;
    }

    public ArrayList<String> getExecutionOrder() {
        ArrayList<String> executionKeys = new ArrayList<>();
        //executionKeys.add("loginMinLength");
        executionKeys.add("loginMaxLength");
        executionKeys.add("loginPattern");
        executionKeys.add("loginRequired");
        executionKeys.add("emailRequired");
        executionKeys.add("emailInvalid");
        executionKeys.add("emailMinLength");
       // executionKeys.add("emailMaxLength");
        executionKeys.add("newpasswordRequired");
        executionKeys.add("newpasswordMinLength");
        executionKeys.add("newpasswordMaxLength");
       // executionKeys.add("newpasswordPattern");
        executionKeys.add("confirmPasswordRequired");
        executionKeys.add("confirmPasswordMinLength");
        executionKeys.add("confirmPasswordMaxLength");
        return executionKeys;
    }

    public String fieldErrorMessageLocators(){
        String testData_jsonAsString = "{" +
               // "'loginMinLength': 'register.messages.validate.login.minlength'," +
                "'loginMaxLength': 'register.messages.validate.login.maxlength'," +
                "'loginPattern': 'register.messages.validate.login.pattern'," +
                "'loginRequired': 'register.messages.validate.login.required'," +
                "'emailRequired': 'global.messages.validate.email.required'," +
                "'emailInvalid': 'global.messages.validate.email.invalid'," +
                "'emailMinLength': 'global.messages.validate.email.minlength'," +
              //  "'emailMaxLength': 'global.messages.validate.email.maxlength'," +
                "'newpasswordRequired': 'global.messages.validate.newpassword.required'," +
                "'newpasswordMinLength': 'global.messages.validate.newpassword.minlength'," +
                "'newpasswordMaxLength': 'global.messages.validate.newpassword.maxlength'," +
               // "'newpasswordPattern': 'global.messages.validate.newpassword.pattern'," +
                "'confirmPasswordRequired': 'global.messages.validate.confirmpassword.required'," +
                "'confirmPasswordMinLength': 'global.messages.validate.confirmpassword.minlength'," +
                "'confirmPasswordMaxLength': 'global.messages.validate.confirmpassword.maxlength'," +
                "}";
        return testData_jsonAsString;

    }

    public String[][] editBranchAsArray() {
        String[][] newBranches = {
                {"branchcincinatiNew", "OH234"},
                {"branchdaytonNew", "OH235"},
                {"branchchicgoNew", "IL236"}
        };
        return newBranches;
    }
    public String[][] editStaffsAsArray() {
        String[][] newStaffs = {
                {"StaffcincinnatiNew", "branchchicgo"},
                {"StaffdaytonNew", "branchcincinati"},
                {"StaffChicagoNew", "branchdayton"}
        };
        return newStaffs;
    }
    public String fieldErrorMessagesBranchModel(){
        String testData_jsonAsString = "{" +
                "'branchNameRequired': 'This field is required.'," +
                "'branchNameMinLength': 'This field is required to be at least 5 characters.'," +
                "'branchNameMaxLength': 'This field cannot be longer than 20 characters.'," +
                "'branchNamePattern': 'This field should follow pattern ^[a-zA-Z\\s]*$'," +
                "'branchCodeRequired': 'This field is required.'," +
                "'branchCodeMinLength': 'This field is required to be at least 2 characters.'," +
                "'branchCodeMaxLength': 'This field cannot be longer than 10 characters.'," +
                "'branchCodePattern': 'This field should follow pattern ^[A-Z0-9]*$.'," +
                "}";

        return  testData_jsonAsString;
    }
    public String fieldErrorValuesBranchModel(){
        String testData_jsonAsString = "{" +
                "'branchNameRequired': ''," +
                "'branchNameMinLength': 'bran'," +
                "'branchNameMaxLength': 'abcdefghijklAnopTDVCE'," +
                "'branchNamePattern': 'A123jk'," +
                "'branchCodeRequired': ''," +
                "'branchCodeMinLength': 'A'," +
                "'branchCodeMaxLength': 'A123CEDRPDD'," +
                "'branchCodePattern': 'a12'," +
                "}";

        return  testData_jsonAsString;
    }
    public ArrayList<String> getBranchModelExecutionOrder() {
        ArrayList<String> executionKeys = new ArrayList<>();
        executionKeys.add("branchNameRequired");
        executionKeys.add("branchNameMinLength");
        executionKeys.add("branchNameMaxLength");
        executionKeys.add("branchNamePattern");
        executionKeys.add("branchCodeRequired");
        executionKeys.add("branchCodeMinLength");
        executionKeys.add("branchCodeMaxLength");
        executionKeys.add("branchCodePattern");
        return executionKeys;
    }

    public String fieldLocatorBranchModel(){
        String testData_jsonAsString = "{" +
                "'branchNameRequired': 'editForm.name.$error.required'," +
                "'branchNameMinLength': 'editForm.name.$error.minlength'," +
                "'branchNameMaxLength': 'editForm.name.$error.maxlength'," +
               "'branchNamePattern': 'editForm.name.$error.pattern'," +
                "'branchCodeRequired': 'editForm.code.$error.required'," +
                "'branchCodeMinLength': 'editForm.code.$error.minlength'," +
                "'branchCodeMaxLength': 'editForm.code.$error.maxlength'," +
                "'branchCodePattern': 'editForm.code.$error.pattern'," +
                "}";

        return  testData_jsonAsString;
    }

    public String fieldErrorMessagesStaffModel(){
        String testData_jsonAsString = "{" +
                "'staffNameRequired': 'This field is required.'," +
               // "'staffNameMinLength': 'This field is required to be at least 5 characters.'," +
                "'staffNameMaxLength': 'This field cannot be longer than 50 characters.'," +
                "'staffNamePattern': 'This field should follow pattern ^[a-zA-Z\\s]*$.'," +
                "}";

        return  testData_jsonAsString;
    }

    public String fieldErrorValuesStaffEditModel(){
        String testData_jsonAsString = "{" +
                "'staffNameRequired': ''," +
               // "'staffNameMinLength': 'bran'," +
                "'staffNameMaxLength': 'abcdefghijklmnopqrstuvwabcdefghijklmnopqrstuvwabcde'," +
                "'staffNamePattern': '1A'," +
                "}";

        return  testData_jsonAsString;
    }
    public ArrayList<String> getStaffhModelExecutionOrder() {
        ArrayList<String> executionKeys = new ArrayList<>();
        executionKeys.add("staffNameRequired");
        //executionKeys.add("branchNameMinLength");
        executionKeys.add("staffNameMaxLength");
        executionKeys.add("staffNamePattern");
        return executionKeys;
    }
    public String fieldLocatorStaffModel(){
        String testData_jsonAsString = "{" +
                "'staffNameRequired': 'editForm.name.$error.required'," +
              //  "'staffNameMinLength': 'editForm.name.$error.minlength'," +
                "'staffNameMaxLength': 'editForm.name.$error.maxlength'," +
                "'staffNamePattern': 'editForm.name.$error.pattern'," +
                "}";

        return  testData_jsonAsString;
    }

}
