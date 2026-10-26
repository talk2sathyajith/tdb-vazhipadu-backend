package com.temple.tdb_vazhipadu_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "temple_orders")
public class TempleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offering_id", nullable = false)
    private Offering offering;

    @Column(name = "devotee_name", nullable = false)
    private String devoteeName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "total_amount", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String status = "BOOKED";

    protected TempleOrder() {
    }

    public TempleOrder(
            Offering offering,
            String devoteeName,
            String phoneNumber,
            LocalDate bookingDate,
            int quantity,
            BigDecimal totalAmount) {

        this.offering = offering;
        this.devoteeName = devoteeName;
        this.phoneNumber = phoneNumber;
        this.bookingDate = bookingDate;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public TempleOrder(@NotNull @Positive Long templeId, String trim, String trim2, String trim3,
			@NotNull LocalDate bookingDate2) {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
        return id;
    }

    public Offering getOffering() {
        return offering;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

	public void setTotalAmount(BigDecimal total) {
		// TODO Auto-generated method stub
		
	}

	public void addItem(OrderItem item) {
		// TODO Auto-generated method stub
		
	}
}