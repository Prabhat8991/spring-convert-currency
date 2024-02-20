package com.convertcurrency.convertcurrency.proxy;

import com.convertcurrency.convertcurrency.model.ConvertedCurrency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-conversion", url = "localhost:8000")
@FeignClient(name = "currency-conversion")
// on removing url this will talk to eureka and get instance and load balance between them
public interface CurrencyConversionProxy {

    @GetMapping("/currency-conversion/from/{from}/to/{to}")
    public ConvertedCurrency retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
