// src/main/java/com/editaliza/editaliza/controllers/ArtistController.java
package com.editaliza.editaliza.controllers;

import com.editaliza.editaliza.models.Artist;
import com.editaliza.editaliza.services.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // ------------------- CREATE -------------------

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        Artist createdArtist = artistService.createArtist(artist);
        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }

    // ------------------- READ -------------------

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.findAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Artist artist = artistService.findArtistById(id);
        return ResponseEntity.ok(artist);
    }

    // ------------------- UPDATE -------------------

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artistDetails) {
        Artist updatedArtist = artistService.updateArtist(id, artistDetails);
        return ResponseEntity.ok(updatedArtist);
    }

    // ------------------- DELETE -------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
    
    // ------------------- TAGS -------------------
    
    @PostMapping("/{artistId}/tags/{tagId}")
    public ResponseEntity<Artist> addTagToArtist(@PathVariable Long artistId, @PathVariable Long tagId) {
        Artist artist = artistService.addTagToArtist(artistId, tagId);
        return ResponseEntity.ok(artist);
    }

    @DeleteMapping("/{artistId}/tags/{tagId}")
    public ResponseEntity<Artist> removeTagFromArtist(@PathVariable Long artistId, @PathVariable Long tagId) {
        Artist artist = artistService.removeTagFromArtist(artistId, tagId);
        return ResponseEntity.ok(artist);
    }
}