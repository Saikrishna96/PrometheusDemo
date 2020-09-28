package com.krish.PrometheusDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("controller")
public class SpringController {
    //  public static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BeerService beerService;

    @GetMapping("order")
    public void orderBeer(@RequestParam("name") String name) {
        System.out.println("ordering");
//        beerService.orderBeer();
        beerService.handleOrders(name);
        System.out.println("Success beer service.");
    }

}