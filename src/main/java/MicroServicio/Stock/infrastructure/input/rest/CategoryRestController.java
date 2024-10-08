package MicroServicio.Stock.infrastructure.input.rest;


import MicroServicio.Stock.application.dto.request.CategoryRequest;
import MicroServicio.Stock.application.dto.response.CategoryResponse;
import MicroServicio.Stock.application.handler.interfaces.ICategoryHandler;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories"),
            @ApiResponse(responseCode = "404", description = "No categories found")
    })
    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryHandler.GetAllCategories());
    }


    @Operation(summary = "Get a category by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String name) {
        CategoryResponse categoryResponse = categoryHandler.getCategoryByName(name);
        return ResponseEntity.ok(categoryResponse);
    }


    @Operation(summary = "Update a category by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{name}")
    public ResponseEntity<Void> updateCategory(@PathVariable String name, @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.updateCategory(categoryRequest);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Delete a category by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        categoryHandler.deleteCategory(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public ResponseEntity<PageCustom<CategoryResponse>> getCategoriesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {

        // Divide el sort en campo y orden
        String sortField = sort[0]; // campo para ordenar
        boolean ascending = sort.length > 1 && sort[1].equalsIgnoreCase("asc");

        PageRequestCustom pageRequest = new PageRequestCustom(page, size, ascending, sortField);
        PageCustom<CategoryResponse> categoriesPage = categoryHandler.getCategories(pageRequest);
        return ResponseEntity.ok(categoriesPage);
    }
}
