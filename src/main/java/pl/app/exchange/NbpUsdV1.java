package pl.app.exchange;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class NbpUsdV1 {

    private String table;
    private String currency;
    private String code;
    private ArrayList<LinkedHashMap<String, Object>> rates;

    public double getBid() {
        return this.getRates().stream()
                .findFirst()
                .map(obj -> (Double) obj.get("bid"))
                .orElseThrow(() -> new RuntimeException("Brak wartości ceny usdy"));
    }

    public double getAsk() {
        return this.getRates().stream()
                .findFirst()
                .map(obj -> (Double) obj.get("ask"))
                .orElseThrow(() -> new RuntimeException("Brak wartości ceny usdy"));
    }
}
