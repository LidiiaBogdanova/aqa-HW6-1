package ru.netology.web.page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {
    private SelenideElement heading = $(Selectors.withText("Пополнение карты"));
    private SelenideElement amount=$("[data-test-id=amount] .input__control");
    private SelenideElement from=$("[data-test-id=from] .input__control");
    private SelenideElement replenishmentButton=$("[data-test-id='action-transfer']");

    public ReplenishmentPage() {
        heading.shouldBe(visible);
    }

    public void doReplenishment(int amountValue, String cardNumber){
        amount.setValue(String.valueOf(amountValue));
        from.setValue(cardNumber);
        replenishmentButton.click();
        //return new DashboardPage();
    }
}
