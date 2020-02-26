package dev.fernandocarvalho.redis.repository;

import dev.fernandocarvalho.redis.model.LineItem;
import org.springframework.data.repository.CrudRepository;

public interface LineItemRepository extends CrudRepository<LineItem, Long> {

}