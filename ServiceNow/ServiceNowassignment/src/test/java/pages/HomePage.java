package pages;

import Utils.CommonSeleniumFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utils.TestBase.getDriver;

public class HomePage extends CommonSeleniumFunctions {
    String HomePage_title = "Welcome to Gurukula!";
    WebDriver driver;
    private String _homePage_Name = "[Login Page]", log_indicator = ">> ";

    public HomePage() {
        driver = getDriver();
    }

    // Page elements
    public final By maintittlelocator1 = By.className("ng-scope"),
            maintittleLocator = By.cssSelector("h1[translate='main.title']"),
            homeMenuLocator = By.cssSelector("span[translate='global.menu.home']"),
            accountMenuLocator = By.cssSelector("span[translate='global.menu.account.main']"),
            loginLinkLocator = By.linkText("login"),
            newAccountRegistorLocator = By.linkText("Register a new account"),
            entitiesMenuLocator = By.cssSelector("span[translate='global.menu.entities.main']"),
            branchMenuLocator = By.cssSelector("span[translate='global.menu.entities.branch']"),
            staffMenuLocator = By.cssSelector("span[translate='global.menu.entities.staff']"),
            accountMenuLoginLocator = By.cssSelector("span[translate='global.menu.account.login']"),
            accountAuthenticateLocator = By.cssSelector("div[translate='global.menu.account.login']"),
            accountRegisterLocator = By.cssSelector("span[translate='global.menu.account.register']");


    public boolean isCurrent() {
        System.out.println(log_indicator + _homePage_Name + " Checking if page is current...");
        return driver.getTitle().equals(HomePage_title);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }


    public Boolean isMainHeaderPresent() {
        return driver.findElements(maintittlelocator1).size() > 0;
    }


    public void clickElement(By locator) {
        System.out.println(log_indicator + _homePage_Name + " Clicking element...");
        isClickable(locator);
        driver.findElement(locator).click();
    }


}