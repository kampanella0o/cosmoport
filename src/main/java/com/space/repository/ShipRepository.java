package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Query("SELECT ship FROM Ship ship WHERE" +
            " ship.name LIKE %:name%" +
            " AND ship.planet LIKE %:planet%" +
//            " AND ((:shipTypes) is null or ship.shipType IN (:shipTypes))" +
            " AND ship.shipType IN (:shipTypes)" +
            " AND ship.prodDate BETWEEN :dateAfter AND :dateBefore" +
            " AND ship.isUsed IN (:isUsedSet)" +
            " AND ship.speed BETWEEN :minSpeed AND :maxSpeed" +
            " AND ship.crewSize BETWEEN :minCrewSize AND :maxCrewSize" +
            " AND ship.rating BETWEEN :minRating AND :maxRating")
    List<Ship> filterShips(@Param("name") String name,
                           @Param("planet") String planet,
                           @Param("shipTypes") Set<ShipType> shipTypes,
                           @Param("dateAfter") Date dateAfter,
                           @Param("dateBefore") Date dateBefore,
                           @Param("isUsedSet") Set<Boolean> isUsedSet,
                           @Param("minSpeed") Double minSpeed,
                           @Param("maxSpeed") Double maxSpeed,
                           @Param("minCrewSize") Integer minCrewSize,
                           @Param("maxCrewSize") Integer maxCrewSize,
                           @Param("minRating") Double minRating,
                           @Param("maxRating") Double maxRating,
                           Pageable pageable
                           );

    @Query("SELECT COUNT(ship)" +
            " FROM Ship ship WHERE" +
            " ship.name LIKE %:name%" +
            " AND ship.planet LIKE %:planet%" +
            " AND ship.shipType IN (:shipTypes)" +
            " AND ship.prodDate BETWEEN :dateAfter AND :dateBefore" +
            " AND ship.isUsed IN (:isUsedSet)" +
            " AND ship.speed BETWEEN :minSpeed AND :maxSpeed" +
            " AND ship.crewSize BETWEEN :minCrewSize AND :maxCrewSize" +
            " AND ship.rating BETWEEN :minRating AND :maxRating")
    Integer filterShipsAndReturnCount(@Param("name") String name,
                           @Param("planet") String planet,
                           @Param("shipTypes") Set<ShipType> shipTypes,
                           @Param("dateAfter") Date dateAfter,
                           @Param("dateBefore") Date dateBefore,
                           @Param("isUsedSet") Set<Boolean> isUsedSet,
                           @Param("minSpeed") Double minSpeed,
                           @Param("maxSpeed") Double maxSpeed,
                           @Param("minCrewSize") Integer minCrewSize,
                           @Param("maxCrewSize") Integer maxCrewSize,
                           @Param("minRating") Double minRating,
                           @Param("maxRating") Double maxRating
                           );

    Ship getById(Long id);


}
