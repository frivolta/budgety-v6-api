package com.budgety.api.service.impl;

import com.budgety.api.entity.MonthlyBudget;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.monthlyBudget.MonthlyBudgetDto;
import com.budgety.api.repository.MonthlyBudgetRepository;
import com.budgety.api.service.MonthlyBudgetService;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MonthlyBudgetServiceImpl implements MonthlyBudgetService {
    private MonthlyBudgetRepository monthlyBudgetRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public MonthlyBudgetServiceImpl(MonthlyBudgetRepository monthlyBudgetRepository, UserService userService, ModelMapper modelMapper) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public MonthlyBudgetDto createMonthlyBudget(Long userId, MonthlyBudgetDto monthlyBudgetDto) {
        try {
            MonthlyBudget monthlyBudget = mapToEntity(monthlyBudgetDto);
            if (monthlyBudgetRepository.existsByDate(monthlyBudget.getStartDate(), monthlyBudget.getEndDate(), userId)) {
                throw new IllegalArgumentException("A budget already exists for this month");
            }
            User user = userService.getUserEntityById(userId);
            monthlyBudget.setUser(user);
            MonthlyBudget savedMonthlyBudget = monthlyBudgetRepository.save(monthlyBudget);
            return mapToDto(savedMonthlyBudget);
        }catch(ParseException  e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public MonthlyBudgetDto getMonthlyBudget(Long userId, Long budgetId) {
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByIdAnUserId(userId, budgetId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "id", budgetId.toString()));
        return mapToDto(monthlyBudget);
    }

    @Override
    public Set<MonthlyBudgetDto> getMonthlyBudgets(Long userId) {
        List<MonthlyBudget> monthlyBudgets = monthlyBudgetRepository.findAllByUserId(userId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budgets", "userId", userId.toString()));

        return monthlyBudgets.stream().map(monthlyBudget -> mapToDto(monthlyBudget))
                .collect(Collectors.toSet());
    }

    @Override
    public MonthlyBudgetDto getMonthlyBudgetByDate(Long userId, Date date) {
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByDate(date, userId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "date", date.toString()));
        return mapToDto(monthlyBudget);
    }

    @Override
    public boolean deleteMonthlyBudget(Long userId, Long budgetId) {
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByIdAnUserId(userId, budgetId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "id", budgetId.toString()));
        monthlyBudgetRepository.delete(monthlyBudget);
        return true;
    }

    // Field to update???
    @Override
    public MonthlyBudgetDto updateMonthlyBudget(Long userId, Long budgetId, MonthlyBudgetDto monthlyBudgetDto) {
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByIdAnUserId(userId, budgetId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "id", budgetId.toString()));
        return mapToDto(monthlyBudget);
    }

    public MonthlyBudget mapToEntity(MonthlyBudgetDto monthlyBudgetDto) throws ParseException {
       MonthlyBudget entity = modelMapper.map(monthlyBudgetDto, MonthlyBudget.class);
       System.out.println(entity.toString());
       entity.setStartDate(monthlyBudgetDto.getStartDate());
       entity.setEndDate(monthlyBudgetDto.getEndDate());
       return entity;
    }

    public MonthlyBudgetDto mapToDto(MonthlyBudget monthlyBudget) {
        MonthlyBudgetDto dto = modelMapper.map(monthlyBudget, MonthlyBudgetDto.class);
        dto.setStartDate(monthlyBudget.getStartDate());
        dto.setEndDate(monthlyBudget.getEndDate());
        return dto;
    }

}
