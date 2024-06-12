package com.testinium.utils;

import com.testinium.base.BrowserManager;
import com.testinium.configuration.ElementParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;


public class Methods {
    protected static final Logger logger = LogManager.getLogger(Methods.class);

    private static Methods instance;

    private Methods() {

    }

    public static Methods getInstance() {
        if (instance == null) {
            instance = new Methods();
        }
        return instance;
    }

    public WebElement findElement(String keyword) {
        WebElement element = null;
        try {
            logger.info("{} objesi aranıyor.", keyword);
            element = BrowserManager.getInstance().getWebDriverWait()
                    .until(ExpectedConditions.presenceOfElementLocated(ElementParser.getInstance().getByWithKeyword(keyword)));
            logger.info("{} objesi bulundu.", keyword);
        } catch (TimeoutException e) {
            Assertions.fail(keyword + " objesi verilen süre içerisinde bulunamamıştır.");

        }
        return element;
    }

    public List<WebElement> findElements(String keyword) {
        List<WebElement> elements = new ArrayList<>();
        try {
            logger.info("{} objesinin elementleri aranıyor.", keyword);
            elements = BrowserManager.getInstance().getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(ElementParser.getInstance().getByWithKeyword(keyword)));
            logger.info("{} adet element bulunmuştur.", elements.size());
        } catch (TimeoutException e) {
            Assertions.fail(keyword + " objesinin elementleri verilen süre içerisinde bulunamamıştır.");
        }
        return elements;
    }

    public WebElement findElementWithShadow(String keyword) {
        WebElement element = null;
        try {
            By by = ElementParser.getInstance().getByWithKeyword(keyword);
            logger.info("Shadow Root ile {} objesi aranıyor.", keyword);
            if (by instanceof By.ByXPath) {
                element = BrowserManager.getInstance().getShadowDriver().findElementByXPath(ElementParser.getInstance().getLocatorValue(keyword));
            } else {
                element = BrowserManager.getInstance().getShadowDriver().findElement(ElementParser.getInstance().getLocatorValue(keyword));
            }
            logger.info("Shadow Root ile {} objesi bulundu.", keyword);
        } catch (NoSuchElementException e) {
            Assertions.fail(keyword + " objesi bulunamamıştır.");
        }
        return element;
    }

    public List<WebElement> findElementsWithShadow(String keyword) {
        List<WebElement> elements = new ArrayList<>();
        try {
            By by = ElementParser.getInstance().getByWithKeyword(keyword);
            logger.info("Shadow Root ile {} objesinin elementleri aranıyor.", keyword);
            if (by instanceof By.ByXPath) {
                elements = BrowserManager.getInstance().getShadowDriver().findElementsByXPath(ElementParser.getInstance().getLocatorValue(keyword));
            } else {

                elements = BrowserManager.getInstance().getShadowDriver().findElements(ElementParser.getInstance().getLocatorValue(keyword));
            }
            if (elements.isEmpty()) {
                Assertions.fail(keyword + " objesinin elementleri bulunamamıştır.");
            }
            logger.info("{} adet element bulunmuştur.", elements.size());
        } catch (NoSuchElementException e) {
            Assertions.fail(keyword + " objesi bulunamamıştır.");
        }
        return elements;
    }


    public void waitDisplayed(String keyword) {
        BrowserManager.getInstance().getWebDriverWait()
                .until(ExpectedConditions.visibilityOfElementLocated(ElementParser.getInstance().getByWithKeyword(keyword)));
        logger.info("{} objesi göründü.", keyword);
    }

    public void click(String keyword) {
        findElement(keyword).click();
        logger.info("{} objesine tıklandı.", keyword);
    }

    public void shadowElementClick(String keyword) {
        findElementWithShadow(keyword).click();
        logger.info("{} objesine tıklandı.", keyword);
    }


    public void sendKeys(String keyword, String text) {
        waitDisplayed(keyword);
        findElement(keyword).sendKeys(text);
        logger.info("{} objesine {} değeri yazıldı.", keyword, text);
    }

    public void shadowElementSendKeys(String keyword, String text) {
        BrowserManager.getInstance().getWebDriverWait().until(ExpectedConditions.elementToBeClickable(findElement(keyword)));
        findElementWithShadow(keyword).sendKeys(text);
        logger.info("{} objesine {} değeri yazıldı.", keyword, text);
    }


    public void waitSeconds(int seconds) {
        try {
            if (seconds <= 0) seconds = 1;
            logger.info("{} saniye beklenecektir.", seconds);
            Thread.sleep(1000L * seconds);
            logger.info("{} saniye beklendi.", seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isElementVisible(String keyword) {
        return findElement(keyword).isDisplayed();
    }

    public String getText(String keyword) {
        waitDisplayed(keyword);
        String value = findElement(keyword).getText();
        logger.info("{} objesinin text değeri: {}'dir.", keyword, value);
        return value;
    }

    public boolean compareTextWithContains(String firstText, String secondText) {
        logger.info("Karşılaştırılacak metinler:\n[{}]\n[{}]", firstText, secondText);
        return firstText.contains(secondText);
    }
}
