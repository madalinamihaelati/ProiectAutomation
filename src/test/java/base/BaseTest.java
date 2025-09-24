package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterEach
    void tearDown() {
        DriverFactory.quitDriver();
    }
}