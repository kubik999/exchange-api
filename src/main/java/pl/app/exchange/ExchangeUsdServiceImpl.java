package pl.app.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.app.system.AppProfile;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
@Profile(AppProfile.DEV)
public class ExchangeUsdServiceImpl implements ExchangeUsdService {

    private static final String URL = "http://api.nbp.pl/api/exchangerates/rates/c/usd";
    private RestTemplate restTemplate;


    @Override
    public Optional<NbpUsdV1> getUsdRate() {

        NbpUsdV1 nbpUsdV1 = null;
        try {
            var response = restTemplate.getForObject(URL, LinkedHashMap.class);
            ObjectMapper mapper = new ObjectMapper();
            nbpUsdV1 = mapper.convertValue(response, NbpUsdV1.class);
        } catch (Exception e) {
            //connection
            return Optional.empty();
        }
        return Optional.of(nbpUsdV1);
    }
}
