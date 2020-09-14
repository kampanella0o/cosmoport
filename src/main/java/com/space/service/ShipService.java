package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ShipService {

    @Autowired
    ShipRepository shipRepository;


    public List<Ship> filterShips(String name, String planet, Set<ShipType> shipTypes,
                                  Date dateAfter, Date dateBefore, Set<Boolean> isUsedSet,
                                  Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                  Integer maxCrewSize, Double minRating, Double maxRating,
                                  ShipOrder orderBy, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy.getFieldName()));

        return shipRepository.filterShips(name, planet, shipTypes, dateAfter,
                dateBefore, isUsedSet, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating,
                maxRating, pageable);
    }

    public Integer filterShipsAndReturnCount(String name, String planet, Set<ShipType> shipTypes,
                                  Date dateAfter, Date dateBefore, Set<Boolean> isUsedSet,
                                  Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                  Integer maxCrewSize, Double minRating, Double maxRating) {

        return shipRepository.filterShipsAndReturnCount(name, planet, shipTypes, dateAfter,
                dateBefore, isUsedSet, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating,
                maxRating);
    }

    public Ship getShip(Long id) throws BadRequestException {
        if (id <= 0) throw new BadRequestException();

        return shipRepository.getById(id);
    }

    public ResponseEntity<Ship> createShip(String name, String planet, ShipType shipType, Long longProdDate, Boolean isUsed, Double speed, Integer crewSize) {

        ResponseEntity<Ship> badRequest = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (name == null || planet == null || shipType == null ||
                longProdDate == null || speed == null || crewSize == null) return badRequest;

        if (name.length() < 1 || name.length() > 50) return badRequest;

        if (planet.length() < 1 || planet.length() > 50) return badRequest;

        Calendar calendar = new GregorianCalendar();
        Date prodDate = new Date(longProdDate);
        calendar.setTime(prodDate);
        int productionYear = calendar.get(Calendar.YEAR);
        if (productionYear < 2800 || productionYear > 3019) return badRequest;

        speed = roundToThreeDigits(speed);
        if (speed < 0.01 || speed > 0.99) return badRequest;

        if (crewSize < 1 || crewSize > 9999) return badRequest;

        isUsed = isUsed == null ?  false : isUsed;
        Double rating = calculateRating(speed, isUsed, prodDate);

        Ship shipToSave = new Ship();
        shipToSave.setName(name);
        shipToSave.setPlanet(planet);
        shipToSave.setShipType(shipType);
        shipToSave.setProdDate(prodDate);
        shipToSave.setIsUsed(isUsed);
        shipToSave.setSpeed(speed);
        shipToSave.setCrewSize(crewSize);
        shipToSave.setRating(rating);
        Ship savedShip = shipRepository.save(shipToSave);
        return (new ResponseEntity<>(savedShip, HttpStatus.OK));
    }


    public ResponseEntity<Ship> updateShip(String stringId, String name, String planet, ShipType shipType, Long longProdDate, Boolean isUsed, Double speed, Integer crewSize) {
        Long id;
        if (!stringId.matches("\\d*") || (id = Long.parseLong(stringId)) == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship shipToUpdate = shipRepository.getById(id);

        if (shipToUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ResponseEntity<Ship> badRequest = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            if (name != null){
                if (name.length() < 1 || name.length() > 50) return badRequest;
                shipToUpdate.setName(name);
            }

            if (planet != null){
                if (planet.length() < 1 || planet.length() > 50) return badRequest;
                shipToUpdate.setPlanet(planet);
            }

            shipToUpdate.setShipType(shipType == null ? shipToUpdate.getShipType() : shipType);

            if (longProdDate != null) {
                Calendar calendar = new GregorianCalendar();
                Date prodDate = new Date(longProdDate);
                calendar.setTime(prodDate);
                int productionYear = calendar.get(Calendar.YEAR);
                if (productionYear < 2800 || productionYear > 3019) return badRequest;
                shipToUpdate.setProdDate(prodDate);
            }

            shipToUpdate.setIsUsed(isUsed == null ? shipToUpdate.getIsUsed() : isUsed);

            if (speed != null) {
                speed = roundToThreeDigits(speed);
                if (speed < 0.01 || speed > 0.99) return badRequest;
                shipToUpdate.setSpeed(speed);
            }

            if (crewSize != null) {
                if (crewSize < 1 || crewSize > 9999) return badRequest;
                shipToUpdate.setCrewSize(crewSize);
            }

            shipToUpdate.setRating(calculateRating(shipToUpdate.getSpeed(), shipToUpdate.getIsUsed(),  shipToUpdate.getProdDate()));
            Ship savedShip = shipRepository.save(shipToUpdate);
            return new ResponseEntity<>(savedShip, HttpStatus.OK);
        }
    }

    public void deleteShip(Long id) throws BadRequestException {
        if (id <= 0) throw new BadRequestException();
        shipRepository.deleteById(id);
    }

    private Double roundToThreeDigits(Double value){
        BigDecimal db = new BigDecimal(value);
        return db.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private Double calculateRating(Double speed, Boolean isUsed, Date prodDate){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(prodDate);
        int currentYear = calendar.get(Calendar.YEAR);
        double usedStateFactor = isUsed ? 0.5 : 1D;
        return  roundToThreeDigits((80 * speed * usedStateFactor) / (3019 - currentYear + 1));
    }


}
