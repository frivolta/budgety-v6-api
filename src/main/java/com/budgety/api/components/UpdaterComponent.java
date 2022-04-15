package com.budgety.api.components;

import com.budgety.api.entity.CategoryType;
import com.budgety.api.entity.EnrichedCategory;
import com.budgety.api.entity.MonthlyBudget;
import com.budgety.api.entity.Transaction;
import com.budgety.api.repositories.EnrichedCategoryRepository;
import com.budgety.api.repositories.MonthlyBudgetRepository;
import com.budgety.api.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class UpdaterComponent {

    private EnrichedCategoryRepository enrichedCategoryRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public UpdaterComponent(EnrichedCategoryRepository enrichedCategoryRepository,TransactionRepository transactionRepository) {
        this.enrichedCategoryRepository = enrichedCategoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public void updateTransactionEnrichedCategory(Transaction transaction){
        Transaction transactionFromRepo = transactionRepository.getById(transaction.getId());
        EnrichedCategory enrichedCategory = enrichedCategoryRepository.getById(transactionFromRepo.getEnrichedCategory().getId());

        BigDecimal newAmount = new BigDecimal(0);
        for (Transaction t: enrichedCategory.getTransactions()){
            if(t.getTransactionType()==CategoryType.EXPENSE){
                newAmount = newAmount.add(t.getAmount());
            }
            if(t.getTransactionType()==CategoryType.INCOME){
                newAmount = newAmount.subtract(t.getAmount());
            }
        }
        enrichedCategory.setCurrentAmount(newAmount);
        enrichedCategoryRepository.save(enrichedCategory);
    }
}
