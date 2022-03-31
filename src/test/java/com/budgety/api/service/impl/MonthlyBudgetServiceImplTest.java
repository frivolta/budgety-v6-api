package com.budgety.api.service.impl;

import com.budgety.api.entity.MonthlyBudget;
import com.budgety.api.payload.monthlyBudget.MonthlyBudgetDto;
import com.budgety.api.repository.MonthlyBudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MonthlyBudgetServiceImplTest {
    @Spy
    @InjectMocks
    @Autowired
    private MonthlyBudgetServiceImpl service;

    @Mock
    private MonthlyBudgetRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private String mockStartDate = "01-03-2022";
    private String mockEndDate = "31-03-2022";

    private MonthlyBudget entity = new MonthlyBudget();
    private MonthlyBudgetDto dto = new MonthlyBudgetDto();

    @BeforeEach
    public void setUp() throws ParseException {
        dto.setId(1L);
        entity.setId(1L);
        dto.setStartDate(formatter.parse(mockStartDate));
        entity.setStartDate(formatter.parse(mockStartDate));
        dto.setEndDate(formatter.parse(mockEndDate));
        entity.setEndDate(formatter.parse(mockEndDate));
    }


    @Test
    public void mapToEntity_shouldCorrect() throws ParseException {
        when(modelMapper.map(dto, MonthlyBudget.class)).thenReturn(entity);
        MonthlyBudget newEntity = service.mapToEntity(dto);
        assertThat(newEntity.getStartDate().compareTo(dto.getStartDate()));
        assertThat(newEntity.getEndDate().compareTo(dto.getEndDate()));
    }

    @Test
    public void mapToDto_shouldCorrect() throws ParseException {
        when(modelMapper.map(entity, MonthlyBudgetDto.class)).thenReturn(dto);
        MonthlyBudgetDto newDto = service.mapToDto(entity);
        assertThat(newDto.getStartDate().compareTo(entity.getStartDate()));
        assertThat(newDto.getEndDate().compareTo(entity.getEndDate()));
    }

    @Test
    public void createMonthlyBudget_shouldThrowIfBudgetAlreadyExists() {
        when(modelMapper.map(dto, MonthlyBudget.class)).thenReturn(entity);
        when(repository.existsByDate(any(), any(), any())).thenReturn(true);
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> service.createMonthlyBudget(1L, dto),
                "A budget already exists for this month");
        assertTrue(thrown.getClass().equals(IllegalArgumentException.class));
    }


}