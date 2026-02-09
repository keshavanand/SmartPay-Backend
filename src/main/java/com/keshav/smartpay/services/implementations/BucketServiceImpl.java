package com.keshav.smartpay.services.implementations;

import com.keshav.smartpay.services.BucketService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BucketServiceImpl implements BucketService {
    Map<String,Bucket> buckets = new ConcurrentHashMap<>();
    @Override
    public String extractClientKey(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    @Override
    public Bucket resolveBucket(HttpServletRequest request) {
        String clientKey = extractClientKey(request);
        return buckets.computeIfAbsent(clientKey, k ->
                Bucket.builder()
                        .addLimit(Bandwidth.builder()
                                .capacity(1)
                                .refillIntervally(1,Duration.ofSeconds(30))
                                .build())
                        .build());
    }
}
