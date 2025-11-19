// src/main/java/com/editaliza/editaliza/controllers/TagController.java
package com.editaliza.editaliza.controllers;

import com.editaliza.editaliza.models.TagData;
import com.editaliza.editaliza.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // ------------------- CREATE -------------------

    @PostMapping
    public ResponseEntity<TagData> createTag(@RequestBody TagData tag) {
        TagData createdTag = tagService.createTag(tag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    // ------------------- READ -------------------

    @GetMapping
    public ResponseEntity<List<TagData>> getAllTags() {
        List<TagData> tags = tagService.findAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagData> getTagById(@PathVariable Long id) {
        TagData tag = tagService.findTagById(id);
        return ResponseEntity.ok(tag);
    }

    // ------------------- UPDATE -------------------

    @PutMapping("/{id}")
    public ResponseEntity<TagData> updateTag(@PathVariable Long id, @RequestBody TagData tagDetails) {
        TagData updatedTag = tagService.updateTag(id, tagDetails);
        return ResponseEntity.ok(updatedTag);
    }

    // ------------------- DELETE -------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}