package com.rapitskyi.coursework.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Train {
    private int id;
    private String startStation;
    private String endStation;
    private List<String> intermediateStations;
    private Date departureTime;
    private Date arrivalTime;
    private double distance;

    public void setId(int id) {
        this.id = id;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public void setIntermediateStations(List<String> intermediateStations) {
        this.intermediateStations = intermediateStations;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Train() {
    }

    public Train(int id, String startStation, String endStation,
                 List<String> intermediateStations, Date departureTime, Date arrivalTime, double distance) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.intermediateStations = intermediateStations;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.distance = distance;
    }

    public Train(Train other) {
        this.id = other.id;
        this.startStation = other.startStation;
        this.endStation = other.endStation;
        this.intermediateStations = new ArrayList<>(other.intermediateStations);
        this.departureTime = other.departureTime;
        this.arrivalTime = other.arrivalTime;
        this.distance = other.distance;
    }

    @Override
    public String toString() {
        return "\nTrain{" +
                "id=" + id +
                ", startStation='" + startStation + '\'' +
                ", endStation='" + endStation + '\'' +
                ", intermediateStations=" + intermediateStations +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", distance=" + distance +
                '}';
    }
}
