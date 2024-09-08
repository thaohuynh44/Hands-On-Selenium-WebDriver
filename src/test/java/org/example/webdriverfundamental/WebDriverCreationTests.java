package org.example.webdriverfundamental;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

public class WebDriverCreationTests {
    @Test
    public void newOperator()
    {
        WebDriver driver = new ChromeDriver();
    }

    @Test
    public void webDriverBuilder()
    {
        // still need to resolve the required driver by using WebDriverManager
        WebDriverManager.chromedriver().setup();

        WebDriver driver = RemoteWebDriver.builder().oneOf(new ChromeOptions()).build();

        //Configure alternative browser
        WebDriver driver2 = RemoteWebDriver.builder().oneOf(new SafariOptions())
                .addAlternative(new ChromeOptions()).build();
    }

    @Test
    public void webDriverManagerSetup()
    {
        // WebDriverManager resolves the required driver and creates an instance of the WebDriver type
        WebDriver driver = WebDriverManager.chromedriver().create();
    }

}
