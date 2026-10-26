package com.temple.tdb_vazhipadu_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "temple_orders")
public class TempleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temple_id", nullable = false)
    private Long templeId;

    @Column(name = "devotee_name", nullable = false, length = 150)
    private String devoteeName;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String nakshatra;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "total_amount", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    protected TempleOrder() {
    }

    public TempleOrder(Long templeId, String devoteeName,
                       String phone, String nakshatra,
                       LocalDate bookingDate) {
        this.templeId = templeId;
        this.devoteeName = devoteeName;
        this.phone = phone;
        this.nakshatra = nakshatra;
        this.bookingDate = bookingDate;
        this.status = "CONFIRMED";
        this.totalAmount = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getId() { return id; }
    public Long getTempleId() { return templeId; }
    public String getDevoteeName() { return devoteeName; }
    public String getPhone() { return phone; }
    public String getNakshatra() { return nakshatra; }
    public LocalDate getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OrderItem> getItems() { return items; }
}