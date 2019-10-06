package com.myretail.redsky;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
 public class ProductDescription {
    private String title;
}
