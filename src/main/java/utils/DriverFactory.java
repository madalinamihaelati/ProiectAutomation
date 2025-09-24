package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();
    private DriverFactory(){}

    public static WebDriver getDriver() {
        if (TL.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions co = new ChromeOptions();
            co.addArguments("--incognito","--start-maximized","--disable-notifications","--disable-infobars");
            // Oprește popupurile de “Save password”
            Map<String,Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            co.setExperimentalOption("prefs", prefs);
            TL.set(new ChromeDriver(co));

            WebDriver d = TL.get();
            d.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            d.manage().deleteAllCookies();
        }
        return TL.get();
    }

    public static void quitDriver() {
        WebDriver d = TL.get();
        if (d != null) {
            d.quit();
            TL.remove();
        }
    }
}
