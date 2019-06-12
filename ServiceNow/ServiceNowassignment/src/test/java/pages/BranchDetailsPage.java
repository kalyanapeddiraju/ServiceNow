package pages;

import Utils.CommonSeleniumFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static Utils.TestBase.getDriver;

public class BranchDetailsPage {
    // This class is created separately to enable page level component edits and validations.

    CommonSeleniumFunctions commonSeleniumFunctions = new CommonSeleniumFunctions();
    String BranchDetailsPage_title = "Branch";
    WebDriver driver;
    public By backButtonLocator = By.cssSelector("span[translate=\"entity.action.back\"]"),
            cancelButtonLocator = By.cssSelector("span[translate=\"entity.action.cancel\"]"),
            dataFieldLocator = By.cssSelector("input");
    String pageHeadingLocatorString = "h2";

    private String BranchDetailsPage_Name = "[Branch Details Page]", BranchDetailsPage_indicator = ">> ";

    public BranchDetailsPage() {
        driver = getDriver();
    }

    public final By titleLocator = By.cssSelector("translate=\"gurukulaApp.branch.detail.title\"");

    public boolean isCurrent() {
        System.out.println(BranchDetailsPage_indicator + BranchDetailsPage_Name + " Checking if page is current...");
        commonSeleniumFunctions.waitForPageLoad();
        return driver.getTitle().equals(BranchDetailsPage_title);
    }

    public boolean isHeadingCorrect(int branchId) {
        List<WebElement> elements = driver.findElements(By.cssSelector(pageHeadingLocatorString));
        if (elements.size() < 1) {
            return false;
        }
        return elements.get(0).getText().equals("Branch " + branchId);
    }

    public boolean getBranchNameFieldEnabled() {
        commonSeleniumFunctions.isElementVisible(dataFieldLocator);
        List<WebElement> elements = driver.findElements(dataFieldLocator);
        return elements.get(0).isEnabled();
    }

    public String getBranchNameFieldValue() {
        commonSeleniumFunctions.isElementVisible(dataFieldLocator);
        List<WebElement> elements = driver.findElements(dataFieldLocator);
        return elements.get(0).getAttribute("value");
    }

    public boolean getBranchCodeFieldEnabled() {
        commonSeleniumFunctions.isElementVisible(dataFieldLocator);
        List<WebElement> elements = driver.findElements(dataFieldLocator);
        return elements.get(1).isEnabled();
    }

    public String getBranchCodeFieldValue() {
        commonSeleniumFunctions.isElementVisible(dataFieldLocator);
        List<WebElement> elements = driver.findElements(dataFieldLocator);
        return elements.get(1).getAttribute("value");
    }

    public void clickBackButton() {
        driver.findElement(backButtonLocator).click();
    }

}
