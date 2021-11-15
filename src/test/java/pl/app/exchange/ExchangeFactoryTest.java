package pl.app.exchange;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@AllArgsConstructor
public class ExchangeFactoryTest {

    public static NbpUsdV1 valid() {
        LinkedHashMap<String, Object> rate = new LinkedHashMap<>();
        rate.put("ask", 4.0);
        rate.put("bid", 4.0);
        ArrayList<LinkedHashMap<String, Object>> arrayList = new ArrayList<>();
        arrayList.add(rate);
        NbpUsdV1 nbpUsdV1 = new NbpUsdV1();
        nbpUsdV1.setCode("USD");
        nbpUsdV1.setCurrency("test");
        nbpUsdV1.setRates(arrayList);
        return nbpUsdV1;
    }
}
