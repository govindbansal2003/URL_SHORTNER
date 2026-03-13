package com.urlshortner.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortenResponse {

    private String shortCode;
    private String shortUrl;
    private String originalUrl;
}
