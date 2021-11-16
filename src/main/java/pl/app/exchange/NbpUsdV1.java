package pl.app.exchange;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class NbpUsdV1 {

    private String table;
    private String currency;
    private String code;
    private List<LinkedHashMap<String, Object>> rates;

    public double getBid() {
        return this.getRates().stream()
                .findFirst()
                .map(obj -> (Double) obj.get("bid"))
                .orElseThrow(() -> new RuntimeException("Brak wartości ceny usd"));
    }

    public double getAsk() {
        return this.getRates().stream()
                .findFirst()
                .map(obj -> (Double) obj.get("ask"))
                .orElseThrow(() -> new RuntimeException("Brak wartości ceny usd"));
    }
}
