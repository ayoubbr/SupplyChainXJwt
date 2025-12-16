package ma.youcode.supplychainxjwt.controller;

import ma.youcode.supplychainxjwt.dto.SupplyOrderRequest;
import ma.youcode.supplychainxjwt.dto.SupplyOrderResponse;
import ma.youcode.supplychainxjwt.model.SupplyOrder;
import ma.youcode.supplychainxjwt.service.SupplyOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply-orders")
public class SupplyOrderController {

    private final SupplyOrderService supplyOrderService;

    public SupplyOrderController(SupplyOrderService supplyOrderService) {
        this.supplyOrderService = supplyOrderService;
    }

    @PostMapping
    public SupplyOrderResponse create(@RequestBody SupplyOrderRequest supplyOrderRequest) {
        SupplyOrder savedOrder = supplyOrderService.save(supplyOrderRequest);
        return supplyOrderService.toResponse(savedOrder);
    }

    @GetMapping
    public List<SupplyOrderResponse> getAll() {
        List<SupplyOrder> orders = supplyOrderService.findAll();
        return supplyOrderService.toResponseList(orders);
    }

    @GetMapping("/{id}")
    public SupplyOrderResponse getById(@PathVariable Long id) {
        SupplyOrder order = supplyOrderService.findById(id);
        return supplyOrderService.toResponse(order);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<SupplyOrderResponse> getBySupplierId(@PathVariable Long supplierId) {
        List<SupplyOrder> orders = supplyOrderService.findBySupplierId(supplierId);
        return supplyOrderService.toResponseList(orders);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id) {
        return supplyOrderService.deleteById(id);
    }

    @PutMapping("/{id}")
    public SupplyOrderResponse update(@PathVariable Long id, @RequestBody SupplyOrderRequest supplyOrderRequest) {
        SupplyOrder updatedOrder = supplyOrderService.update(supplyOrderRequest, id);
        return supplyOrderService.toResponse(updatedOrder);
    }

}