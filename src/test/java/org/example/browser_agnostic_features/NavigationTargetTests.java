package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NavigationTargetTests {
    //Open new page in new browser tab
    //Force open new tab: by using modifier Key Ctrl with mouse click into a weblink
    //JS command: window.open(url)
    //Frame and iframe:
    //Frame (not encouraged): an HTML element type that defines a particular area (frameset) where a web page can be displayed
    //iFrame: another HTML element that allows embedding an HTML page into the current one

    //WebDriver provides the TargetLocator interface to deal with the target (tabs, windows, frames and iframes) to allow changing the focus of the future command of a WebDriver object
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromedriver().create();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
    }

    //Tabs and Windows:
    //Open new web page by opening the second tab in the current browser -> param: WindowType.TAB
    //Open new web page by opening a new window -> param: WindowType.WINDOW
    @Test
    void testNewTab() {
        String initHandle = driver.getWindowHandle();

        // open new tab and change the focus to it
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        assertThat(driver.getWindowHandles().size()).isEqualTo(2);

        driver.switchTo().window(initHandle);
        driver.close();
        assertThat(driver.getWindowHandles().size()).isEqualTo(1);
    }

    @Test
    void testNewWindow() {
        String initHandle = driver.getWindowHandle();

        // open new window and change the focus to it
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        assertThat(driver.getWindowHandles().size()).isEqualTo(2);

        driver.switchTo().window(initHandle);
        driver.close();
        assertThat(driver.getWindowHandles().size()).isEqualTo(1);
    }

    @Test
    void testIFrames() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/iframes.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // wait for the frame and also switching to it
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("my-iframe"));

        //switch To IFrame using Web Element
//        WebElement iframe = driver.findElement(By.id("my-iframe"));
        //Switch to the frame
//        driver.switchTo().frame(iframe);

        By pName = By.tagName("p");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pName, 0));
        List<WebElement> paragraphs = driver.findElements(pName);
        assertThat(paragraphs).hasSize(20);
    }

    @Test
    void testFrames() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/frames.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String frameName = "frame-body";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(frameName)));

        driver.switchTo().frame(frameName);

        By pName = By.tagName("p");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pName, 0));
        List<WebElement> paragraphs = driver.findElements(pName);
        assertThat(paragraphs).hasSize(20);
    }
}
