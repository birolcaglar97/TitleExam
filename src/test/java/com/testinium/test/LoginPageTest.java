package com.testinium.test;

import com.testinium.base.BaseTest;
import com.testinium.page.LoginPage;
import com.testinium.utils.CsvHelper;
import com.testinium.utils.ExcelHelper;
import com.testinium.utils.Methods;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("Teknosa Login Page Tests")
@Owner("Kazım Birol Çağlar - Testinium")
public class LoginPageTest extends BaseTest {
    LoginPage loginPage;

    public LoginPageTest() {
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("PozitifLoginSenaryosu")
    @Description("Pozitif login senaryosu")
    public void positiveLoginTest() {
        loginPage.moveToLogin()
                .enterEmail(CsvHelper.getInstance().getValueWithRowAndColumn("test.csv", 0, 0,true))
                .enterPassword(CsvHelper.getInstance().getValueWithRowAndColumn("test.csv", 0, 1,true))
                .clickLogin()
                .validateLogin();
    }

    @Test
    @DisplayName("NegatifLoginSenaryosu")
    @Description("Negatif login senaryosu")
    public void negativeLoginTest() {
        loginPage.moveToLogin()
                .enterEmail(ExcelHelper.getInstance().getValueFromCell("test.xlsx", 0, 1,true));
    }

}
