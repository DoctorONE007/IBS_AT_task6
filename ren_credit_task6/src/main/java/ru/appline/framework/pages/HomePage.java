package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Реализация домашней страницы
 */
public class HomePage extends BasePage {

    @FindBy(xpath = "//li[@class='nav__item nav__item_parent']/a/span[not(@class='nav__link-icon')]")
    private List<WebElement> listBaseMenu;

    @FindBy(xpath = "//nav[@class='nav__item-sub-nav-secondary nav nav_secondary nav_inner nav_sub']//a")
    private List<WebElement> listSubMenu;


    /**
     * Функция клика на любой пункт меню
     *
     * @param nameBaseMenu - наименование меню
     * @return HomePage - т.е. остаемся на этой странице
     */
    @Step("Открытие меню {nameBaseMenu}")
    public HomePage selectBaseMenu(String nameBaseMenu) {
        for (WebElement menuItem : listBaseMenu) {
            if (menuItem.getText().equals(nameBaseMenu)) {
                waitUtilElementToBeClickable(menuItem);
                menuItem.click();
                return this;
            }
        }
        Assertions.fail("Меню '" + nameBaseMenu + "' не было найдено на стартовой странице!");
        return this;
    }

    /**
     * Функция клика на любое подменю
     *
     * @param nameSubMenu - наименование подменю
     * @return InsurancePage - т.е. переходим на страницу {@link CalculatorPage}
     */
    @Step("Открыть подменю {nameSubMenu}")
    public CalculatorPage selectSubMenu(String nameSubMenu) {
        for (WebElement menuItem : listSubMenu) {
            if (menuItem.getText().equalsIgnoreCase(nameSubMenu)) {
                waitUtilElementToBeClickable(menuItem);
                menuItem.click();
                return pageManager.getCalculatorPage();
            }
        }
        Assertions.fail("Подменю '" + nameSubMenu + "' не было найдено на стартовой странице!");
        return pageManager.getCalculatorPage();
    }


}
