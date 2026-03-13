package com.urlshortner.controller;

import com.urlshortner.dto.ShortenRequest;
import com.urlshortner.dto.ShortenResponse;
import com.urlshortner.dto.UrlStatsResponse;
import com.urlshortner.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(
            @Valid @RequestBody ShortenRequest request,
            HttpServletRequest httpRequest) {
        ShortenResponse response = urlShortenerService.shortenUrl(request.getUrl(), httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/urls")
    public ResponseEntity<List<UrlStatsResponse>> getAllUrls(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(urlShortenerService.getAllUrls(httpRequest));
    }

    @GetMapping("/api/urls/{shortCode}/stats")
    public ResponseEntity<UrlStatsResponse> getStats(
            @PathVariable String shortCode,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(urlShortenerService.getStats(shortCode, httpRequest));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
