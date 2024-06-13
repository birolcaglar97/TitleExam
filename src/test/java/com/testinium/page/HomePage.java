package com.testinium.page;

import com.testinium.base.BaseTest;
import com.testinium.base.BrowserManager;
import com.testinium.utils.CsvHelper;
import com.testinium.utils.Methods;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

public class HomePage extends BaseTest {

    private String eventPrice = null;

    public HomePage acceptCookie(){
        if(Methods.getInstance().isElementVisibleWithoutWait("AllowAll")){
            Methods.getInstance().click("AllowAll");
        }
        return this;
    }
    public HomePage searchActivity(){
        Methods.getInstance().click("EtkinlikAraScroll");
        Methods.getInstance().click("EtkinlikAra");
        Methods.getInstance().sendKeys("EtkinlikAra",CsvHelper.getInstance().getValueWithRowAndColumn("test.csv",0,0,true));
        Methods.getInstance().waitSeconds(5);
        Methods.getInstance().click("LocationSelect");
        Methods.getInstance().click("LocationSelect");
        Methods.getInstance().sendEnter("EtkinlikAra");
        return this;
    }
    public HomePage selectActivity(){
        Methods.getInstance().click("EtkinlikSec");
        return this;
    }

    public HomePage getActivityPrice() {
        eventPrice = Methods.getInstance().getText("EtkinlikFiyat");
       return this;
    }

    public HomePage showTickets() {
        Methods.getInstance().click("EtkinlikBiletleriGor");
        return this;
    }

    public HomePage compareTicketPrices() {
        Methods.getInstance().waitDisplayed("BiletSecPenceresi");
        List<String> prices = Methods.getInstance().getTexts("EtkinlikBiletFiyatlari");
        for (int i = 0; i < prices.size(); i++) {
            if(eventPrice.contains(prices.get(i).replaceAll("\\nBilgi",""))){
                logger.info("Bilet fiyatı karşılaştırılıyor.Bilet fiyatları :{} Karşılaştırılan fiyat :{} . Bilgiler uyuşmaktadır.",eventPrice,prices.get(i).replaceAll("\\nBilgi",""));
            } else {
                logger.error("Karşılaştırılan fiyat bilgisi etkinlik sayfasındaki fiyat ile uyuşmamaktadır. Bilet sayfasındaki fiyat bilgisi :{} karşılaştırılan fiyat :{}",eventPrice,prices.get(i));
            }
        }
        return this;
    }

    public HomePage selectTicket() {
        if(Methods.getInstance().isElementVisibleWithoutWait("BiletSecSelect")){
            Methods.getInstance().selectByValue("BiletSecSelect","1");
        } else {
            logger.error("Seçili etkinlikte bilet kalmamıştır. Test Sonlandırılıyor.");
            Assertions.fail("Seçili etkinlikte bilet kalmamıştır. Test Sonlandırılıyor.");
        }
        return this;
    }

    public HomePage addTicketsToBasket() {
        Methods.getInstance().waitSeconds(1);
        Methods.getInstance().click("SeciliBiletleriAl");
        return this;
    }
    public HomePage login(){
        Methods.getInstance().sendKeys("UserName", CsvHelper.getInstance().getValueWithRowAndColumn("test.csv",0,1,true));
        Methods.getInstance().click("LoginEmailContinue");
        Methods.getInstance().sendKeys("Password", CsvHelper.getInstance().getValueWithRowAndColumn("test.csv",0,2,true));
        Methods.getInstance().waitSeconds(1);
        Methods.getInstance().click("LoginPasswordContinue");
        return this;
    }

    public HomePage compareBasketPrice() {
        Methods.getInstance().click("OdemeyeGec");
        Methods.getInstance().click("OdemeyeGec");
        String price = Methods.getInstance().getText("BasketPrice").replaceAll("TOPLAM","");
        if(eventPrice.contains(price)){
            logger.info("Fiyatlar uyuşmaktadır.");
        } else {
            logger.info("Fiyatlar sepet ile etkinlik sayfasında uyuşmamaktadır.");
        }
        return this;

    }

    public HomePage popupClose() {
        try {
            Methods.getInstance().click("PopUp");
        } catch (Exception e ){
            logger.info("Popup görüntülenmedi.");
        }
        return this;
    }

}
