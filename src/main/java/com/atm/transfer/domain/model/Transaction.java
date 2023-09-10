package com.atm.transfer.domain.model;

import com.atm.transfer.domain.enumerated.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne()
    @JoinColumn(name = "ref_user_id")
    private User refUserId;

    @Enumerated(EnumType.STRING)
    private Type type;

    private BigDecimal amount;

    private BigDecimal total;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                "\n\t, userId=" + userId +
                "\n\t, refUserId=" + refUserId +
                "\n\t, type=" + type +
                "\n\t, amount=" + amount +
                "\n\t, total=" + total +
                "}\n";
    }

    public Transaction(User user, User userRef, Type type, BigDecimal value, BigDecimal total) {
        this.userId = user;
        this.refUserId = userRef;
        this.type = type;
        this.amount = value;
        this.total = total;
    }

    public BigDecimal calculatePayerTotal(BigDecimal value) {
        return this.total.subtract(value);
    }

    public BigDecimal calculatePayeeTotal(BigDecimal value) {
        return this.total.add(value);
    }
}
