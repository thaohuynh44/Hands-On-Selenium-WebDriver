package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class DialogBoxTests {
    // Javascript provides different dialog boxes (pop-ups) to interact
    // Alert: show a message and wait for user to press OK (only choice)
    // Confirm: show a dialog box with question and 2 buttons: OK and Cancel
    // Prompt: show a dialog with a text message, an input field and the buttons OK and cancel
    // Modal window: dialog that implementing by CSS, disable the main window (but keeps it visible) while overlaying a child pop-up, shows a message and some buttons

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromedriver().create();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
    }

    //WebDriver provides the interface Alert to manipulate JS dialogs (alert, confirm, prompt)
    @Test
    void testAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("my-alert")).click();
//        wait.until(ExpectedConditions.alertIsPresent());
//
//        Alert alert = driver.switchTo().alert();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertThat(alert.getText()).isEqualTo("Hello world!");
        alert.accept();
    }

    @Test
    void testConfirm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("my-confirm")).click();
        wait.until(ExpectedConditions.alertIsPresent());

        Alert confirm  =  driver.switchTo().alert();
        assertThat(confirm.getText()).isEqualTo("Is this correct?");

        //click Cancel button
        confirm.dismiss();
    }

    @Test
    void testPrompt() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("my-prompt")).click();
        wait.until(ExpectedConditions.alertIsPresent());

        Alert prompt = driver.switchTo().alert();

        //somehow not working in Chrome
        prompt.sendKeys("John Doe");
        assertThat(prompt.getText()).isEqualTo("Please enter your name");

        prompt.accept();
    }

    @Test
    void testModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("my-modal")).click();
        WebElement close = driver.findElement(By.xpath("//button[text()='Close']"));

        assertThat(close.getTagName()).isEqualTo("button");
        wait.until(ExpectedConditions.elementToBeClickable(close));
        close.click();
    }

}
