package com.myretail.redsky;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
 public class AvailableToPromiseNetwork {
    @JsonProperty("product_id")
    private String productId;
}
