package org.example.webdriverfundamental;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import static org.assertj.core.api.Assertions.assertThat;

public class WebDriverMethodsTest {
    WebDriver driver = WebDriverManager.chromedriver().create();

    @Test
    public void loadWebPage()
    {
        //load the webpage in the current browser
        // wait until the page has fully loaded (onload event has fired) before returning control to test script. Have to using waits if test agains the SPA app (AJAX calls)
        // refresh the page to changing the URL
        // can get lost the browser history due to it refresh the page

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        String currentUrl = driver.getCurrentUrl();
        // get the <title> value
        String currentTitle = driver.getTitle();
        // get the HTML source code
        String webSource = driver.getPageSource();

        System.out.println(currentUrl);
        System.out.println(currentTitle);
        System.out.println(webSource);
    }

    @Test
    public void navigateWeb()
    {
        // navigate interface  exposes the ability to move backwards and forwards in your browser’s history
        // navigate remains the browser history
        // not refresh the page in case of SPA
        // navigate().to() behaves similarly to the get()

        driver.navigate().to("https://bonigarcia.dev/selenium-webdriver-java/");

        // will return the unique identifier for open window in current browser
        String currentWindowHandle  = driver.getWindowHandle();
        //getWindowHandles() : set of window in current browser

        System.out.println(currentWindowHandle);
    }

    @Test
    public void switchingFrame() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/frames.html");

        String beforeSwitching = driver.getWindowHandle();
        System.out.println(beforeSwitching);

        WebElement headerFrame = driver.findElement(By.name("frame-header"));
        driver.switchTo().frame(headerFrame);

        // not working for frame!
        String afterSwitching = driver.getWindowHandle();
        System.out.println(afterSwitching);
    }

    @Test
    public void manageBrowser()
    {
        //manage() is a generic utility for managing different aspects of browser (e.g. browser size, position, cookies, timeouts, or logs)
        var options = driver.manage();


        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        // close the current windows, quiting the browser if there are no more windows opened
        driver.close();

        // close all windows and quit the browser: will call the Dispose() to safely end session
        driver.quit();
    }

    @Test
    public void sessionIdentifier()
    {
        //each time instantiating a WebDriver object, the underlying driver (chromedriver, geckodriver...) creates a unique identifier called sessionId to track the browser session
        // getSessionId() belongs to the RemoteWebDriver -> need to cast

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        assertThat(sessionId).isNotNull();

        System.out.println("The session Id is " + sessionId.toString());
    }
}
