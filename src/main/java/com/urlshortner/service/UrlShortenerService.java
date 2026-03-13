package com.urlshortner.service;

import com.urlshortner.dto.ShortenResponse;
import com.urlshortner.dto.UrlStatsResponse;
import com.urlshortner.entity.UrlMapping;
import com.urlshortner.repository.UrlMappingRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlShortenerService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final UrlMappingRepository urlMappingRepository;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    @Transactional
    public ShortenResponse shortenUrl(String originalUrl, HttpServletRequest request) {
        String shortCode = generateUniqueCode();

        UrlMapping urlMapping = UrlMapping.builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .build();

        urlMappingRepository.save(urlMapping);

        String baseUrl = getBaseUrl(request);

        return ShortenResponse.builder()
                .shortCode(shortCode)
                .shortUrl(baseUrl + "/" + shortCode)
                .originalUrl(originalUrl)
                .build();
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found: " + shortCode));

        urlMapping.setClickCount(urlMapping.getClickCount() + 1);
        urlMappingRepository.save(urlMapping);

        return urlMapping.getOriginalUrl();
    }

    public List<UrlStatsResponse> getAllUrls(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        return urlMappingRepository.findAll().stream()
                .map(mapping -> UrlStatsResponse.builder()
                        .shortCode(mapping.getShortCode())
                        .shortUrl(baseUrl + "/" + mapping.getShortCode())
                        .originalUrl(mapping.getOriginalUrl())
                        .createdAt(mapping.getCreatedAt())
                        .clickCount(mapping.getClickCount())
                        .build())
                .collect(Collectors.toList());
    }

    public UrlStatsResponse getStats(String shortCode, HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        UrlMapping mapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found: " + shortCode));

        return UrlStatsResponse.builder()
                .shortCode(mapping.getShortCode())
                .shortUrl(baseUrl + "/" + mapping.getShortCode())
                .originalUrl(mapping.getOriginalUrl())
                .createdAt(mapping.getCreatedAt())
                .clickCount(mapping.getClickCount())
                .build();
    }

    private String generateUniqueCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for (int i = 0; i < CODE_LENGTH; i++) {
                sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }
            code = sb.toString();
        } while (urlMappingRepository.existsByShortCode(code));
        return code;
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        if ((scheme.equals("http") && serverPort == 80) ||
                (scheme.equals("https") && serverPort == 443)) {
            return scheme + "://" + serverName;
        }
        return scheme + "://" + serverName + ":" + serverPort;
    }
}
