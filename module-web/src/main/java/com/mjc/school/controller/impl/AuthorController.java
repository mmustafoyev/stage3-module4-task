package com.mjc.school.controller.impl;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @CommandHandler(operation = 6)
    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDtoResponse> getAuthors() {
        return authorService.readAll();
    }
    @CommandHandler(operation = 10)
    @GetMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse getAuthorById(@PathVariable Long id) {
        return authorService.readById(id);
    }
    @CommandHandler(operation = 21)
    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse getByNewsId(@PathVariable Long newsId) {
        return authorService.readByNewsId(newsId);
    }
    @CommandHandler(operation = 2)
    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDtoResponse createAuthor(@RequestBody AuthorDtoRequest authorDtoRequest) {
        return authorService.create(authorDtoRequest);
    }
    @CommandHandler(operation = 14)
    @PutMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse updateAuthor(@RequestBody AuthorDtoRequest authorDtoRequest) {
        return authorService.update(authorDtoRequest);
    }
    @CommandHandler(operation = 18)
    @DeleteMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteById(id);
    }
}
