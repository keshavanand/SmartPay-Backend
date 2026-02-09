package com.keshav.smartpay.services;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface BucketService {

    String extractClientKey(HttpServletRequest request);
    Bucket resolveBucket(HttpServletRequest request);
}
