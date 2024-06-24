package com.nhnacademy.bookstorefront.delivery.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "deliveryService", url = "http://localhost:8080")
public interface DeliveryServiceClient {


}
