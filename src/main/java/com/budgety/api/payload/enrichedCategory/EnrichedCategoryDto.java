package com.budgety.api.payload.enrichedCategory;

import com.budgety.api.entity.CategoryType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class EnrichedCategoryDto{
    private Long id;
    private BigDecimal currentAmount = new BigDecimal(0);
    @Enumerated(EnumType.STRING)
    private CategoryType type;
    private BigDecimal budgetOrGoal;
    @NotNull
    private Long categoryId;
}
