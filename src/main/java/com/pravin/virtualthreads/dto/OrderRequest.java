package com.pravin.virtualthreads.dto;

public record OrderRequest(String customerName,
                           String productName,
                           Integer quantity) {
}
