package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        var amountValue = 5000;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var InitialBalanceFirstCard = dashboardPage.getCardBalance(0);
        var InitialBalanceSecondCard = dashboardPage.getCardBalance(1);
        var replenishmentPage = dashboardPage.secondCardReplenishment();
        replenishmentPage.doReplenishment(amountValue, DataHelper.getFirstCardInfo().getNumber());
        var CurrentBalanceFirstCard = dashboardPage.getCardBalance(0);
        var CurrentBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertTrue(InitialBalanceFirstCard - CurrentBalanceFirstCard == amountValue);
        assertTrue(CurrentBalanceSecondCard - InitialBalanceSecondCard == amountValue);


    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        var amountValue = 5000;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var InitialBalanceFirstCard = dashboardPage.getCardBalance(0);
        var InitialBalanceSecondCard = dashboardPage.getCardBalance(1);
        var replenishmentPage = dashboardPage.firstCardReplenishment();
        replenishmentPage.doReplenishment(amountValue, DataHelper.getSecondCardInfo().getNumber());
        var CurrentBalanceFirstCard = dashboardPage.getCardBalance(0);
        var CurrentBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertTrue(CurrentBalanceFirstCard - InitialBalanceFirstCard == amountValue);
        assertTrue(InitialBalanceSecondCard - CurrentBalanceSecondCard == amountValue);
    }

    @Test
    void shouldNotTransferMoneyFromFirstToSecondCard() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var InitialBalanceFirstCard = dashboardPage.getCardBalance(0);
        var InitialBalanceSecondCard = dashboardPage.getCardBalance(1);
        var amountValue = InitialBalanceFirstCard*2;
        var replenishmentPage = dashboardPage.secondCardReplenishment();
        replenishmentPage.doReplenishment(amountValue, DataHelper.getFirstCardInfo().getNumber());
        var CurrentBalanceFirstCard = dashboardPage.getCardBalance(0);
        var CurrentBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertTrue(CurrentBalanceFirstCard == InitialBalanceFirstCard);
        assertTrue(InitialBalanceSecondCard == CurrentBalanceSecondCard);

    }

    @Test
    void shouldNotTransferMoneyFromSecondToFirstCard() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var InitialBalanceFirstCard = dashboardPage.getCardBalance(0);
        var InitialBalanceSecondCard = dashboardPage.getCardBalance(1);
        var amountValue = InitialBalanceSecondCard*2;
        var replenishmentPage = dashboardPage.firstCardReplenishment();
        replenishmentPage.doReplenishment(amountValue, DataHelper.getSecondCardInfo().getNumber());
        var CurrentBalanceFirstCard = dashboardPage.getCardBalance(0);
        var CurrentBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertTrue(CurrentBalanceFirstCard == InitialBalanceFirstCard);
        assertTrue(InitialBalanceSecondCard == CurrentBalanceSecondCard);
    }
}
