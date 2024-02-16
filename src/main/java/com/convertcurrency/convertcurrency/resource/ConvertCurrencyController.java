package com.convertcurrency.convertcurrency.resource;

import com.convertcurrency.convertcurrency.model.ConvertedCurrency;
import com.convertcurrency.convertcurrency.proxy.CurrencyConversionProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class ConvertCurrencyController {

    @Autowired private CurrencyConversionProxy currencyConversionProxy;

    @GetMapping("/convert-currency/from/{fromCurrency}/to/{toCurrency}/quantity/{quantity}")
    public ConvertedCurrency getConvertedCurrency(@PathVariable String fromCurrency, @PathVariable String toCurrency, @PathVariable BigDecimal quantity) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", fromCurrency);
        uriVariables.put("to", toCurrency);
        ResponseEntity<ConvertedCurrency> forEntity =
                new RestTemplate()
                        .getForEntity("http://localhost:8000/currency-conversion/from/USD/to/INR",
                                ConvertedCurrency.class,
                                uriVariables);
        ConvertedCurrency convertedCurrency = forEntity.getBody();
        return new ConvertedCurrency(convertedCurrency.getId(),
                fromCurrency, toCurrency, quantity,
                convertedCurrency.getConversionMultiple(),
                quantity.multiply(convertedCurrency.getConversionMultiple()),
                convertedCurrency.getEnvironment());
    }

    @GetMapping("/convert-currency-feign/from/{fromCurrency}/to/{toCurrency}/quantity/{quantity}")
    public ConvertedCurrency getConvertedCurrencyFeign
            (@PathVariable String fromCurrency, @PathVariable String toCurrency, @PathVariable BigDecimal quantity) {
        ConvertedCurrency convertedCurrency = currencyConversionProxy.retrieveExchangeValue(fromCurrency, toCurrency);
        return new ConvertedCurrency(convertedCurrency.getId(),
                fromCurrency, toCurrency, quantity,
                convertedCurrency.getConversionMultiple(),
                quantity.multiply(convertedCurrency.getConversionMultiple()),
                convertedCurrency.getEnvironment() + " " + "feign");
    }

}
