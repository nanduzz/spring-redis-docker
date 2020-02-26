package dev.fernandocarvalho.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("lineItems")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItem implements Serializable {

    @Indexed
    private Long orderId;

    @Id
    private Long id;

    private String description;
}