package com.mjc.school.controller.impl;


import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @CommandHandler(operation = 7)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDtoResponse> getTags() {
        return tagService.readAll();
    }
    @CommandHandler(operation = 11)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse getTagById(@PathVariable Long id) {
        return tagService.readById(id);
    }
    @CommandHandler(operation = 22)
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse getByNewsId(@PathVariable Long newsId) {
        return tagService.readByNewsId(newsId);
    }
    @CommandHandler(operation = 3)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDtoResponse createTag(@RequestBody TagDtoRequest tagDtoRequest) {
        return tagService.create(tagDtoRequest);
    }
    @CommandHandler(operation = 15)
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse updateTag(@RequestBody TagDtoRequest tagDtoRequest) {
        return tagService.update(tagDtoRequest);
    }
    @CommandHandler(operation = 19)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}
