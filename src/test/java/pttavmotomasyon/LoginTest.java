package pttavmotomasyon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class LoginTest {

    private AppiumDriver driver;
    private WebDriverWait wait;

    // Locators
    private static final By PROFILE_BUTTON = By.id("com.pttem.epttavm:id/buttonOpenAccountPage");
    private static final By LOGIN_BUTTON = By.id("com.pttem.epttavm:id/buttonLogin");
    private static final By EMAIL_TEXT_BOX = By.id("com.pttem.epttavm:id/editTextUserEmail");
    private static final By PASSWORD_TEXT_BOX = By.id("com.pttem.epttavm:id/editTextPassword");
    private static final By HGS_BUTTON = By.id("com.pttem.epttavm:id/textViewBuyHGS");
    private static final By PROFILE_NAME = By.id("com.pttem.epttavm:id/textViewAccountName");

    // Navigation Bar Items
    private static final By ANASAYFA_BUTTON = By.xpath("(//android.widget.ImageView[@resource-id='com.pttem.epttavm:id/navigation_bar_item_icon_view'])[1]");
    private static final By KATEGORILER_BUTTON = By.xpath("//android.widget.TextView[@resource-id='com.pttem.epttavm:id/navigation_bar_item_small_label_view' and @text='Kategoriler']");
    private static final By SEPETIM_BUTTON = By.xpath("//android.widget.TextView[@resource-id='com.pttem.epttavm:id/navigation_bar_item_small_label_view' and @text='Sepetim']");
    private static final By FAVORILERIM_BUTTON = By.xpath("//android.widget.TextView[@resource-id='com.pttem.epttavm:id/navigation_bar_item_small_label_view' and @text='Favorilerim']");
    private static final By KAMPANYALAR_BUTTON = By.xpath("//android.widget.TextView[@resource-id='com.pttem.epttavm:id/navigation_bar_item_small_label_view' and @text='Kampanyalar']");

    @BeforeTest
    public void setUp() {
        setupDriver();
    }

    private void setupDriver() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("deviceName", "Genymotion");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("appium:platformVersion", "12");
            capabilities.setCapability("appium:appPackage", "com.pttem.epttavm");
            capabilities.setCapability("appium:appActivity", "com.pttem.epttavm.ui.activities.MainActivity");

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        } catch (MalformedURLException e) {
            System.out.println("Hata : " + e.getMessage());
        }
    }

    @Test
    public void testLogin() throws InterruptedException {
        performLogin("***", "***");
        navigateThroughApp();
        verifyProfileName();
    }

    private void performLogin(String email, String password) throws InterruptedException {
        clickElement(PROFILE_BUTTON);
        Thread.sleep(1000);
        clickElement(LOGIN_BUTTON);
        sendKeysToElement(EMAIL_TEXT_BOX, email);
        sendKeysToElement(PASSWORD_TEXT_BOX, password);
        clickElement(LOGIN_BUTTON);
    }

    private void navigateThroughApp() {
        clickElement(ANASAYFA_BUTTON);
        clickElement(KATEGORILER_BUTTON);
        clickElement(SEPETIM_BUTTON);
        clickElement(FAVORILERIM_BUTTON);
        clickElement(KAMPANYALAR_BUTTON);
    }

    private void verifyProfileName() {
        String profileNameText = getElementText(PROFILE_NAME);
        System.out.println("Profile Name : " + profileNameText);
        assertElementIsDisplayed(PROFILE_NAME);
    }

    private void clickElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void sendKeysToElement(By locator, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).sendKeys(text);
    }

    private String getElementText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    private void assertElementIsDisplayed(By locator) {
        boolean isVisible = driver.findElement(locator).isDisplayed();
        assert isVisible : "Element görünür değil.";
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
