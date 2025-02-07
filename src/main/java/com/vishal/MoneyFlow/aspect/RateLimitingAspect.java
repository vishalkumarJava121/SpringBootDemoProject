package com.vishal.MoneyFlow.aspect;

import com.vishal.MoneyFlow.exception.TooManyRequestsException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitingAspect {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Around("@annotation(RateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        String clientIp = RequestContextHolder.currentRequestAttributes()
                .getAttribute("client-ip", RequestAttributes.SCOPE_REQUEST).toString();

        Bucket bucket = cache.computeIfAbsent(clientIp, this::createNewBucket);

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();
        } else {
            throw new TooManyRequestsException("Rate limit exceeded, try again later");
        }
    }

    private Bucket createNewBucket(String clientIp) {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1))))
                .build();
    }
}

