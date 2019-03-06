package com.sbmongo.starbucks.Repository;

import com.sbmongo.starbucks.Entity.Cities;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("citiesRepository")
public interface CitiesRepository extends MongoRepository<Cities, String> {
    public Cities findBy_id(ObjectId _id);
    public List<Cities> findByCity(String city);
    public List<Integer> countByCity(String city);
}
