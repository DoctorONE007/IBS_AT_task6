package ru.appline.framework.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.appline.framework.basetestsclass.BaseTests;
import ru.appline.framework.utils.Listener;

@ExtendWith(Listener.class)
public class FirstTest extends BaseTests {
    @Test
    public void rubTest() {
        app.getHomePage()
                .selectBaseMenu("Вклады")
                .selectSubMenu("Калькулятор доходности")
                .selectCurrency("Рубли")
                .selectAmount("300 000")
                .selectTerm("6 месяцев")
                .selectMonthlyAmount("50 000")
                .checkOrUncheckBox("Поставить", "Ежемесячная капитализация")
                .checkInfo("12 781,28","6","250 000","562 781,28");

    }
    @Test
    public void dollarTest() {
        app.getHomePage()
                .selectBaseMenu("Вклады")
                .selectSubMenu("Калькулятор доходности")
                .selectCurrency("Доллары США")
                .selectAmount("500 000")
                .selectTerm("12 месяцев")
                .selectMonthlyAmount("20 000")
                .checkOrUncheckBox("Поставить", "Ежемесячная капитализация")
                .checkInfo("920,60","12","220 000","720 920,60");

    }
}
