package com.testinium.page;


import com.testinium.base.BaseTest;
import com.testinium.test.LoginPageTest;
import com.testinium.utils.Methods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;


public class LoginPage extends BaseTest {
    protected static final Logger logger = LogManager.getLogger(LoginPageTest.class);

    public LoginPage moveToLogin() {
        logger.info("Giriş ekranına gidiliyor.");
        Methods.getInstance().click("GirisYapVeyaUyeOl");
        Methods.getInstance().click("GirisYap");
        return this;
    }

    public LoginPage enterEmail(String email) {
        Methods.getInstance().sendKeys("KullaniciAdi", email);
        Methods.getInstance().click("LoginDevamEt");
        return this;
    }

    public LoginPage enterPassword(String password) {
        Methods.getInstance().sendKeys("Sifre", password);
        return this;
    }

    public LoginPage clickLogin() {
        Methods.getInstance().click("LoginButton");
        return this;
    }

    public LoginPage validateLogin() {
        Methods.getInstance().click("GirisYapVeyaUyeOl");
        if (Methods.getInstance().isElementVisible("Hesabim")) {
            logger.info("Başarı ile giriş yapılmıştır.");
        } else {
            String msg = "Giriş başarılı bir şekilde yapılamamıştır. Test Sonlandırılıyor";
            logger.error(msg);
            Assertions.fail(msg);
        }
        return this;
    }
}
