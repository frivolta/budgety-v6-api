package com.budgety.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="monthly_budgets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})
})
public class MonthlyBudget extends DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "monthlyBudget", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EnrichedCategory> enrichedCategories;

    @OneToMany(mappedBy = "monthlyBudget", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
}
