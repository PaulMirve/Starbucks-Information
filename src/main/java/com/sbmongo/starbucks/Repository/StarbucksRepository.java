package com.sbmongo.starbucks.Repository;

import com.sbmongo.starbucks.Entity.Starbucks;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("starbucksRepository")
public interface StarbucksRepository extends MongoRepository<Starbucks, String> {
    public Starbucks findByCity(String city);
}
