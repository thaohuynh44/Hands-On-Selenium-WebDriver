package org.example.webdriverfundamental;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class WebDriverDisposal {
    @Test
    public void testDisposal()
    {
        // there are 2 ways to dispose the webDriver
        // quit(): as the general , closes the browser and every associated window
        // close(): terminate only the current window, use it in case of handling different windows (or tabs) in the same browser, finish some the windows and still use the rest

        WebDriver driver = WebDriverManager.firefoxdriver().create();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
//        driver.close();
        driver.quit();
    }

}
