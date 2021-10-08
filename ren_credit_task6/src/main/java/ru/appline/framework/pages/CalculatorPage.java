package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CalculatorPage extends BasePage {

    @FindBy(xpath = "//span[@class='calculator__currency-field-btn']/span")
    private List<WebElement> listCurrency;
    @FindBy(xpath = "//input[@name='amount']")
    private WebElement inputAmount;
    @FindBy(xpath = "//select")
    private WebElement termSelect;
    @FindBy(xpath = "//input[@name='replenish']")
    private WebElement inputMonthlyAmount;
    @FindBy(xpath = "//div[@class='calculator__content']//span[@class='calculator__check-block-text']/span")
    private List<WebElement> listCheckBox;
    @FindBy(xpath = "//span[@class='js-calc-earned']")
    private WebElement earnedInfo;
    @FindBy(xpath = "//tr[@class='calculator__dep-result-table-row']/td[contains(text(),'Пополнение за')]/span")
    private WebElement monthsInfo;
    @FindBy(xpath = "//span[@class='js-calc-replenish']")
    private WebElement replenishInfo;
    @FindBy(xpath = "//span[@class='js-calc-result']")
    private WebElement allAmountInfo;

    @Step("Выбрать {currency}")
    public CalculatorPage selectCurrency(String currency) {
        for (WebElement currencyItem : listCurrency) {
            if (currencyItem.getText().equals(currency)) {
                waitUtilElementToBeClickable(currencyItem);
                currencyItem.click();
                return this;
            }
        }
        Assertions.fail("Валюта '" + currency + "' не найдена!");
        return this;
    }

    @Step("Выбрать сумма вклада {amount}")
    public CalculatorPage selectAmount(String amount) {
        inputAmount.click();
        inputAmount.clear();
        inputAmount.sendKeys(amount);
        wait.until(ExpectedConditions.attributeToBe(inputAmount, "value", amount));
        Assertions.assertEquals(amount, inputAmount.getAttribute("value")
                , "Поле 'Сумма вклада' было заполнено некорректно");

        return this;
    }

    @Step("Выбрать - {months}")
    public CalculatorPage selectTerm(String months) {
        Select term = new Select(termSelect);
        term.selectByVisibleText(months);
        return this;
    }

    @Step("Выбрать ежемесячное пополнение {amount}")
    public CalculatorPage selectMonthlyAmount(String amount) {
        inputMonthlyAmount.click();
        inputMonthlyAmount.clear();
        inputMonthlyAmount.sendKeys(amount);
        wait.until(ExpectedConditions.attributeToBe(inputMonthlyAmount, "value", amount));
        Assertions.assertEquals(amount, inputMonthlyAmount.getAttribute("value")
                , "Поле 'Ежемесячное пополнение' было заполнено некорректно");

        return this;
    }

    @Step("Выбрать чекбокс {nameField}")
    public CalculatorPage checkOrUncheckBox(String operation, String nameField) {
        String lastTimeAmount = allAmountInfo.getText();
        String booleanFlag = operation.equals("Поставить") ? "true" : "false";
        for (WebElement checkBox : listCheckBox) {
            if (checkBox.getText().equals(nameField)) {
                checkBox.click();
                Assertions.assertEquals(booleanFlag, checkBox.findElement(By.xpath("./../..//input")).getAttribute("checked")
                        , "CheckBox '" + nameField + "' не выполнился");
                while (waitUtilTextToBePresent(allAmountInfo, lastTimeAmount)) {
                }
                return this;
            }
        }

        Assertions.fail("CheckBox с наименованием '" + nameField + "' отсутствует на странице ");
        return null;

    }

    @Step("Проверка итоговой информации")
    public CalculatorPage checkInfo(String earned, String months, String replenish, String allAmount) {
        Assertions.assertEquals(earned, earnedInfo.getText(), "'Начисленно' не совпадает");
        if (months.equals("3"))
            Assertions.assertEquals(months + " месяца", monthsInfo.getText(), "'Количество месяцев' не совпадает");
        else
            Assertions.assertEquals(months + " месяцев", monthsInfo.getText(), "'Количество месяцев' не совпадает");
        Assertions.assertEquals(replenish, replenishInfo.getText(), "'Пополнение' не совпадает");
        Assertions.assertEquals(allAmount, allAmountInfo.getText(), "'К снятию' не совпадает");
        return this;
    }
}
