package pl.app.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.app.system.AppProfile;

import java.util.LinkedHashMap;

@Service
@AllArgsConstructor
@Profile(AppProfile.DEV)
public class ExchangeUsdServiceImpl implements ExchangeUsdService {

    private static String URL = "http://api.nbp.pl/api/exchangerates/rates/c/usd";
    private RestTemplate restTemplate;


    @Override
    public NbpUsdV1 getUsdRate() {
        var forObject = restTemplate.getForObject(URL, LinkedHashMap.class);
        ObjectMapper mapper = new ObjectMapper();
        NbpUsdV1 nbpUsdV1 = null;
        try {
            nbpUsdV1 = mapper.convertValue(forObject, NbpUsdV1.class);
        } catch (Exception e) {
            //connection
        }
        return nbpUsdV1;
    }
}
