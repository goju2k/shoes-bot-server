package com.my.document.generator.service;

import com.my.document.generator.enums.UserAgentEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class KreamService {

    public String getKreamAtm(String model) {

        String url = "https://www.kream.co.kr/search?keyword="+model;

        try{

            Document doc = Jsoup.connect(url)
                    .userAgent(UserAgentEnum.IPHONE.value())
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
                            .userAgent(UserAgentEnum.IPHONE.value())
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
