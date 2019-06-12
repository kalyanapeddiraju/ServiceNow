package pages;

import Utils.CommonSeleniumFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static Utils.TestBase.getDriver;

public class BranchesPage {

    CommonSeleniumFunctions commonSeleniumFunctions = new CommonSeleniumFunctions();
    String BranchesPage_title = "Branches";
    WebDriver driver;

    private String _branchesPage_Name = "[Branches Page]",
            _branchEditModal_Name = "[Edit Modal]",
            _branchVeiw_Name = "[Veiw All Branches]",
            _branchDeleteModal_Name = "[Delete Modal]",
            _branchesPage_indicator = ">> ";

    public BranchesPage() {
        driver = getDriver();
    }


    public final By brachestittlelocator = By.cssSelector("h2[translate='gurukulaApp.branch.home.title']"),
            createButtonlocator = By.cssSelector("span[translate='gurukulaApp.branch.home.createLabel']"),
            createEdidBranchTittlelocator = By.cssSelector("h4[translate='gurukulaApp.branch.home.createOrEditLabel']"),
            branchNameFiledLocator = By.name("name"),
            branchCodeFiledLocator = By.name("code"),
            branchSaveButtonFiledLocator = By.cssSelector("span[translate='entity.action.save']"),
            brachTableLocator = By.cssSelector("tr[ng-repeat='branch in branches']"),
            brachSeachLocator = By.id("searchQuery"),
            brachQuerySearchButtonLocator = By.cssSelector(".btn.btn-info");
    //brachQuerySearchButtonLocator=By.className("btn btn-info");


    public final By branchOptionViewLocator = By.cssSelector("button[ui-sref=\"branchDetail({id:branch.id})\"]"),
            branchOptionEditLocator = By.cssSelector("button[ng-click=\"showUpdate(branch.id)\"]"),
            branchOptionDeleteLocator = By.cssSelector("button[ng-click=\"delete(branch.id)\"]");

    public final By branchEditLabelLocator = By.id("myBranchLabel"),
            branchEditIdFieldLocator = By.cssSelector("input[ng-model=\"branch.id\"]"),
            branchEditNameFieldLocator = By.cssSelector("input[ng-model=\"branch.name\"]"),
            branchEditCodeFieldLocator = By.cssSelector("input[ng-model=\"branch.code\"]"),
            branchEditSaveLocator = By.cssSelector("button[ng-disabled=\"editForm.$invalid\"]"),
            branchEditCancelLocator = By.cssSelector("span[translate=\"entity.action.cancel\"]");

    public final By branchDelelteLabelLocator = By.cssSelector("h4[translate=\"entity.delete.title\"]"),
            branchDeleltequestionLocator = By.cssSelector("p[translate='gurukulaApp.branch.delete.question']"),
            branchDeleteButtonLocator = By.cssSelector("button[ng-disabled=\"deleteForm.$invalid\"]"),
            branchDeleteCancelButtonFiledLocator = By.cssSelector("span[translate='entity.action.cancel']");

    public final By branchPaginationFirstLink = By.cssSelector("li[ng-show=\"links['next']\"]"),
            branchPaginationPrevLink = By.cssSelector("li[ng-show=\"links['prev']\""),
            branchPaginationNextLink = By.cssSelector("li[ng-show=\"links['first']\"]"),
            branchPaginationLasttLink = By.cssSelector("li[ng-show=\"links['last']\"]");

    public boolean isCurrent() {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + " Checking if page is current...");
        commonSeleniumFunctions.waitForPageLoad();
        return driver.getTitle().equals(BranchesPage_title);
    }

    // check if present
    public boolean isOptionClickableAtIndex(int i, By locator) {
        // Waiting for one element that matches requirement
        commonSeleniumFunctions.isElementVisible(locator);
        // Getting list of elements that match requirement
        List<WebElement> allTargetOptionButtons = driver.findElements(locator);
        if (allTargetOptionButtons.size() < i) {
            System.out.println(_branchesPage_indicator + _branchesPage_Name + "(!) View button for row " + i + " was not found.");
            return false;
        } else {
            return allTargetOptionButtons.get(i).isEnabled();
        }
    }

    // click the button
    public void clickOptionAtIndex(int i, By locator) {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + " Clicking option at index " + i + "...");
        List<WebElement> allViewOptionButtons = driver.findElements(locator);
        if (allViewOptionButtons.size() < i) {
            System.out.println(_branchesPage_indicator + _branchEditModal_Name + " (!) View button for row " + i + " was not found.");
        } else {
            commonSeleniumFunctions.isClickable(allViewOptionButtons.get(i));
            allViewOptionButtons.get(i).click();
        }
    }

    // methods for the branch edit modal below
    public boolean isBranchEditModalDisplayed() {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchEditModal_Name + " Checking if edit modal is displayed...");
        return commonSeleniumFunctions.isElementVisible(branchEditLabelLocator);
    }

    public boolean isBranchEditModalNotDisplayed() {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchEditModal_Name + " Checking if edit modal is not displayed...");
        return commonSeleniumFunctions.isElementNotVisible(branchEditLabelLocator);
    }

    public boolean isBranchEditFieldEnabled(By locator) {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchEditModal_Name + " Checking if field is enabled...");
        commonSeleniumFunctions.isElementVisible(locator);
        return driver.findElement(locator).getAttribute("readonly") == null;
    }

    public String getBranchEditFieldValue(By locator) {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchEditModal_Name + " Getting field value...");
        commonSeleniumFunctions.isElementVisible(locator);
        return driver.findElement(locator).getAttribute("value");
    }

    public boolean isSaveButtonEnabledInModal() {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchEditModal_Name + " Checking if Save button is enabled...");
        WebElement element = driver.findElement(branchEditSaveLocator);
        return element.getAttribute("disabled") == null;
    }

    public boolean isCancelButtonEnabledInModal() {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchEditModal_Name + " Checking if Cancel button is enabled...");
        return commonSeleniumFunctions.isElementEnabled(branchEditCancelLocator);
    }

    public void clickSaveInEditModal() throws InterruptedException {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchEditModal_Name +
                " Clicking Save button...");
        commonSeleniumFunctions.isElementVisible(branchEditSaveLocator);
        driver.findElement(branchEditSaveLocator).click();
        Thread.sleep(1000);
        commonSeleniumFunctions.isElementNotVisible(By.className(".modal.fade.in"));
        commonSeleniumFunctions.isElementNotVisible(By.className(".modal.fade"));
    }

    public boolean isBranchDeleteButtonEnabledInModal() {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchDeleteModal_Name +
                " Checking if delete button is enabled...");
        WebElement element = driver.findElement(branchDeleteButtonLocator);
        return element.getAttribute("disabled") == null;
    }

    public boolean isBranchDeleteCancelButtonEnabledInModal() {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchDeleteModal_Name +
                " Checking if Branch delete > cancel button is enabled...");
        WebElement element = driver.findElement(branchDeleteCancelButtonFiledLocator);
        return element.getAttribute("disabled") == null;
    }

    public void clickDeleteButton() throws InterruptedException {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + " Clicking delete button...");
        commonSeleniumFunctions.clickElement(branchDeleteButtonLocator);
        Thread.sleep(1000);
        commonSeleniumFunctions.isElementNotVisible(By.className(".modal.fade.in"));
        commonSeleniumFunctions.isElementNotVisible(By.className(".modal.fade"));
    }

    public By getLocatorNgValue(String ngValue) {
        return By.cssSelector("p[ng-show='" + ngValue + "']");
    }

    public boolean isBranchPaginationEnabled(By locator) {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchVeiw_Name + " Checking if Pagination Links are enabled...");
        if (!commonSeleniumFunctions.isElementEnabled(locator)) {
            System.out.println(_branchesPage_indicator + _branchesPage_Name + " Element either does not exist or is not enabled. \n\tCannot read attributes, returning false...");
            return false;
        }
        return driver.findElement(locator).getAttribute("readonly") == null;
    }

    public boolean isBranchPaginationVisible(By locator) {
        System.out.println(_branchesPage_indicator + _branchesPage_Name + _branchVeiw_Name + " Checking if Pagination Links are enabled...");
        return commonSeleniumFunctions.isElementVisible(locator);
    }
}
