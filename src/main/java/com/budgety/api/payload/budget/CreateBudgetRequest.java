package com.budgety.api.payload.budget;

import lombok.Data;

import java.util.Set;

@Data
public class CreateBudgetRequest{
    private BudgetDto budget;
    private Set<Long> categoryIds;
}
