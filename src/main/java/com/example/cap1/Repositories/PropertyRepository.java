package com.example.cap1.Repositories;


import com.example.cap1.Models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select p from Property p where p.property_id= :propertyId")
    Property findPropertyById(@Param("propertyId") Long propertyId);

    @Query("select p from Property p where p.property_isRented= false ")
    List<Property> findProperties();

    @Query("select p from Property p where p.property_type = :propertyType and p.property_isRented= false ")
    List<Property> findByPropertyType(@Param("propertyType") String propertyType);

    @Query("select p from Property p where p.property_price between :minPrice  and :maxPrice and p.property_type= :propertyType and p.property_isRented= false ")
    List<Property> findByPropertyPriceBetweenAndType(@Param("minPrice") Double minPrice,
                                                     @Param("maxPrice") Double maxPrice,
                                                     @Param("propertyType") String propertyType);


}
