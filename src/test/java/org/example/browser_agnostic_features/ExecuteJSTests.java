package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExecuteJSTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromiumdriver().create();
//        JavascriptException js = (JavascriptException) driver;
    }

    // JS can manipulate DOM, user interaction, handle request and response from remote servers, working with regex
    // WebDriver allows injecting and executing JS
    // 3 categories: sync, pinned, async scripts
    // Any driver object that inherits from RemoteWebDriver also implement JavascriptExecutor -> cast to JavascriptExecutor

    //Synchronous scripts
    //allows executing a piece of JS in the context of the current web page in WebDrive session
    //blocks the control flow until the script terminates

    //situations that require executing JS are very heterogeneous
    // 2 examples: scrolling and handling color picker

    //Executing JS to scroll web page
    @Test
    void testScrollBy() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String script = "window.scrollBy(0, 1000);";
        js.executeScript(script);
    }

    @Test
    void testScrollIntoView() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement lastElement = driver.findElement(By.cssSelector("p:last-child"));
        String script = "arguments[0].scrollIntoView();";
        js.executeScript(script, lastElement);
    }

    //infinite scroll page: enables the dynamic loading of more content when the user reaches the end of the web page
    @Test
    void testInfiniteScroll() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By pLocator = By.tagName("p");


    }



}
