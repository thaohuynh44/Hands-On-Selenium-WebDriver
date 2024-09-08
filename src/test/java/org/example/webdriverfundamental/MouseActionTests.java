package org.example.webdriverfundamental;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class MouseActionTests {

    // Mouse actions:
    // Single-click (left-click / click)
    // Right-click (context-click)
    // double click, cursor movement, drag and drop, mouseover
    // Selenium allows impersonating these actions by using a helper class Actions
    // scrolling in WebDriver is possible by executing JavaScript

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromiumdriver().create();
    }

    @Test
    void testNavigation() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        driver.findElement(By.xpath("//a[text()='Navigation']")).click();
        driver.findElement(By.xpath("//a[text()='Next']")).click();
        driver.findElement(By.xpath("//a[text()='3']")).click();
        driver.findElement(By.xpath("//a[text()='2']")).click();
        driver.findElement(By.xpath("//a[text()='Previous']")).click();

        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertThat(bodyText).contains("Lorem ipsum");
    }

    @Test
    void testCheckboxes() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement checkbox2 = driver.findElement(By.id("my-checkbox-2"));
        checkbox2.click();
        assertThat(checkbox2.isSelected()).isTrue();

        WebElement radio2 = driver.findElement(By.id("my-radio-2"));
        radio2.click();
        assertThat(radio2.isSelected()).isTrue();
    }

    // to-do tests
    @Test
    void testRightClickDoubleClicks() {

    }

    @Test
    void testMouseover() {

    }

    @Test
    void testDragAndDrop() {

    }

    @Test
    void testClickAndHold() {

    }

    @Test
    void testCopyAndPaste() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        Actions actions = new Actions(driver);

        WebElement inputText = driver.findElement(By.name("my-text"));
        WebElement textArea = driver.findElement(By.name("my-textarea"));

        Keys modifier = SystemUtils.IS_OS_MAC ? Keys.COMMAND : Keys.CONTROL;
        actions.sendKeys(inputText, "hello world").keyDown(modifier)
                .sendKeys(inputText, "a").sendKeys(inputText, "c")
                .sendKeys(textArea, "v").build().perform();

        Thread.sleep(3000);

        assertThat(textArea.getAttribute("value")).isEqualTo(inputText.getAttribute("value"));

    }
}
