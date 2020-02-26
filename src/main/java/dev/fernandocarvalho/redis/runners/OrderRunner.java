package dev.fernandocarvalho.redis.runners;

import dev.fernandocarvalho.redis.model.LineItem;
import dev.fernandocarvalho.redis.model.Order;
import dev.fernandocarvalho.redis.repository.LineItemRepository;
import dev.fernandocarvalho.redis.repository.OrderRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.*;

@Log
@Component
public class OrderRunner implements Runner {

    private LineItemRepository lineItemRepository;
    private OrderRepository orderRepository;

    public OrderRunner(LineItemRepository lineItemRepository, OrderRepository orderRepository) {
        this.lineItemRepository = lineItemRepository;
        this.orderRepository = orderRepository;
    }

    public void run() {
        Long orderId = generateId();
        List<LineItem> itemList = Arrays.asList(
                new LineItem(orderId, generateId(), "plunger"),
                new LineItem(orderId, generateId(), "soup"),
                new LineItem(orderId, generateId(), "coffemug"));

        itemList.stream()
                .map(lineItemRepository::save)
                .forEach(li -> log.info(li.toString()));
        Order order = new Order(orderId, new Date(), itemList);

        orderRepository.save(order);

        Collection<Order> found = orderRepository.findByWhen(order.getWhen());
        found.forEach(o -> log.info("found:" + o.toString()));
    }

    @Override
    public String name() {
        return "repositories";
    }


    private Long generateId() {
        long tmp = new Random().nextLong();
        return Math.max(tmp, tmp * -1);
    }

}
