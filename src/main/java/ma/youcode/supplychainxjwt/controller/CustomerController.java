package ma.youcode.supplychainxjwt.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.supplychainxjwt.dto.CustomerRequest;
import ma.youcode.supplychainxjwt.dto.CustomerResponse;
import ma.youcode.supplychainxjwt.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customer) {
        return customerService.save(customer);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest customer,
                                           @PathVariable Long id) {
        return customerService.update(customer, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }

    @GetMapping
    public List<CustomerResponse> getCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/name/{name}")
    public List<CustomerResponse> getCustomersByName(@PathVariable String name) {
        return customerService.findByName(name);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }
}
