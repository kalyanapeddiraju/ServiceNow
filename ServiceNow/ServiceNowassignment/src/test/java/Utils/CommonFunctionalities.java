package Utils;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Utils.TestBase.getDriver;

public class CommonFunctionalities {
    WebDriver driver;

    public CommonFunctionalities() {
        driver = getDriver();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getMainHeaderText(By locator) {
        return driver.findElement(locator).getText();
    }

    public void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    public boolean checkElementPresnt(String LableName){

        return driver.getPageSource().contains(LableName);

    }

    public void setTextFieldValueFor(By locator, String value) {
        driver.findElement(locator).sendKeys(value);
    }


    public boolean isClickable(By locator) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForPageLoad(){

        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };

        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public boolean isElementEnabled( By locator){

        return driver.findElement(locator).isEnabled();
    }

    public boolean isElementVisable( By locator){

        return driver.findElement(locator).isDisplayed();
    }



}
