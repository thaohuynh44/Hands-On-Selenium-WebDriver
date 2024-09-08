package org.example.webdriverfundamental;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyBoardsActionTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromiumdriver().create();
    }

    // WebDriver allows impersonating keyboard user actions: sendKeys() and clear()

    @Test
    void testSendKeys() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement inputText = driver.findElement(By.name("my-text"));

        String textValue = "Hello World!";
        inputText.sendKeys(textValue);
        assertThat(inputText.getAttribute("value")).isEqualTo(textValue);

        inputText.clear();
        assertThat(inputText.getAttribute("value")).isEmpty();
    }

    //several use cases need to impersonate the keyboard actions: file upload (type="file") , range sliders (type="range")
    @Test
    void testFileUploading() throws IOException {
        // WebDriver API does not provide the mechanism to handle file inputs
        // treat the elements for uploading files as regular text inputs, need to simulate the user typing them
        // type the absolute file path to be uploaded

        String initUrl = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
        driver.get(initUrl);

        WebElement inputFile = driver.findElement(By.name("my-file"));

        //Create a temporary file
        Path tempFile = Files.createTempFile("tempfiles", ".tmp");
        String fileName = tempFile.toAbsolutePath().toString();
        System.out.println("Using temporal file " + fileName + " in file uploading");
        inputFile.sendKeys(fileName);

        driver.findElement(By.tagName("form")).submit();
        assertThat(driver.getCurrentUrl()).isNotEqualTo(initUrl);

        // When uploading file to a remote browser, need to load the file from the local file system explicitly: specify a local file detector
//        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());

    }

    @Test
    void testRangeSliders() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement slider = driver.findElement(By.name("my-range"));
        String intialValue = slider.getAttribute("value");
        System.out.println("The initial value of slider is " + intialValue);

        for(int i =0 ; i < 5 ; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }
        Thread.sleep(3000);

        String endValue = slider.getAttribute("value");
        System.out.println("The final value of slider is " + endValue);
        assertThat(intialValue).isNotEqualTo(endValue);
    }
}
