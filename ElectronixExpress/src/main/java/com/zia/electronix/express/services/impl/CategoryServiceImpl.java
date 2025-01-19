package com.zia.electronix.express.services.impl;

import com.zia.electronix.express.dtos.CategoryDto;
import com.zia.electronix.express.dtos.PageableResponse;
import com.zia.electronix.express.entities.Category;
import com.zia.electronix.express.exception.ResourceNotFoundException;
import com.zia.electronix.express.repositories.CategoryRepository;
import com.zia.electronix.express.services.CategoryService;
import com.zia.electronix.express.utilities.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        //creating categoryID randomly

        String categoryID = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryID);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = repository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!!"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = repository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!!"));
        repository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> page = repository.findAll(pageable);
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
        return response;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category  category = repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!!"));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> search(String keywords) {
        List<Category> categories = categoryRepository.findByTitleContaining(keywords);
        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

    }


}
