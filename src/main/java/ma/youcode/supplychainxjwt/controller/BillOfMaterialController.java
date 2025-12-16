package ma.youcode.supplychainxjwt.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.supplychainxjwt.dto.BillOfMaterialRequest;
import ma.youcode.supplychainxjwt.dto.BillOfMaterialResponse;
import ma.youcode.supplychainxjwt.service.BillOfMaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billOfMaterials")
@RequiredArgsConstructor
public class BillOfMaterialController {

    private final BillOfMaterialService billOfMaterialService;

    @PostMapping
    public BillOfMaterialResponse save(@RequestBody BillOfMaterialRequest billOfMaterialRequest) {
        return billOfMaterialService.save(billOfMaterialRequest);
    }

    @PutMapping("/{id}")
    public BillOfMaterialResponse update(@RequestBody BillOfMaterialRequest billOfMaterialRequest,
                                         @PathVariable Long id) {
        return billOfMaterialService.update(billOfMaterialRequest, id);
    }

    @GetMapping
    public List<BillOfMaterialResponse> getAll() {
        return billOfMaterialService.getAll();
    }

    @GetMapping("/{id}")
    public BillOfMaterialResponse getById(@PathVariable Long id) {
        return billOfMaterialService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        billOfMaterialService.delete(id);
    }

}
