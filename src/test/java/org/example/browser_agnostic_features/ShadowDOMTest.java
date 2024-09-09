package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class ShadowDOMTest {
    //Shadow DOM enables the creation of scoped subtrees inside the regular DOM tree (encapsulate group of a DOM tree to apply different CSS styles from the original DOM)
    //Node in the regular DOM that shadow tree is attached is called the 'shadow host'
    //The root node of the shadow tree is called the 'shadow root'

    //Shadow tree is isolated from the original DOM, can be challenging for automated testing (since regular location strategies cannot find web elements within shadow tree)
    //Selenium WebDriver 4 provides a WebElement method allows access shadow DOM

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromedriver().create();
    }

    @Test
    void testShadowDOM() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/shadow-dom.html");

        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p")); //By.tagName() not working ???

        assertThat(textElement.getText()).isEqualTo("Hello Shadow DOM");
    }
}
