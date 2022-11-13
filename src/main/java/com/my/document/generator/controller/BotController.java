package com.my.document.generator.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/bot")
public class BotController {

    private void log(String s){
        System.out.println("[BotController] "+s);
    }
    String userAgentVal = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";

    @GetMapping("/kreamAmt/{model}")
    public String getKreamAtm(
            @PathVariable("model") String model
    ) throws IOException {

        String url = "https://www.kream.co.kr/search?keyword="+model;
        log(url);

        try{

            Document doc = Jsoup.connect(url)
                    .userAgent(userAgentVal)
                    .timeout(300000)
                    .get();

            //판매중 최저가격 정보
            Element elem = doc.selectFirst("div.amount");
            if(elem != null){
                String amt = elem.text();
                try{
                    Element elem2 = doc.selectFirst("a.item_inner");
                    String url2 = elem2.attr("href");
                    Document doc2 = Jsoup.connect("https://www.kream.co.kr"+url2)
                                        .userAgent(userAgentVal)
                                        .timeout(300000)
                                        .get();
                    Elements elem3 = doc2.select("dd.product_info");
                    amt += " [발매가] :"+elem3.get(3).text();

                }catch(Exception e2){

                }

                return amt;
            }

        }catch(Exception e){

        }

        return null;

    }

}
