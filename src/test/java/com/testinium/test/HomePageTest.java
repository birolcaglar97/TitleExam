package com.testinium.test;

import com.testinium.base.BaseTest;
import com.testinium.page.HomePage;
import com.testinium.utils.Methods;
import org.junit.jupiter.api.Test;

public class HomePageTest extends BaseTest {
    private HomePage homePage;
    public HomePageTest(){
        homePage = new HomePage();
    }

    @Test
    public void ticketTest(){
        homePage.acceptCookie()
                .searchActivity()
                .acceptCookie()
                .selectActivity()
                .getActivityPrice()
                .showTickets()
                .compareTicketPrices()
                .selectTicket()
                .addTicketsToBasket()
                .login()
                .compareBasketPrice();

    }
}
