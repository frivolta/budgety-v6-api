package com.budgety.api.service.impl;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.category.CategoryDto;
import com.budgety.api.repository.CategoryRepository;
import com.budgety.api.repository.UserRepository;
import com.budgety.api.service.CategoryService;
import com.budgety.api.utils.DefaultCategories;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    public Category getCategoryEntityById(Long catId, Long userid) {
        return categoryRepository.findByIdAndUserId(catId, userid).orElseThrow(()->new ResourceNotFoundException("category", "id", catId.toString()));
    }

    @Override
    public Set<Category> getCategoryEntitiesById(Set<Long> ids, Long userId) {
        List<Category> categories = categoryRepository.findAllByIdsAndUserId(ids, userId);
        return new HashSet<>(categories);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId.toString()));
        Category category = mapToEntity(categoryDto);
        category.setUser(user);
        categoryRepository.save(category);
        return mapToDto(category);
    }

    @Override
    public void generateDefaultForUser(User user) {
        Set<CategoryDto> defaultCategories = new DefaultCategories.Builder().withDefaultExpenses().withDefaultIncomes().build().getDefaultCategories();
        defaultCategories.stream().forEach(categoryDto -> {
            Category category = new Category();
            category.setUser(user);
            category.setName(categoryDto.getName());
            category.setColor(categoryDto.getColor());
            category.setIcon(categoryDto.getIcon());
            category.setType(categoryDto.getType());
            categoryRepository.save(category);
        });
    }


    private CategoryDto mapToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category mapToEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }
}
