package com.budgety.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="budgets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "name"})
})
public class Budget extends DefaultEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private BigDecimal maxAmount;
    private BigDecimal leftAmount;
    private Date fromDate;
    private Date toDate;

    @ManyToMany
    @JoinTable(
            name = "budget_category",
            joinColumns = @JoinColumn(name = "budget_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
    @ManyToMany()
    @JoinTable(
            name = "budget_expense",
            joinColumns = @JoinColumn(name = "budget_id"),
            inverseJoinColumns = @JoinColumn(name="expense_id")
    )
    private Set<Expense> expenses;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
