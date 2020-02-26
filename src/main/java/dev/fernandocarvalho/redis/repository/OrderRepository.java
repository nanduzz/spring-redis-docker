package dev.fernandocarvalho.redis.repository;


import dev.fernandocarvalho.redis.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Collection<Order> findByWhen(Date date);
}

