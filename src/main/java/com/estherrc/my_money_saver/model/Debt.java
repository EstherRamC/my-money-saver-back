package com.estherrc.my_money_saver.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "debts")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "debt_amount")
    private BigDecimal debtAmount;

    @Column(name = "in_debt_with")
    private String inDebtWith;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "debt_payed")
    private boolean debtPayed;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and setters
}
