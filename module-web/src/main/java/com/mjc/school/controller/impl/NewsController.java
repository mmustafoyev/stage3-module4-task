package com.mjc.school.controller.impl;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    @CommandHandler(operation = 5)
    @GetMapping("/news")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> getNewsList() {
        return newsService.readAll();
    }
    @CommandHandler(operation = 9)
    @GetMapping("/news/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse getNewsById(@PathVariable Long id) {
        return newsService.readById(id);
    }
    @CommandHandler(operation = 24)
    @GetMapping("/news1")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> getNewsByParams(
            @RequestParam(required = false) NewsQueryParams newsQueryParams
    ) {
        return newsService.readByQueryParams(newsQueryParams);
    }
    @CommandHandler(operation = 1)
    @PostMapping("/news")
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDtoResponse createNews(@RequestBody NewsDtoRequest newsDtoRequest) {
        return newsService.create(newsDtoRequest);
    }
    @CommandHandler(operation = 13)
    @PutMapping("/news")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse updateNews(@RequestBody NewsDtoRequest newsDtoRequest) {
        return newsService.update(newsDtoRequest);
    }
    @CommandHandler(operation = 17)
    @DeleteMapping("/news/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
    }
}