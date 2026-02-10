package com.keshav.smartpay.services.implementations;

import com.keshav.smartpay.services.BucketService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {
    private final ProxyManager<String> proxyManager;
    private final Supplier<BucketConfiguration> bucketConfigurationSupplier;

    @Override
    public String extractClientKey(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    @Override
    public Bucket resolveBucket(HttpServletRequest request) {
        String clientKey = String.format("bucket4j:%s",extractClientKey(request));
        return proxyManager.builder().build(clientKey, bucketConfigurationSupplier);
    }
}
