package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CookiesTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromedriver().create();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");
    }

    //HTTP is stateless protocol
    //Cookies mechanism is an extension of HTTP that allows maintaining web session or personalize the user exp on the web
    //Cookie is a small pieces of text send from server to client, and client store it to use later

    // WebDriver enables managing the browser cookies, accessible through the webdriver obj
    // Cookie class as an abstraction of a single cookie in Selenium

    @Test
    void testReadCookies() {


        WebDriver.Options options = driver.manage();

        Set<Cookie> cookies = options.getCookies();
        assertThat(cookies).hasSize(2);

        Cookie username = options.getCookieNamed("username");
        assertThat(username.getValue()).isEqualTo("John Doe");
        assertThat(username.getPath()).isEqualTo("/");

        driver.findElement(By.id("refresh-cookies")).click();
    }

    @Test
    void testAddCookies() {
        WebDriver.Options options = driver.manage();

        Cookie newCookie  = new Cookie("new-cookie-key", "new-cookie-value");
        options.addCookie(newCookie);
        String readValue = options.getCookieNamed(newCookie.getName()).getValue();

        assertThat(newCookie.getValue()).isEqualTo(readValue);
        driver.findElement(By.id("refresh-cookies")).click();
    }

    @Test
    void testEditCookies() {
        WebDriver.Options options = driver.manage();

        Cookie username = options.getCookieNamed("username");
        Cookie editedCookie = new Cookie(username.getName(), "new-cookie-value");
        options.addCookie(editedCookie);

        Cookie readCookie = options.getCookieNamed(username.getName());
        assertThat(editedCookie).isEqualTo(readCookie);

        driver.findElement(By.id("refresh-cookies")).click();
    }

    @Test
    void deleteCookies() {
        WebDriver.Options options = driver.manage();

        Set<Cookie> cookies = options.getCookies();
        Cookie username = options.getCookieNamed("username");
        options.deleteCookie(username);

        assertThat(options.getCookies()).hasSize(cookies.size() - 1);
        driver.findElement(By.id("refresh-cookies")).click();
    }
}
