package com.space.model;

import com.space.controller.ShipOrder;
import com.space.model.ShipType;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GetShipsRequest {
    public static final long janFirst2800 = 26160710400000L;
    public static final long janFirst3020 = 33134745600000L;
    public static final double speedLowerBound = 0.0099;
    public static final double speedUpperBound = 1.0;
    public static final int crewSizeLowerBound = 0;
    public static final int crewSizeUpperBound = 10000;
    public static final int defaultPageNumber = 0;
    public static final int defaultPageSize = 3;

    private String name = "";
    private String planet = "";
    private ShipType shipType;
    private Set<ShipType> shipTypes = new HashSet<>(Arrays.asList(ShipType.MILITARY,ShipType.TRANSPORT, ShipType.MERCHANT));
    private Long after = janFirst2800;
    private Long before = janFirst3020;
    private Date dateAfter;
    private Date dateBefore;
    private Boolean isUsed;
    private Set<Boolean> isUsedSet = new HashSet<>(Arrays.asList(true, false));
    private Double minSpeed = speedLowerBound;
    private Double maxSpeed = speedUpperBound;
    private Integer minCrewSize = crewSizeLowerBound;
    private Integer maxCrewSize = crewSizeUpperBound;
    private Double minRating = Double.MIN_VALUE;
    private Double maxRating = Double.MAX_VALUE;
    private ShipOrder order = ShipOrder.ID;
    private Integer pageNumber = defaultPageNumber;
    private Integer pageSize = defaultPageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setShipTypes(Set<ShipType> shipTypes) {
        this.shipTypes = shipTypes;
    }

    public Set<ShipType> getShipTypes() {
        return shipType == null ? shipTypes : new HashSet<>(Arrays.asList(shipType));
    }

    public Long getAfter() {
        return after;
    }

    public void setAfter(Long after) {
        this.after = after;
    }

    public Long getBefore() {
        return before;
    }

    public void setBefore(Long before) {
        this.before = before;
    }

    public Date getDateAfter() {
        return new Date(after);
    }

    public void setDateAfter(Date dateAfter) {
        this.dateAfter = dateAfter;
    }

    public Date getDateBefore() {
        return new Date(before);
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean used) {
        isUsed = used;
    }

    public Set<Boolean> getIsUsedSet() {
        return isUsed == null ? isUsedSet : new HashSet<>(Arrays.asList(isUsed));
    }

    public void setIsUsedSet(Set<Boolean> isUsedSet) {
        this.isUsedSet = isUsedSet;
    }

    public Double getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(Double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getMinCrewSize() {
        return minCrewSize;
    }

    public void setMinCrewSize(Integer minCrewSize) {
        this.minCrewSize = minCrewSize;
    }

    public Integer getMaxCrewSize() {
        return maxCrewSize;
    }

    public void setMaxCrewSize(Integer maxCrewSize) {
        this.maxCrewSize = maxCrewSize;
    }

    public Double getMinRating() {
        return minRating;
    }

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }

    public Double getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(Double maxRating) {
        this.maxRating = maxRating;
    }

    public ShipOrder getOrder() {
        return order;
    }

    public void setOrder(ShipOrder order) {
        this.order = order;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
