package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TimeoutTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.edgedriver().create();
    }

    // WebDriver allows specifying three types of timeouts by invoking manage().timeouts()
    // 1. implicit wait:         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    // 2. page loading timeouts | TimeoutException
    // 3. script loading timeouts  | ScriptTimeoutException

    //Page loading timeout: (default 30 secs) the time limit to interrupt a navigation attempt, time in which a web page loaded. if timeout exceeds , exception is thrown
    @Test
    void testPageLoadTimeout() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(1));

        assertThatThrownBy(() -> driver.get("https://bonigarcia.dev/selenium-webdriver-java/"))
                .isInstanceOf(TimeoutException.class);
    }

    // Script loading timeout: time limit to interrupt a script that is being evaluated (default 300 secs)
    @Test
    void testScriptTimeout() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(3));

        assertThatThrownBy(() -> {
            long waitMilis = Duration.ofSeconds(5).toMillis();
            String script = "const callback = arguments[arguments.lenght - 1];"
                    + "window.setTimeout(callback, " + waitMilis + ");";
            js.executeAsyncScript(script);
        }).isInstanceOf(ScriptTimeoutException.class);
    }

}
