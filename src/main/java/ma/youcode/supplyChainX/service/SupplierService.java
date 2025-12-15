package ma.youcode.supplyChainX.service;

import ma.youcode.supplyChainX.model.Supplier;
import ma.youcode.supplyChainX.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier save(Supplier supplier) {
        if (supplierRepository.existsByName(supplier.getName())) {
            throw new IllegalArgumentException("Supplier with name " + supplier.getName() + " already exists.");
        }
        if (supplierRepository.existsByContact(supplier.getContact())) {
            throw new IllegalArgumentException("Supplier with contact " + supplier.getContact() + " already exists.");
        }

        return supplierRepository.save(supplier);
    }

    public Supplier update(Supplier supplier, Long id) {

        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));

        if (supplierRepository.existsByName(supplier.getName())) {
            Supplier supplierByName = supplierRepository.findByName(supplier.getName());
            if (!Objects.equals(supplierByName.getId(), existingSupplier.getId())) {
                throw new IllegalArgumentException("Supplier with name " + supplier.getName() + " already exists.");
            }
        }

        existingSupplier.setName(supplier.getName());
        existingSupplier.setContact(supplier.getContact());
        existingSupplier.setRating(supplier.getRating());
        existingSupplier.setLeadTime(supplier.getLeadTime());

        return supplierRepository.save(existingSupplier);
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier deleteById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));

        if (supplier.getSupplyOrders() != null && !supplier.getSupplyOrders().isEmpty()) {
            throw new IllegalStateException("Cannot delete supplier with existing supply orders.");
        }

        supplierRepository.deleteById(id);
        return supplier;
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));
    }

    public Supplier findByName(String name) {
        if (!supplierRepository.existsByName(name)) {
            throw new IllegalArgumentException("Supplier with name " + name + " does not exist.");
        }
        return supplierRepository.findByName(name);
    }
}
