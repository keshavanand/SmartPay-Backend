package com.keshav.smartpay.infrastructure.security.filters;

import com.keshav.smartpay.infrastructure.ratelimit.BucketService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class RegisterRateLimitFilter extends OncePerRequestFilter {

    private final BucketService bucketService;

    @Override
    protected  boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        return !request.getRequestURI().equals("/api/v1/auth/register");
    }
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        Bucket bucket = bucketService.resolveBucket(request);
        if(!bucket.tryConsume(1)){
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("We can’t process your request right now. Please try again later.");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
