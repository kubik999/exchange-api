package pl.app.exchange;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
@Setter
public class NbpUsdV1 {

    private String table;
    private String currency;
    private String code;
    private ArrayList<LinkedHashMap<String, Object>> rates;
}
