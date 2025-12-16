package ma.youcode.supplychainxjwt.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.supplychainxjwt.dto.DeliveryRequest;
import ma.youcode.supplychainxjwt.dto.DeliveryResponse;
import ma.youcode.supplychainxjwt.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public DeliveryResponse createDelivery(@RequestBody DeliveryRequest request) {
        return deliveryService.createDelivery(request);
    }
}

