package dev.fernandocarvalho.redis.runners;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Log
@Component
public class GeographyRunner implements Runner {

    final RedisTemplate<String, String> rt;

    @Autowired
    public GeographyRunner(RedisTemplate<String, String> rt) {
        this.rt = rt;
    }

    public void run() {
        GeoOperations<String, String> geo = rt.opsForGeo();
        geo.add("Sicily", new Point(13.361389, 38.115556), "Arigento");
        geo.add("Sicily", new Point(15.087269, 37.502669), "Catania");
        geo.add("Sicily", new Point(13.583333, 37.316667), "Palermo");

        Circle circle = new Circle(new Point(13.583333, 37.316667),
                new Distance(100, RedisGeoCommands.DistanceUnit.KILOMETERS));

        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = geo.radius("Sicily", circle);

        geoResults
                .getContent()
                .forEach(c -> log.info(c.toString()));
    }

    @Override
    public String name() {
        return "Geography";
    }
}
