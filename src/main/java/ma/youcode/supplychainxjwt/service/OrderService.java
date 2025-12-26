package ma.youcode.supplychainxjwt.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.youcode.supplychainxjwt.dto.OrderRequest;
import ma.youcode.supplychainxjwt.dto.OrderResponse;
import ma.youcode.supplychainxjwt.mapper.OrderMapper;
import ma.youcode.supplychainxjwt.model.Customer;
import ma.youcode.supplychainxjwt.model.Order;
import ma.youcode.supplychainxjwt.model.Product;
import ma.youcode.supplychainxjwt.repository.CustomerRepository;
import ma.youcode.supplychainxjwt.repository.OrderRepository;
import ma.youcode.supplychainxjwt.repository.ProductRepository;
import ma.youcode.supplychainxjwt.shared.enums.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderResponse save(OrderRequest orderRequest) {
        log.info(
                "Order creation requested customerId={} productId={} quantity={}",
                orderRequest.getCustomerId(),
                orderRequest.getProductId(),
                orderRequest.getQuantity()
        );

        validateOrder(orderRequest);

        Order order = new Order();
        order.setStatus(OrderStatus.EN_PREPARATION);
        order.setQuantity(orderRequest.getQuantity());


        Optional<Customer> customerOpt =
                customerRepository.findById(orderRequest.getCustomerId());

        if (customerOpt.isEmpty()) {
            log.error(
                    "Order creation failed: customer not found customerId={}",
                    orderRequest.getCustomerId()
            );
            throw new EntityNotFoundException("Customer not found");
        }

        Customer customer = customerOpt.get();

        order.setCustomer(customer);

        Optional<Product> productOpt =
                productRepository.findById(orderRequest.getProductId());

        if (productOpt.isEmpty()) {
            log.error(
                    "Order creation failed: product not found productId={}",
                    orderRequest.getProductId()
            );
            throw new EntityNotFoundException("Product not found");
        }

        Product product = productOpt.get();

        if (product.getStock() < orderRequest.getQuantity()) {
            log.error(
                    "Insufficient stock productId={} available={} requested={}",
                    product.getId(),
                    product.getStock(),
                    orderRequest.getQuantity()
            );
            throw new IllegalStateException("Product doesn't have enough stock: " +
                    product.getStock() + " - Quantity ordered: " + orderRequest.getQuantity());
        }

        long oldQty = product.getStock();
        long newQty = product.getStock() - orderRequest.getQuantity();

        log.info(
                "Stock updated productId={} oldQty={} newQty={}",
                product.getId(),
                oldQty,
                newQty
        );

        product.setStock(product.getStock() - orderRequest.getQuantity());
        productRepository.save(product);
        order.setProduct(product);

        Order saved = orderRepository.save(order);
        log.info(
                "Order created successfully orderId={} customerId={} productId={} quantity={}",
                saved.getId(),
                customer.getId(),
                product.getId(),
                saved.getQuantity()
        );
        return orderMapper.mapOrderToResponse(saved);
    }

    public OrderResponse update(Long id, OrderRequest orderRequest) {
        log.info(
                "Order update requested orderId={} newStatus={} newProductId={} newQuantity={}",
                id,
                orderRequest.getStatus(),
                orderRequest.getProductId(),
                orderRequest.getQuantity()
        );

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }

        Optional<Order> orderOpt = orderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            log.error(
                    "Order update failed: order not found orderId={}",
                    id
            );
            throw new EntityNotFoundException("Order not found with id: " + id);
        }

        Order existingOrder = orderOpt.get();

        if (existingOrder.getStatus() == OrderStatus.LIVREE) {
            log.warn(
                    "Order update denied: already delivered orderId={}",
                    id
            );
            throw new IllegalStateException("Cannot update an order that is already delivered");
        }


        if (orderRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        validateOrder(orderRequest);

        OrderStatus newStatus = OrderStatus.valueOf(orderRequest.getStatus());
        existingOrder.setStatus(newStatus);


        // Update customer if changed
        if (!existingOrder.getCustomer().getId().equals(orderRequest.getCustomerId())) {
            Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            existingOrder.setCustomer(customer);
        }

        Product oldProduct = existingOrder.getProduct();
        Product newProduct = oldProduct;

        Long newProductId = orderRequest.getProductId();

        // If product changed
        if (!oldProduct.getId().equals(newProductId)) {
            log.info(
                    "Order product changed orderId={} oldProductId={} newProductId={}",
                    id,
                    oldProduct.getId(),
                    newProductId
            );

            newProduct = productRepository.findById(newProductId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));


            // Return old product stock
            oldProduct.setStock(oldProduct.getStock() + existingOrder.getQuantity());
            productRepository.save(oldProduct);

            if (newProduct.getStock() < orderRequest.getQuantity()) {
                log.error(
                        "Stock insufficient for order update orderId={} productId={} available={} requested={}",
                        id,
                        newProduct.getId(),
                        newProduct.getStock(),
                        orderRequest.getQuantity()
                );

                throw new IllegalStateException("Insufficient stock for new product: " + newProduct.getName() +
                        " - stock: " + newProduct.getStock() + " - Quantity ordered: " + orderRequest.getQuantity());
            }

            // Deduct new product stock
            newProduct.setStock(newProduct.getStock() - orderRequest.getQuantity());
            productRepository.save(newProduct);

            existingOrder.setProduct(newProduct);
        } else {
            // If same product but quantity changed
            int quantityDiff = orderRequest.getQuantity() - existingOrder.getQuantity();
            if (quantityDiff != 0) {
                if (quantityDiff > 0 && newProduct.getStock() < quantityDiff) {
                    throw new IllegalStateException("Not enough stock for product: " + newProduct.getName());
                }
                newProduct.setStock(newProduct.getStock() - quantityDiff);
                productRepository.save(newProduct);
            }
        }

        existingOrder.setQuantity(orderRequest.getQuantity());

        Order saved = orderRepository.save(existingOrder);

        log.info(
                "Order updated successfully orderId={} status={} quantity={}",
                existingOrder.getId(),
                existingOrder.getStatus(),
                existingOrder.getQuantity()
        );

        return orderMapper.mapOrderToResponse(saved);
    }

    public OrderResponse cancel(Long id) {
        log.info(
                "Order cancellation requested orderId={}",
                id
        );

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.LIVREE) {
            log.warn(
                    "Order cancellation denied: already delivered orderId={}",
                    id
            );

            throw new IllegalStateException("Cannot cancel an order that is already delivered");
        }

        order.setStatus(OrderStatus.ANNULEE);

        order.getProduct().setStock(order.getProduct().getStock() + order.getQuantity());
        productRepository.save(order.getProduct());

        Order cancelled = orderRepository.save(order);

        log.info(
                "Order cancelled successfully orderId={} restoredStock={} productId={}",
                cancelled.getId(),
                cancelled.getQuantity(),
                cancelled.getProduct().getId()
        );

        return orderMapper.mapOrderToResponse(cancelled);
    }

    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::mapOrderToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getById(Long id) {
        log.debug("Order fetched orderId={}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        return orderMapper.mapOrderToResponse(order);
    }

    private void validateOrder(OrderRequest orderRequest) {
        if (orderRequest.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (orderRequest.getCustomerId() == null || orderRequest.getCustomerId() == 0) {
            throw new IllegalArgumentException("Customer id must be set");
        }
        if (orderRequest.getProductId() == null || orderRequest.getProductId() == 0) {
            throw new IllegalArgumentException("Product id must be set");
        }

        try {
            if (orderRequest.getStatus() != null) {
                OrderStatus.valueOf(orderRequest.getStatus());
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + orderRequest.getStatus());
        }
    }
}
