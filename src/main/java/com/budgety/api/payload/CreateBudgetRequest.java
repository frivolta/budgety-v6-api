package com.budgety.api.payload;

import lombok.Data;

import java.util.Set;

@Data
public class CreateBudgetRequest{
    private BudgetDto budget;
    private Set<Long> categoryIds;
}
