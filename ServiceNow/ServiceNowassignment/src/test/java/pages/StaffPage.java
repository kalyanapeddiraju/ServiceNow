package pages;

import Utils.CommonSeleniumFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static Utils.TestBase.getDriver;

public class StaffPage {

    String StaffPage_title = "Staffs";
    WebDriver driver;
    CommonSeleniumFunctions commonSeleniumFunctions = new CommonSeleniumFunctions();

    private String _staffsPage_Name = "[Staffs Page]",
            _staffPage_indicator = ">> ",
            _staffEditModal_Name = "[Edit Modal]",
            _staffDeleteModal_Name = "[Delete Modal]",
            _staffView_Name="[Veiw All Branches]";

    public final By staffPaginationNextLink = By.cssSelector("li[ng-show=\"links['next']\"]"),
                    staffPaginationPrevLink = By.cssSelector("li[ng-show=\"links['prev']\""),
                    staffPaginationFirstLink = By.cssSelector("li[ng-show=\"links['first']\"]"),
                    staffPaginationLasttLink = By.cssSelector("li[ng-show=\"links['last']\"]");


    public StaffPage() {
        driver = getDriver();
    }


    public final By Stafftittlelocator = By.cssSelector("h2[translate='gurukulaApp.staff.home.title']"),
            createButtonlocator = By.cssSelector("span[translate='gurukulaApp.staff.home.createLabel']"),
    //createrEdidBranchTittlelocator = By.cssSelector("span[translate='gurukulaApp.staff.home.createLabel']"),
    createrEdidBranchTittlelocator = By.cssSelector("button[data-target=\"#saveStaffModal\"]"),
            staffNameFiledLocator = By.cssSelector("input[ng-model='staff.name']"),
            branchNameDropDownFiledLocator = By.name("related_branch"),
            staffSaveButtonFiledLocator = By.cssSelector("span[translate='entity.action.save']"),
            staffCancelButtonFiledLocator = By.cssSelector("span[translate='entity.action.cancel']"),
            staffTableLocator = By.cssSelector("tr[ng-repeat='staff in staffs']"),
            staffSearchFiledLocator = By.id("searchQuery"),
            staffQuerySearchButtonLocator = By.cssSelector(".btn.btn-info");

    public final By staffOptionViewLocator = By.cssSelector("button[ui-sref=\"staffDetail({id:staff.id})\"]"),
            staffOptionEditLocator = By.cssSelector("button[ng-click=\"showUpdate(staff.id)\"]"),
            staffOptionDeleteLocator = By.cssSelector("button[ng-click=\"delete(staff.id)\"]");

    public final By staffEditLabelLocator = By.id("myStaffLabel"),
            staffEditIdFieldLocator = By.cssSelector("input[ng-model=\"staff.id\"]"),
            staffEditNameFieldLocator = By.cssSelector("input[ng-model=\"staff.name\"]"),
            staffEditBranchNameDropDownLocator = By.name("related_branch"),
    //staffEditBranchNameDropDownLocator = By.cssSelector("input[ng-model=\"staff.related_branchId\"]"),
    staffEditSaveLocator = By.cssSelector("button[ng-disabled=\"editForm.$invalid\"]"),
            staffEditCancelLocator = By.cssSelector("span[translate=\"entity.action.cancel\"]");

    public final By staffDelelteLabelLocator = By.cssSelector("h4[translate=\"entity.delete.title\"]"),
            staffDeleltequestionLocator = By.cssSelector("p[translate='gurukulaApp.staff.delete.question']"),
            staffDeleteButtonLocator = By.cssSelector("button[ng-disabled=\"deleteForm.$invalid\"]"),
            staffDeleteCancelButtonFiledLocator = By.cssSelector("span[translate='entity.action.cancel']");


    public boolean isCurrent() {
        System.out.println(_staffPage_indicator + _staffsPage_Name + " Checking if page is current...");
        return driver.getTitle().equals(StaffPage_title);
    }


    // check if present
    public boolean isOptionClickableAtIndex(int i, By locator) {
        // Waiting for one element that matches requirement
        commonSeleniumFunctions.isElementVisible(locator);
        // Getting list of elements that match requirement
        List<WebElement> allTargetOptionButtons = driver.findElements(locator);
        if (allTargetOptionButtons.size() < i) {
            System.out.println(">>> View button for row " + i + " was not found.");
            return false;
        } else {
            return allTargetOptionButtons.get(i).isEnabled();
        }
    }

    // click the button
    public void clickOptionAtIndex(int i, By locator) {
        commonSeleniumFunctions.waitForPageLoad();
        List<WebElement> allViewOptionButtons = driver.findElements(locator);
        if (allViewOptionButtons.size() <= i) {
            System.out.println(">>> View button for row " + i + " was not found.");
        } else {
            allViewOptionButtons.get(i).click();
        }
    }

    // methods for the branch edit modal below
    public boolean isStaffEditModalDisplayed() {
        return commonSeleniumFunctions.isElementVisible(staffEditLabelLocator);
    }

    public boolean isStaffEditModalNotDisplayed() {
        return commonSeleniumFunctions.isElementNotVisible(staffEditLabelLocator);
    }

    public boolean isStaffEditFieldEnabled(By locator) {
        commonSeleniumFunctions.isElementVisible(locator);
        return driver.findElement(locator).getAttribute("readonly") == null;
    }

    public String getStaffEditFieldValue(By locator) {
        commonSeleniumFunctions.isElementVisible(locator);
        return driver.findElement(locator).getAttribute("value");
    }

    public String getStaffEditDropDownValue(By locator) {

        commonSeleniumFunctions.isElementVisible(locator);
        return new Select(driver.findElement(locator)).getFirstSelectedOption().getText();

    }

    public boolean isSaveButtonEnabledInModal() {
        System.out.println(_staffPage_indicator + _staffsPage_Name + _staffEditModal_Name +
                " Checking if Save button is enabled...");
        WebElement element = driver.findElement(staffEditSaveLocator);
        return element.getAttribute("disabled") == null;
    }

    public boolean isCancelButtonEnabledInModal() {
        System.out.println(_staffPage_indicator + _staffsPage_Name + _staffEditModal_Name +
                " Checking if Cancel button is enabled...");
        return commonSeleniumFunctions.isElementEnabled(staffEditCancelLocator);
    }

    public void clickSaveInEditModal() throws InterruptedException {
        System.out.println(_staffPage_indicator + _staffsPage_Name + _staffEditModal_Name +
                " Clicking Save button...");
        commonSeleniumFunctions.isElementVisible(staffEditSaveLocator);
        driver.findElement(staffEditSaveLocator).click();
        Thread.sleep(1000);
        commonSeleniumFunctions.isElementNotVisible(By.className(".modal.fade.in"));
    }

    public boolean isStaffDeleteButtonEnabledInModal() {
        System.out.println(_staffPage_indicator + _staffsPage_Name + _staffDeleteModal_Name +
                " Checking if staff delete > delete button is enabled...");
        WebElement element = driver.findElement(staffDeleteButtonLocator);
        return element.getAttribute("disabled") == null;
    }

    public boolean isStaffDeleteCancelButtonEnabledInModal() {
        System.out.println(_staffPage_indicator + _staffsPage_Name + _staffDeleteModal_Name +
                " Checking if staff delete > cancel button is enabled...");
        WebElement element = driver.findElement(staffDeleteCancelButtonFiledLocator);
        return element.getAttribute("disabled") == null;
    }

    public By getLocatorNgValue(String ngValue) {
        return By.cssSelector("p[ng-show='" + ngValue + "']");
    }

    public boolean isStaffPaginationEnabled(By locator) {
        System.out.println(_staffPage_indicator + _staffsPage_Name + _staffView_Name + " Checking if Pagination Links are enabled...");
        return driver.findElement(locator).getAttribute("readonly") == null;
    }

    public boolean isStaffPaginationVisible(By locator) {
        System.out.println(_staffPage_indicator + _staffsPage_Name + _staffView_Name + " Checking if Pagination Links are visible...");
        return commonSeleniumFunctions.isElementVisible(locator);
    }
}
