package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement firstCard = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement secondCard = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement replenishmentButton1 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"] .button__text");
    private SelenideElement replenishmentButton2 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"] .button__text");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(int index) {
        val text = cards.get(index).text();
        return extractBalance(text);
    }


    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public ReplenishmentPage firstCardReplenishment() {
        replenishmentButton1.click();
        return new ReplenishmentPage();
    }

    public ReplenishmentPage secondCardReplenishment() {
        replenishmentButton2.click();
        return new ReplenishmentPage();
    }
}