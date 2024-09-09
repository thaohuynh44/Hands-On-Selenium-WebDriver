package org.example.browser_agnostic_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;

public class DropdownListAndDataListTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromedriver().create();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
    }

    // classical HTML tags used to render dropdown lists are <select> and <options>
    // WebDriver provides a helper class called Select to simplify their manipulation, wrap a select WebElement and provides variety features

    @Test
    void testDropdownList() {
        Select select = new Select(driver.findElement(By.name("my-select")));

        String optionLabel = "Three";
        select.selectByVisibleText(optionLabel);

        assertThat(select.getFirstSelectedOption().getText()).isEqualTo(optionLabel);
    }

    //Another way to implement the dropdown list is using <datalist>
    //datalist is similar to <select> from the graphical point of view,
    //distinction:  select -> user choose option from the available options,
    //              datalist -> shows list of suggested options with input form text filed, users are free to select one of those or type a custom value.
    //WebDriver does not provide helper class for <datalist>, interact with them as standard input text

    @Test
    void testDatalist() {
        WebElement datalist =  driver.findElement(By.name("my-datalist"));
        datalist.click();

        WebElement option = driver.findElement(By.xpath("//datalist//option[2]"));
        String optionValue = option.getAttribute("value");

        datalist.sendKeys(optionValue);

        assertThat(optionValue).isEqualTo("New York");
    }
}
