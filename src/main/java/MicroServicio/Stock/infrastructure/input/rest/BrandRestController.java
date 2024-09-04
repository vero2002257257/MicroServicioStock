package MicroServicio.Stock.infrastructure.input.rest;

import MicroServicio.Stock.application.dto.request.BrandRequest;
import MicroServicio.Stock.application.dto.response.BrandResponse;
import MicroServicio.Stock.application.handler.interfaces.IBrandHandler;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandRestController {

    private final IBrandHandler brandHandler;

    @Operation(summary = "Create a new brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/")
    public ResponseEntity<Void> createBrand(@RequestBody BrandRequest brandRequest) {
        brandHandler.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of brands"),
            @ApiResponse(responseCode = "404", description = "No brands found")
    })
    @GetMapping("/")
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        return ResponseEntity.ok(brandHandler.getAllBrands());
    }

    @Operation(summary = "Get a brand by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity<BrandResponse> getBrand(@PathVariable String name) {
        BrandResponse brandResponse = brandHandler.getBrandByName(name);
        return ResponseEntity.ok(brandResponse);
    }

    @Operation(summary = "Update a brand by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{name}")
    public ResponseEntity<Void> updateBrand(@PathVariable String name, @RequestBody BrandRequest brandRequest) {
        brandHandler.updateBrand(brandRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a brand by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String name) {
        brandHandler.deleteBrand(name);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get brands with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list of brands",
                    content = @Content(schema = @Schema(implementation = PageCustom.class))),
            @ApiResponse(responseCode = "404", description = "No brands found",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/paged")
    public ResponseEntity<PageCustom<BrandResponse>> getBrandsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {

        // Divide el sort en campo y orden
        String sortField = sort[0]; // campo para ordenar
        boolean ascending = sort.length > 1 && sort[1].equalsIgnoreCase("asc");

        PageRequestCustom pageRequest = new PageRequestCustom(page, size, ascending, sortField);
        PageCustom<BrandResponse> brandsPage = brandHandler.getBrands(pageRequest);
        return ResponseEntity.ok(brandsPage);
    }

}
