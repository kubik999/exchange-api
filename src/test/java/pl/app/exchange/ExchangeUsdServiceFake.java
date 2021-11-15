package pl.app.exchange;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.app.system.AppProfile;

import java.util.Optional;

@Service
@AllArgsConstructor
@Profile(AppProfile.TEST)
public class ExchangeUsdServiceFake implements ExchangeUsdService {
    @Override
    public Optional<NbpUsdV1> getUsdRate() {
        return Optional.of(ExchangeFactoryTest.valid());
    }
}
