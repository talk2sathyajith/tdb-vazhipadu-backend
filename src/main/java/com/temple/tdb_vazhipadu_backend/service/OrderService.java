  package com.temple.tdb_vazhipadu_backend.service;


	
	

	import java.math.BigDecimal;
	import java.util.List;

	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;

	import com.temple.tdb_vazhipadu_backend.dto.CreateOrderRequest;
	import com.temple.tdb_vazhipadu_backend.dto.OrderResponse;
	import com.temple.tdb_vazhipadu_backend.dto.OrderResponse.OrderItemResponse;
	import com.temple.tdb_vazhipadu_backend.entity.Offering;
	import com.temple.tdb_vazhipadu_backend.entity.OrderItem;
	import com.temple.tdb_vazhipadu_backend.entity.TempleOrder;
	import com.temple.tdb_vazhipadu_backend.exception.BusinessRuleException;
	import com.temple.tdb_vazhipadu_backend.exception.ResourceNotFoundException;
	import com.temple.tdb_vazhipadu_backend.repository.OfferingRepository;
	import com.temple.tdb_vazhipadu_backend.repository.OrderRepository;

	@Service
	public class OrderService {

	    private final OfferingRepository offeringRepository;
	    private final OrderRepository orderRepository;

	    public OrderService(
	            OfferingRepository offeringRepository,
	            OrderRepository orderRepository) {
	        this.offeringRepository = offeringRepository;
	        this.orderRepository = orderRepository;
	    }

	    @Transactional
	    public OrderResponse create(CreateOrderRequest request) {
	        TempleOrder order = new TempleOrder(
	            request.templeId(),
	            request.devoteeName().trim(),
	            request.phone().trim(),
	            request.nakshatra().trim(),
	            request.bookingDate()
	        );

	        BigDecimal total = BigDecimal.ZERO;

	        for (CreateOrderRequest.OrderItemRequest itemRequest
	                : request.items()) {

	            Offering offering = offeringRepository
	                .findById(itemRequest.offeringId())
	                .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                        "Offering not found: "
	                            + itemRequest.offeringId()
	                    ));

	            if (!offering.isActive()) {
	                throw new BusinessRuleException(
	                    "Offering is inactive: " + offering.getName()
	                );
	            }

	            BigDecimal unitPrice = offering.getPrice();

	            BigDecimal lineTotal = unitPrice.multiply(
	                BigDecimal.valueOf(itemRequest.quantity())
	            );

	            OrderItem item = new OrderItem(
	                offering,
	                itemRequest.quantity(),
	                unitPrice,
	                lineTotal
	            );

	            order.addItem(item);
	            total = total.add(lineTotal);
	        }

	        order.setTotalAmount(total);

	        TempleOrder saved = orderRepository.save(order);

	        return toResponse(saved);
	    }

	    @Transactional(readOnly = true)
	    public OrderResponse findById(Long id) {
	        TempleOrder order = orderRepository.findById(id)
	            .orElseThrow(() ->
	                new ResourceNotFoundException(
	                    "Order not found: " + id
	                ));

	        return toResponse(order);
	    }

	    private OrderResponse toResponse(TempleOrder order) {
	        List<OrderItemResponse> items = order.getItems()
	            .stream()
	            .map(item -> new OrderItemResponse(
	                item.getOffering().getId(),
	                item.getOffering().getName(),
	                item.getQuantity(),
	                item.getUnitPrice(),
	                item.getLineTotal()
	            ))
	            .toList();

	        return new OrderResponse(
	            order.getId(),
	            order.getTempleId(),
	            order.getDevoteeName(),
	            order.getPhone(),
	            order.getNakshatra(),
	            order.getBookingDate(),
	            order.getStatus(),
	            order.getTotalAmount(),
	            order.getCreatedAt(),
	            items
	        );
	    }
	}

