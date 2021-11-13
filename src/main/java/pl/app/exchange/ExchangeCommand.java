package pl.app.exchange;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeCommand {

    private double value;
    private ExchangeNominal nominalFrom;
    private ExchangeNominal nominalTo;
}
