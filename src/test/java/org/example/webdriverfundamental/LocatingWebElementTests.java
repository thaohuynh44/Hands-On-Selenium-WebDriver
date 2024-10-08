package org.example.webdriverfundamental;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LocatingWebElementTests {
    // WebDriver API uses the interface WebElement (abstraction for HTML DOM) to interact with different elements in the web page
    // findElement()
    // findElements()
    // both methods accept the By param (specify the location strategy)

    //DOM : allows representing the XML-like documents in a tree structure
    // each HTML tag: DOM node (root node /element is the <html> tag)
    // each HTML attribute: DOM property
    // text content of HTML tags is in the resulting tree also

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverManager.chromiumdriver().create();
    }

    @Test
    void testClickElement() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement returnToIndexLnk = driver.findElement(By.linkText("Return to index"));
        returnToIndexLnk.click();

        assertThat(driver.getTitle()).isEqualTo("Hands-On Selenium WebDriver with Java");
    }

    @Test
    void testSubmitElement() {
        // use the submit() inside of form tag to submit the form

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement textInputTxt = driver.findElement(By.id("my-text-id"));
        WebElement passwordTxt = driver.findElement(By.name("my-password"));
        WebElement textAreaTxt = driver.findElement(By.name("my-textarea"));

        textInputTxt.sendKeys("Test text input");
        textInputTxt.submit();
//        passwordTxt.sendKeys("password");
//        textAreaTxt.sendKeys("Test text area");


    }

    @Test
    void testLocationStrategies() {
        // WebDriver API provides 8 location strategies
        // other advanced strategies: compound and relative locators

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        // by HTML tag name
        WebElement textArea = driver.findElement(By.tagName("textarea"));
        assertThat(textArea.getDomAttribute("rows")).isEqualTo("3");

        // by HTML attribute: standard attributes (class, name, id). nonstandard attributes (i.e. myprop)
        WebElement textByName = driver.findElement(By.name("my-text"));
        assertThat(textByName.isEnabled()).isTrue();

        // by id
        //!!! ids can be autogenerated and volatile between different sessions
        WebElement textById = driver.findElement(By.id("my-text-id"));
        assertThat(textById.getAttribute("type")).isEqualTo("text");
        assertThat(textById.getDomAttribute("type")).isEqualTo("text");
        assertThat(textById.getDomProperty("type")).isEqualTo("text");

        assertThat(textById.getAttribute("myprop")).isEqualTo("myvalue");
        assertThat(textById.getDomAttribute("myprop")).isEqualTo("myvalue");
        assertThat(textById.getDomProperty("myprop")).isNull();

        // by className
        List<WebElement> byClassName = driver.findElements(By.className("form-control"));

        assertThat(byClassName.size()).isPositive();
        assertThat(byClassName.get(0).getAttribute("name")).isEqualTo("my-text");

        // by link text
        // twofold: locate by exact and by partial text occurence

        WebElement linkByText = driver.findElement(By.linkText("Return to index"));
        assertThat(linkByText.getTagName()).isEqualTo("a");
        assertThat(linkByText.getCssValue("cursor")).isEqualTo("pointer");

        WebElement linkPartialText = driver.findElement(By.partialLinkText("index"));
        assertThat(linkPartialText.getLocation()).isEqualTo(linkByText.getLocation());
        assertThat(linkPartialText.getRect()).isEqualTo(linkByText.getRect());
    }

    @Test
    void locatingByCssSelector() {
        // straightforward strategies below have some limitations: not always avaialble, not unique, can be changed between sessions -> to overcome: WebDriver provides CssSelector and XPath

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement hidden = driver.findElement(By.cssSelector("input[type=hidden]"));
        assertThat(hidden.isDisplayed()).isFalse();

        // use pseudoclass checked to locate clicked checkboxes
        WebElement checkbox1 = driver.findElement(By.cssSelector("[type=checkbox]:checked"));
        assertThat(checkbox1.getAttribute("id")).isEqualTo("my-check-1");

        WebElement checkbox2 = driver.findElement(By.cssSelector("[type=checkbox]:not(:checked)"));
        assertThat(checkbox2.getAttribute("id")).isEqualTo("my-check-2");
    }

    @Test
    void testXPathlocators() {
        // XPath (XML Path Language): powerful way of navigating to the DOM of XML-like documents as HTML pages
        // Use to create advanced queries to select node

        // Absolute path: start with / i.e. /html/body/a (discouraged due to minimal change in layout would make a locator fail)
        // Relative path: start with // i.e. //tagname[@attribute='value']

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement hidden = driver.findElement(By.xpath("//input[@type='hidden']"));
        assertThat(hidden.isDisplayed()).isFalse();

        // advanced built-in functions of XPath
        WebElement radio1 = driver.findElement(By.xpath("//*[@type='radio' and @checked]"));
        assertThat(radio1.getAttribute("id")).isEqualTo("my-radio-1");
        assertThat(radio1.isSelected()).isTrue();

        WebElement radio2 = driver.findElement(By.xpath("//*[@type='radio' and not(@checked)]"));
        assertThat(radio2.getAttribute("id")).isEqualTo("my-radio-2");
        assertThat(radio2.isSelected()).isFalse();

    }

    @Test
    void compoundStrategies() {
        // support classes that enable the composition of different locator types

        // ByIdOrName: seeks by id, if not available, seeks by name
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement fileElement = driver.findElement(new ByIdOrName("my-file"));
        assertThat(fileElement.getAttribute("id")).isBlank();
        assertThat(fileElement.getAttribute("name")).isNotBlank();

        // ByChained: seeks elements in a sequence (second el should appear inside the first one. and so on)
        List<WebElement> rowsInForm = driver.findElements(new ByChained(By.tagName("form"), By.className("row")));
        assertThat(rowsInForm.size()).isEqualTo(1);

        // ByAll: seeks elements that match a number of location strategies
        List<WebElement> allRowsInForm = driver.findElements(new ByAll(By.tagName("form"), By.className("row")));
        assertThat(allRowsInForm.size()).isEqualTo(5);  //find 5 elements, since the locator matches a <form> plus 4 element class=row are available on the page
    }

    @Test
    void testRelativeLocators() {
        // a new way in Selenium WebDriver version4
        // find elements relative to another known element, based on the CSS box model (model determine each element of the web document is rendered using a rectangular box)
        // relative locators available in the Selenium WebDriver API allow finding elements in relation to the position of another web element

        // with() of RelativeLocator: above(), below(), near(), toLeftOf(), toRightOf()
        // can be very sensitive to page layout, need to be careful when using in responsive pages, because the layout can vary depending on the viewport
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement link = driver.findElement(By.linkText("Return to index"));
        RelativeLocator.RelativeBy relative = RelativeLocator.with(By.tagName("input"));
        WebElement readonly = driver.findElement(relative.above(link));
        assertThat(readonly.getAttribute("name")).isEqualTo("my-readonly");
    }

    @Test
    void testDatePicker() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        // Get the current date from the system clock
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentDay = today.getDayOfMonth();

        // click on the date picker to open the calendar
        WebElement datePicker = driver.findElement(By.name("my-date"));
        datePicker.click();

        //Click on the current month by search by text
        WebElement monthElement = driver.findElement(By.xpath(String.format("//th[contains(text(), '%d')]", currentYear)));
        monthElement.click();

        //Click on the left arrow using relative locators
        WebElement arrowLeft = driver.findElement(RelativeLocator.with(By.tagName("th")).toRightOf(monthElement));
        arrowLeft.click();


        //Click on the current month of that year
//        WebElement monthPastYear = driver.findElement(RelativeLocator.with())
        // to-do
        Thread.sleep(3000);
    }


}

