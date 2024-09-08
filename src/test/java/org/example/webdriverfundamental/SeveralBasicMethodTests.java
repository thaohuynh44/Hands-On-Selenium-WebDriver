package org.example.webdriverfundamental;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class SeveralBasicMethodTests {
    WebDriver driver;

    @BeforeEach
    void setUp()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    void testBasicMethods()
    {
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);

        assertThat(driver.getTitle()).isEqualTo("Hands-On Selenium WebDriver with Java");
        assertThat(driver.getCurrentUrl()).isEqualTo(sutUrl);
        assertThat(driver.getPageSource()).containsIgnoringCase("</html>");
    }


}
