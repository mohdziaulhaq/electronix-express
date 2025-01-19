package com.zia.electronix.express.controller;

import com.zia.electronix.express.dtos.ApiResponseMessage;
import com.zia.electronix.express.dtos.CategoryDto;
import com.zia.electronix.express.dtos.ImageResponse;
import com.zia.electronix.express.dtos.PageableResponse;
import com.zia.electronix.express.services.CategoryService;
import com.zia.electronix.express.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Value("${category.image-path}")
    public String categoryImagePath;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;
    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
         CategoryDto dto = categoryService.create(categoryDto);
         return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto) {
       CategoryDto updateCategory = categoryService.update(categoryDto,categoryId);
       return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Category is deleted successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

            ) {
        PageableResponse<CategoryDto> pageableResponse =categoryService.getAll(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    // get single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {
        CategoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    //search
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keywords) {
        return ResponseEntity.ok(categoryService.search(keywords));
    }

    //upload cover image
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@PathVariable String categoryId, @RequestParam("coverImage") MultipartFile file) throws IOException {
        String imageName = fileService.uploadFile(file,categoryImagePath);
        CategoryDto categoryDto = categoryService.get(categoryId);
        categoryDto.setCoverImage(imageName);
        categoryService.update(categoryDto,categoryId);
        ImageResponse imageResponse = ImageResponse.builder().message("Image uploaded successfully").success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    //serve cover image
    @GetMapping("/image/{categoryId}")
    public void getCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = categoryService.get(categoryId);
        InputStream resource = fileService.getResource(categoryImagePath,categoryDto.getCoverImage());
        response.setContentType("image/jpeg");
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
