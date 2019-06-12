package Utils;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static Utils.TestBase.getDriver;

public class CommonSeleniumFunctions {
    WebDriver driver;
    final String _util_indicator = "(util)", _log_indicator = ">>>> ";

    public CommonSeleniumFunctions() {
        driver = getDriver();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getMainHeaderText(By locator) {
        isElementVisible(locator);
        return driver.findElement(locator).getText();
    }

    public void clickElement(By locator) {
        isClickable(locator);
        driver.findElement(locator).click();
    }

    public boolean checkElementPresent(String LableName) {
        return driver.getPageSource().contains(LableName);
    }

    public boolean checkElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public void setTextFieldValueFor(By locator, String value) {
        driver.findElement(locator).sendKeys(value);
    }


    public boolean isClickable(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isClickable(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForPageLoad() {
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

    public boolean isElementEnabled(By locator) {
        if (isElementVisible(locator)) {
            return driver.findElement(locator).isEnabled();
        } else {
            System.out.println(_log_indicator + _util_indicator + " Element does not exist, so cannot check if its enabled. Returning false...");
            return false;
        }
    }

    public boolean isElementVisible(By locator) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
//        driver.findElements(locator).clear();
//        return driver.findElement(locator).isDisplayed();
    }

    public boolean isElementNotVisible(By locator) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isCheckBoxChecked(By locator) {
        return driver.findElement(locator).getAttribute("checked").equals("true");
    }

    public String[][] getTableCount(By locator) throws InterruptedException {
        System.out.println(_log_indicator + _util_indicator + " Retrieving table content...");
        WebElement mytable = driver.findElement(By.xpath("//table/tbody"));
//        String[][] tableData;

        Thread.sleep(1000);
        isElementVisible(By.tagName("tr"));
        List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
        //To calculate no of rows In table.
        if (rows_table.size() < 1) {
            return null;
        } else {
            int rows_count = rows_table.size();
            int columns_count = 0;
            Thread.sleep(1000);
            isElementVisible(By.tagName("td"));
            if (rows_table.get(0).findElements(By.tagName("td")).size() > 0) {
                columns_count = rows_table.get(0).findElements(By.tagName("td")).size();
            }

            String[][] tableData = new String[rows_count][columns_count];


            for (int row = 0; row < rows_count; row++) {
                List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
                //System.out.println("Number of cells In Row " + row + " are " + columns_count);
                for (int column = 0; column < columns_count; column++) {
                    String celltext = Columns_row.get(column).getText();
                    tableData[row][column] = celltext;
                    // System.out.println("Cell Value Of row number " + row + " and column number " + column + " Is " + celltext);
                    //System.out.println("The Table Content is >>>>>" + rowData);
                }

            }

            //System.out.println("The Table Content is >>>>>" + rowData);
            //System.out.println("Array Size >>>>> "+ rowData);
            return tableData;
        }
    }


    public void clearText(By locator) {

        WebElement element = driver.findElement(locator);
        element.clear();
        //element.sendKeys(Keys.CONTROL + "a");
        //element.sendKeys(Keys.DELETE);
    }

}
