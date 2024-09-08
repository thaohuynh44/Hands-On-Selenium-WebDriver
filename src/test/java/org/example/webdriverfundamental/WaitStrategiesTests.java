package org.example.webdriverfundamental;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class WaitStrategiesTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromedriver().create();
        driver.manage().window().maximize();
    }

    // three principal waiting strategies: implicit wait, explicit wait, and fluent waits

    // Thread.sleep is considered as a bad smell

    // IMPLICIT WAIT
    // Implicit wait is considered as a bad practice, explicit and fluent waits are more preferable
    // By default implicit wait has zero seconds (not wait at all), implicit polling time is depends on driver implementation and frequently less than 500ms
    // if the element not present in elapsed time, exception is thrown
    // implicit waits only work on finding elements, and are applied globally -> checking the absence of web elements usually increases the execution time for the entire scripts



    @Test
    void testImplicitWait() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement landscape = driver.findElement(By.id("landscape"));
        assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
    }

    //EXPLICIT WAIT
    //allows pausing the execution a maximum amount of time until a specific condition happens
    // need to create an instance of WebDriverWait
    // WebDriver provides a comprehensive set of expected conditions using the ExpectedConditions class

    @Test
    void testExplicitWaits() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
        assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
    }

    @Test
    void testSlowCalculator() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");

        // 1 + 3
        driver.findElement(By.xpath("//span[text()='1']")).click();
        driver.findElement(By.xpath("//span[text()='+']")).click();
        driver.findElement(By.xpath("//span[text()='3']")).click();
        driver.findElement(By.xpath("//span[text()='=']")).click();

        // ... should be 4, wait for it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(By.className("screen"), "4"));
    }

    //FLUENT WAIT
    //is a generalization of explicit waits
    // WebDriverWait -> FluentWait -> Wait
    //pausing the execution until certain conditions
    //provides fine-grained configuration capabilities (fluent API) : can chain several invocations

//    withTimeout()
//    pollingEvery() : default 500ms, how often the condition is evaluated
//    withMessage: custom error message
//    ignoring: ignore specific exceptions while waiting for condition

    @Test
    void testFluentWaits() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
        assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
    }

    // Complementary characteristics in Selenium WebDriver should be aware of:
//    Loading strategy: specify different approaches for page loading, accessible through browser-specific capabilities (ChromeOptions...): Page Loading Strategy
//    Timeouts: specify the maximum elapsed times for page loading and script loading




}
