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
@Profile(AppProfile.TEST)
public class ExchangeUsdServiceIFake implements ExchangeUsdService {
    @Override
    public NbpUsdV1 getUsdRate() {
        return null;
    }
}
