package com.rapitskyi.coursework.entity;

import com.rapitskyi.coursework.dto.request.TrainsRequest;
import lombok.Getter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Getter
public class Train {
    private int id;
    private String startStation;
    private String endStation;
    private List<IntermediateStation> intermediateStations;
    private String departureTime;
    private String arrivalTime;
    private double distance;

    //конструктор за замовчуванням
    public Train() {
    }

    //конструктор з параметрами
    public Train(int id, String startStation, String endStation, String departureTime, String arrivalTime, double distance) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.intermediateStations = new LinkedList<>();
        intermediateStations.add(new IntermediateStation(startStation, departureTime));
        intermediateStations.add(new IntermediateStation(endStation, arrivalTime));
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.distance = distance;
    }

    //конструктор копіювання
    public Train(Train other) {
        this.id = other.id;
        this.startStation = other.startStation;
        this.endStation = other.endStation;
        this.intermediateStations = new LinkedList<>(other.intermediateStations);
        this.departureTime = other.departureTime;
        this.arrivalTime = other.arrivalTime;
        this.distance = other.distance;
    }

    public void addIntermediateStation(String stationName, String arrival) {
        intermediateStations.add(intermediateStations.size() - 1, new IntermediateStation(stationName, arrival));
    }

    public double getAverageSpeed() {
        LocalTime departure = LocalTime.parse(departureTime);
        LocalTime arrival = LocalTime.parse(arrivalTime);
        long minutes = departure.until(arrival, ChronoUnit.MINUTES);
        double hours = minutes / 60.0;
        return distance / hours;
    }

    public boolean isMatchingRequest(TrainsRequest request) {
        IntermediateStation
                startStation = getIntermediateStation(request.fromStation()),
                endStation = getIntermediateStation(request.toStation());
        if (startStation != null && endStation != null) {
            LocalTime
                    departure = LocalTime.parse(startStation.arrival()),
                    arrival = LocalTime.parse(endStation.arrival());
            if (departure.isBefore(arrival)) {
                LocalTime
                        timeFrom = LocalTime.parse(request.timeFrom()),
                        timeTo = LocalTime.parse(request.timeTo());
                if (request.isDeparture()) {
                    return departure.isAfter(timeFrom) &&
                            departure.isBefore(timeTo);
                } else {
                    return arrival.isAfter(timeFrom) &&
                            arrival.isBefore(timeTo);
                }
            }
        }
        return false;
    }

    public IntermediateStation getIntermediateStation(String name) {
        return intermediateStations.stream().filter(st -> st.name.equals(name)).findFirst().orElse(null);
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

    public record IntermediateStation(String name, String arrival) {
    }
}
