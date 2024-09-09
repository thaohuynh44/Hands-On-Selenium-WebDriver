package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class BrowserHistoryTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromedriver().create();
    }

    @Test
    void testHistory() {
        String baseUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        String firstPage = baseUrl + "navigation1.html";
        String secondPage = baseUrl + "navigation2.html";
        String thirdPage = baseUrl + "navigation3.html";

        driver.get(firstPage);

        driver.navigate().to(secondPage);
        driver.navigate().to(thirdPage);
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();

        assertThat(driver.getCurrentUrl()).isEqualTo(thirdPage);
    }
}
