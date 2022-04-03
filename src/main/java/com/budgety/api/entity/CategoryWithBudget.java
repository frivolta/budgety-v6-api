package com.budgety.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "categories_with_budget", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})})
public class CategoryWithBudget extends EnrichedCategory{
    private BigDecimal budget;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "categoryWithBudget")
    private Set<Transaction> transactions;
}
