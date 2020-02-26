package dev.fernandocarvalho.redis.runners;

import dev.fernandocarvalho.redis.service.OrderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log
public class CacheRunner implements Runner{

    private final OrderService orderService;

    @Value("${dev.fernandocarvalho.name}")
    private String name;


    @Autowired
    public CacheRunner(OrderService orderService) {
        this.orderService = orderService;
    }

    public void run() {
        Runnable measure = () -> orderService.byId(1l);
        Runnable measure2 = () -> orderService.byId(2l);
        log.info("first: " + measure(measure));
        log.info("two-2: " + measure(measure2));
        log.info("three: " + measure(measure));
        log.info("four: " + measure(() -> orderService.clearCache(1l)));
        log.info("five: " + measure(measure));
        log.info("six-2: " + measure(measure2));
        log.info("se7en: " + measure(measure));
        log.info("eight-2: " + measure(measure2));
    }

    @Override
    public String name() {
        return this.name;
    }

    private long measure(Runnable r) {
        long start = System.currentTimeMillis();
        r.run();
        long stop = System.currentTimeMillis();

        return stop - start;
    }
}
