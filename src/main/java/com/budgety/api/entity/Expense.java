package com.budgety.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "expenses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"}),
})
public class Expense extends DefaultEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String expenseDescription;
    private BigDecimal amount;
    private CategoryType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "expenses")
    private Set<Budget> budgets;
}
