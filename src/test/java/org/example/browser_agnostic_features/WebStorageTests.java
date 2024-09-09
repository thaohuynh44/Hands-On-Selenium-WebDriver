package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;

import static org.assertj.core.api.Assertions.assertThat;

public class WebStorageTests {
    //WebStorage API allows web application to store data locally on the client file system
    //Two types of web storage:
    //window.localStorage: store data permanently
    //window.sessionStorage: store data during the session time (deleted when closing the browser tab)

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromedriver().create();
    }

    //WebDriver provides the WebStorage interface, need to cast driver
    @Test
    void testWebStorage() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-storage.html");

        WebStorage webStorage = (WebStorage) driver;
        LocalStorage localStorage = webStorage.getLocalStorage();

        System.out.println("Local storage elements: " + localStorage.size());

        SessionStorage sessionStorage = webStorage.getSessionStorage();
        sessionStorage.keySet()
                .forEach(key -> System.out.println("Session storage: " + key + "=" + sessionStorage.getItem(key)));

        assertThat(sessionStorage.size()).isEqualTo(2);

        sessionStorage.setItem("new element", "new value");
        assertThat(sessionStorage.size()).isEqualTo(3);
        driver.findElement(By.id("display-session")).click();
    }
}
