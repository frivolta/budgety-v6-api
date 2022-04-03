package com.budgety.api.controller;

import com.budgety.api.payload.enrichedCategory.DeleteEnrichedCategoryRequest;
import com.budgety.api.payload.enrichedCategory.EnrichedCategoryDto;
import com.budgety.api.service.EnrichedCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/monthly-budget/{budgetId}/enriched-category")
public class EnrichedCategoryController {
    private EnrichedCategoryService enrichedCategoryService;


    public EnrichedCategoryController(EnrichedCategoryService enrichedCategoryService) {
        this.enrichedCategoryService = enrichedCategoryService;
    }

    @GetMapping("/{enrichedCategoryId}")
    public ResponseEntity<EnrichedCategoryDto> getEnrichedCategory(
            @PathVariable Long userId,
            @PathVariable Long budgetId,
            @PathVariable Long enrichedCategoryId
    ) {
        EnrichedCategoryDto enrichedCategoryDto = enrichedCategoryService.getEnrichedCategoryById(userId, enrichedCategoryId);
        return new ResponseEntity<>(enrichedCategoryDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<EnrichedCategoryDto> createEnrichedCategory(
            @PathVariable Long userId,
            @PathVariable Long budgetId,
            @Valid @RequestBody EnrichedCategoryDto enrichedCategoryDto
    ) {
        EnrichedCategoryDto newEnrichedCategoryDto = enrichedCategoryService.createEnrichedCategoryInBudget(userId, budgetId, enrichedCategoryDto);
        return new ResponseEntity<>(newEnrichedCategoryDto, HttpStatus.OK);
    }

    @PutMapping("/{enrichedCategoryId}")
    public ResponseEntity<EnrichedCategoryDto> updateEnrichedCategory(
            @PathVariable Long userId,
            @PathVariable Long budgetId,
            @PathVariable Long enrichedCategoryId,
            @Valid @RequestBody EnrichedCategoryDto enrichedCategoryDto
    ) {
        EnrichedCategoryDto updatedEnrichedCategoryDto = enrichedCategoryService.updateEnrichedCategoryInBudget(userId, enrichedCategoryId,enrichedCategoryDto);
        return new ResponseEntity<>(updatedEnrichedCategoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{enrichedCategoryId}")
    public ResponseEntity<DeleteEnrichedCategoryRequest> deleteEnrichedCategory(
            @PathVariable Long userId,
            @PathVariable Long budgetId,
            @PathVariable Long enrichedCategoryId
    ) {
        boolean ok = enrichedCategoryService.deleteEnrichedCategory(userId, enrichedCategoryId);
        DeleteEnrichedCategoryRequest res = new DeleteEnrichedCategoryRequest();
        res.setOk(ok);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
