package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;


public class WindowSizeAndPositionTests {
    //Allows manipulating browser size and position using Window interface

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.edgedriver().create();
    }

    @Test
    void testWindow() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        WebDriver.Window window = driver.manage().window();

        Point initialPosition = window.getPosition();
        Dimension initialSize = window.getSize();
        System.out.println("Initial window: position" + initialPosition + " ,size " + initialSize);

        //maximize the window
        window.maximize();
        Thread.sleep(1000);

        // fullscreen = press F11
        window.fullscreen();
        Thread.sleep(1000);

        Point maximizePosition = window.getPosition();
        Dimension maximizeSize = window.getSize();
        System.out.println("Maximize window: position" + maximizePosition + " ,size " + maximizeSize);

        assertThat(initialSize).isNotEqualTo(maximizeSize);
        assertThat(initialPosition).isNotEqualTo(maximizePosition);
    }
}
