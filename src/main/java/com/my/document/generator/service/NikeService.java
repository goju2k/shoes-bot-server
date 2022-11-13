package com.my.document.generator.service;

import com.my.document.generator.enums.UserAgentEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NikeService {

    private final Logger logger = LoggerFactory.getLogger(NikeService.class);

    private Map<String,Long> NIKE_MODELS = null;
    private final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;
    private final long MAX_CACHE_TIME = ONE_DAY_TIME * 5; //5일이 지나면 캐시에서 삭제

    public List<String> getNikeNewRelease(
            @RequestBody Map<String,String> inputData
    ) {

        List<String> result = new ArrayList<>();
        String url = "https://www.nike.com/kr/launch?s=in-stock";
        String preUri = "https://www.nike.com";

        try {

            logger.debug("userAgent => "+ UserAgentEnum.IPHONE.value());
            Document doc = Jsoup.connect(url)
                    .userAgent(UserAgentEnum.IPHONE.value())
                    .maxBodySize(3000000)
                    .timeout(300000)
                    .get();

            Elements elems = doc.select("a.card-link");

            //초기화 로직
            if(NIKE_MODELS == null){

                NIKE_MODELS = new HashMap<>();
                if(elems != null){
                    for(Element elem : elems) {
                        NIKE_MODELS.put(preUri + elem.attr("href"), System.currentTimeMillis());
                    }
                }

            }else{

                //캐시 클리어 체크
                clearNikeModelCacheValue();

                if(elems != null) {

                    for (Element elem : elems) {

                        //주소를 key 값으로 기존에 존재하는지 체크
                        String href = preUri + elem.attr("href");
                        if(NIKE_MODELS.containsKey(href)){ //이미 처리한건 pass
                            continue;
                        }

                        NIKE_MODELS.put(href, System.currentTimeMillis());
                        result.add(href);

                    }

                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;

    }

    //nike cache 시간 지난것 제거
    private void clearNikeModelCacheValue(){

        if(NIKE_MODELS != null){

            long now = System.currentTimeMillis();
            List<String> keysForDelete = new ArrayList<>();
            for(Map.Entry<String, Long> entry : NIKE_MODELS.entrySet()){
                if(now - entry.getValue() > MAX_CACHE_TIME){
                    keysForDelete.add(entry.getKey());
                }
            }

            //cache 시간 지난것 제거
            for(String key : keysForDelete){
                NIKE_MODELS.remove(key);
            }

        }

    }

}
