package com.budgety.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"sub"})
})
public class User extends DefaultEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sub;
    private String email;
    private boolean confirmed=false;
    private BigDecimal accountBalance;
    @Length(max = 3, message = "Provide a valid currency")
    private String currency;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> expens;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Category> categories;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MonthlyBudget> monthlyBudgets;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EnrichedCategory> enrichedCategories;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="profile_id", referencedColumnName = "id")
    private Profile profile;

}
