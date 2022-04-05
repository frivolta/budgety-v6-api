package com.budgety.api.service.impl;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.CategoryType;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.category.CategoryDto;
import com.budgety.api.repositories.CategoryRepository;
import com.budgety.api.repositories.UserRepository;
import com.budgety.api.service.CategoryService;
import com.budgety.api.utils.DefaultCategories;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    public Category getCategoryEntityById(Long catId, Long userid) {
        return categoryRepository.findByIdAndUserId(catId, userid).orElseThrow(()->new ResourceNotFoundException("category", "id", catId.toString()));
    }

    @Override
    public CategoryDto getCategoryById(Long catId, Long userid) {
        return mapToDto(getCategoryEntityById(catId, userid));
    }

    @Override
    public Set<Category> getCategoryEntitiesById(Set<Long> ids, Long userId) {
        List<Category> categories = categoryRepository.findAllByIdsAndUserId(ids, userId);
        return new HashSet<>(categories);
    }

    @Override
    public List<CategoryDto> getCategoryEntitiesByUserId(Long userId) {
        List<Category> categories = categoryRepository.findAllByUserId(userId);
        List<CategoryDto> categoriesDtos = categories.stream().map(c->mapToDto(c)).collect(Collectors.toList());
        return categoriesDtos;
    }

    @Override
    public List<CategoryDto> getCategoriesByType(Long userId, CategoryType categoryType) {
        List<Category> categories = categoryRepository.findAllByCategoryType(userId, categoryType);
        List<CategoryDto> categoryDtos = categories.stream().map(c->mapToDto(c)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto updateCategoryById(Long userId, Long categoryId, CategoryDto categoryDto) {
        Category category = getCategoryEntityById(categoryId, userId);
        category.setColor(categoryDto.getColor());
        category.setName(categoryDto.getName());
        category.setIcon(categoryDto.getIcon());
        category.setSlug(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return mapToDto(updatedCategory);
    }

    @Override
    public boolean deleteCategoryById(Long userId, Long categoryId) {
        Category category = getCategoryEntityById(categoryId, userId);
        categoryRepository.delete(category);
        return true;
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
        Set<CategoryDto> defaultCategories = new DefaultCategories.Builder()
                .withDefaultExpenses()
                .withDefaultIncomes()
                .withDefaultSavings()
                .build().getDefaultCategories();
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
