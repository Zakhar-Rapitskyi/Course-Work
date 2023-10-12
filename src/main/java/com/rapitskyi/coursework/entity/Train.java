package com.rapitskyi.coursework.entity;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Train {
    private int id;
    private String startStation;
    private String endStation;
    private List<IntermidiateStation> intermediateStations;
    private String departureTime;
    private String arrivalTime;
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

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void addIntermediateStation(String stationName, String arrival) {
        intermediateStations.add(intermediateStations.size() - 1, new IntermidiateStation(stationName, arrival));
    }

    public Train() {
    }

    public Train(int id, String startStation, String endStation, String departureTime, String arrivalTime, double distance) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.intermediateStations = new LinkedList<>();
        intermediateStations.add(new IntermidiateStation(startStation, departureTime));
        intermediateStations.add(new IntermidiateStation(endStation, arrivalTime));
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.distance = distance;
    }

    public Train(Train other) {
        this.id = other.id;
        this.startStation = other.startStation;
        this.endStation = other.endStation;
        this.intermediateStations = new LinkedList<>(other.intermediateStations);
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

    record IntermidiateStation(String name, String arrival) {
    }
}
