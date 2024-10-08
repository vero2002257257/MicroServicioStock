package MicroServicio.Stock.infrastructure.input.rest;

import MicroServicio.Stock.application.dto.request.ArticleRequest;
import MicroServicio.Stock.application.dto.response.ArticleResponse;
import MicroServicio.Stock.application.handler.interfaces.IArticleHandler;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import MicroServicio.Stock.infrastructure.exception.ArticleNotFoundException;
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
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleRestController {
    private final IArticleHandler articleHandler;

    @Operation(summary = "Create a new article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/")
    public ResponseEntity<Void> createArticle(@RequestBody ArticleRequest articleRequest) {
        articleHandler.createArticle(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of articles",
                    content = @Content(schema = @Schema(implementation = ArticleResponse.class))),
            @ApiResponse(responseCode = "404", description = "No articles found",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        return ResponseEntity.ok(articleHandler.getAllArticles());
    }

    @Operation(summary = "Get an article by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article found successfully",
                    content = @Content(schema = @Schema(implementation = ArticleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long id) {
        return articleHandler.getArticleById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ArticleNotFoundException("Articulo no encontrado"));
    }

    @Operation(summary = "Get paginated list of articles with sorting and filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articles retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No articles found",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/paged")
    public ResponseEntity<PageCustom<ArticleResponse>> getArticlesPaged(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "name") String sortField,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String categoryName) {

        PageRequestCustom pageRequest = new PageRequestCustom(page, size, ascending, sortField);
        PageCustom<ArticleResponse> articles = articleHandler.getArticles(pageRequest, brandName, categoryName);

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}
