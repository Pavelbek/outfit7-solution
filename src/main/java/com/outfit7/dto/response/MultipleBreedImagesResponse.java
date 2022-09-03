package com.outfit7.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MultipleBreedImagesResponse {
    List<String> message;
    String status;
}
