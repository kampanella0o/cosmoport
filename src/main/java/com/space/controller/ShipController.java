package com.space.controller;

import com.space.model.GetShipsRequest;
import com.space.model.PostShipRequest;
import com.space.model.Ship;
import com.space.service.BadRequestException;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ShipController {

    @Autowired
    ShipService shipService;

    @GetMapping("/rest/ships")
    public ResponseEntity<List<Ship>> getShips(GetShipsRequest getShipsRequest) {
        return new ResponseEntity<>(shipService.filterShips(
                getShipsRequest.getName(),
                getShipsRequest.getPlanet(),
                getShipsRequest.getShipTypes(),
                getShipsRequest.getDateAfter(),
                getShipsRequest.getDateBefore(),
                getShipsRequest.getIsUsedSet(),
                getShipsRequest.getMinSpeed(),
                getShipsRequest.getMaxSpeed(),
                getShipsRequest.getMinCrewSize(),
                getShipsRequest.getMaxCrewSize(),
                getShipsRequest.getMinRating(),
                getShipsRequest.getMaxRating(),
                getShipsRequest.getOrder(),
                getShipsRequest.getPageNumber(),
                getShipsRequest.getPageSize()),
                HttpStatus.OK);
    }

    @GetMapping("/rest/ships/count")
    public ResponseEntity<Integer> getShipsCount(GetShipsRequest getShipsRequest) {
        return new ResponseEntity<>(shipService.filterShipsAndReturnCount(
                getShipsRequest.getName(),
                getShipsRequest.getPlanet(),
                getShipsRequest.getShipTypes(),
                getShipsRequest.getDateAfter(),
                getShipsRequest.getDateBefore(),
                getShipsRequest.getIsUsedSet(),
                getShipsRequest.getMinSpeed(),
                getShipsRequest.getMaxSpeed(),
                getShipsRequest.getMinCrewSize(),
                getShipsRequest.getMaxCrewSize(),
                getShipsRequest.getMinRating(),
                getShipsRequest.getMaxRating()),
                HttpStatus.OK);
    }

    @GetMapping("/rest/ships/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable(value = "id") Long id) {

        Ship responseShip;

        try {
            responseShip = shipService.getShip(id);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (responseShip == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(responseShip, HttpStatus.OK);
        }

    }

    @PostMapping("/rest/ships")
    public ResponseEntity<Ship> createShip(@RequestBody PostShipRequest postShipRequest) {
        return shipService.createShip(postShipRequest.getName(),
                postShipRequest.getPlanet(),
                postShipRequest.getShipType(),
                postShipRequest.getProdDate(),
                postShipRequest.getIsUsed(),
                postShipRequest.getSpeed(),
                postShipRequest.getCrewSize());
    }

    @PostMapping("/rest/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable(value = "id") String stringId, @RequestBody PostShipRequest postShipRequest) {
        if (postShipRequest == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return shipService.updateShip(
                stringId,
                postShipRequest.getName(),
                postShipRequest.getPlanet(),
                postShipRequest.getShipType(),
                postShipRequest.getProdDate(),
                postShipRequest.getIsUsed(),
                postShipRequest.getSpeed(),
                postShipRequest.getCrewSize());
    }

    @DeleteMapping("/rest/ships/{id}")
    public ResponseEntity<Ship> deleteShip(@PathVariable(value = "id") Long id) {
        try {
            shipService.deleteShip(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
