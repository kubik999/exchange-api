package pl.app.exchange;

import java.util.Optional;

public interface ExchangeUsdService {
    Optional<NbpUsdV1> getUsdRate();
}
