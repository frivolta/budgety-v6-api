package com.budgety.api.service.impl;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.CategoryDto;
import com.budgety.api.repository.CategoryRepository;
import com.budgety.api.repository.UserRepository;
import com.budgety.api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDto getCategoryByName(String name, Long userId) {
        Category category = categoryRepository.findByNameAndUserId(name, userId).orElseThrow(()->new ResourceNotFoundException("category", "name", name));
        return mapToDto(category);
    }

    @Override
    public Category getCategoryEntityByName(String name, Long userid) {
        return categoryRepository.findByNameAndUserId(name, userid).orElseThrow(()->new ResourceNotFoundException("category", "name", name));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId.toString()));
        Category category = mapToEntity(categoryDto);
        category.setUser(user);
        categoryRepository.save(category);
        return mapToDto(category);
    }



    private CategoryDto mapToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category mapToEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }
}
