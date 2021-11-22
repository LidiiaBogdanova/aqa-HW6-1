package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        var initialBalanceFirstCard = dashboardPage.getCardBalance(0);
        var initialBalanceSecondCard = dashboardPage.getCardBalance(1);
        var replenishmentPage = dashboardPage.secondCardReplenishment();
        replenishmentPage.doReplenishment(amountValue, DataHelper.getFirstCardInfo().getNumber());
        var currentBalanceFirstCard = dashboardPage.getCardBalance(0);
        var currentBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertTrue(initialBalanceFirstCard - currentBalanceFirstCard == amountValue);
        assertTrue(currentBalanceSecondCard - initialBalanceSecondCard == amountValue);


    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        var amountValue = 5000;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var initialBalanceFirstCard = dashboardPage.getCardBalance(0);
        var initialBalanceSecondCard = dashboardPage.getCardBalance(1);
        var replenishmentPage = dashboardPage.firstCardReplenishment();
        replenishmentPage.doReplenishment(amountValue, DataHelper.getSecondCardInfo().getNumber());
        var currentBalanceFirstCard = dashboardPage.getCardBalance(0);
        var currentBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertTrue(currentBalanceFirstCard - initialBalanceFirstCard == amountValue);
        assertTrue(initialBalanceSecondCard - currentBalanceSecondCard == amountValue);
    }

    @Test
    void shouldNotTransferMoneyFromFirstToSecondCard() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var initialBalanceFirstCard = dashboardPage.getCardBalance(0);
        var initialBalanceSecondCard = dashboardPage.getCardBalance(1);
        var amountValue = initialBalanceFirstCard*2;
        var replenishmentPage = dashboardPage.secondCardReplenishment();
        replenishmentPage.doReplenishment(amountValue, DataHelper.getFirstCardInfo().getNumber());
        var currentBalanceFirstCard = dashboardPage.getCardBalance(0);
        var currentBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertTrue(currentBalanceFirstCard == initialBalanceFirstCard);
        assertTrue(initialBalanceSecondCard == currentBalanceSecondCard);

    }

    @Test
    void shouldNotTransferMoneyFromSecondToFirstCard() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var initialBalanceFirstCard = dashboardPage.getCardBalance(0);
        var initialBalanceSecondCard = dashboardPage.getCardBalance(1);
        var amountValue = initialBalanceSecondCard*2;
        var replenishmentPage = dashboardPage.firstCardReplenishment();
        replenishmentPage.doReplenishment(amountValue, DataHelper.getSecondCardInfo().getNumber());
        var currentBalanceFirstCard = dashboardPage.getCardBalance(0);
        var currentBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertTrue(currentBalanceFirstCard == initialBalanceFirstCard);
        assertTrue(initialBalanceSecondCard == currentBalanceSecondCard);
    }
}
