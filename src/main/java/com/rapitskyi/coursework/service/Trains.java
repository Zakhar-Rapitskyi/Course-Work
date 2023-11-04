package com.rapitskyi.coursework.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rapitskyi.coursework.controller.MVCController;
import com.rapitskyi.coursework.dto.request.TrainsRequest;
import com.rapitskyi.coursework.entity.Train;
import com.rapitskyi.coursework.exception.StationNotFoundException;
import com.rapitskyi.coursework.exception.TrainNotFoundException;
import lombok.Getter;

import java.io.*;
import java.text.Collator;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;

import static com.rapitskyi.coursework.controller.MVCController.stations;

@Getter
public class Trains {
    private final List<Train> trains;

    public Trains() {
        trains = new ArrayList<>();
    }

    public Trains(List<Train> trains) {
        this.trains = trains;
    }

    public boolean add(Train train) {
        List<String> stations = MVCController.stations;
        List<String> intermediateStations = train.getIntermediateStations().stream().map(Train.IntermediateStation::name).toList();
        if (!new HashSet<>(stations).containsAll(intermediateStations))
            throw new StationNotFoundException("Неможливо додати поїзд бо станції не існує");
        return trains.add(train);
    }

    public static List<String> readStationsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("stations.json"))) {
            String json = reader.readLine();
            Gson gson = new Gson();
            List<String> stations = gson.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
            System.out.println("Data has been read from " + "stations.json");
            return stations;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage() + "\n(Помилка відкриття файлу)");
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e.getMessage() + "\n(Не правильний вміст файлу)");
        }
    }

    public static Trains readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("trains.json"))) {
            String json = reader.readLine();
            Gson gson = new Gson();

            Trains trainsFromFile = gson.fromJson(json, Trains.class);
            System.out.println("Data has been read from " + "trains.json");
            return Objects.requireNonNullElseGet(trainsFromFile, Trains::new);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage() + "\n(Помилка відкриття файлу)");
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e.getMessage() + "\n(Не правильний вміст файлу)");
        }
    }

    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("trains.json"))) {
            Gson gson = new Gson();
            String json = gson.toJson(this);

            writer.write(json);
            System.out.println("Data has been written to " + "trains.json");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage() + "\n(Помилка відкриття файлу)");
        }
    }

    public Trains findByRequest(TrainsRequest request) {
        return new Trains(trains.stream().filter(train -> train.isMatchingRequest(request)).toList());
    }

    public Trains getThatRunThrough(String station) {
        if (!stations.contains(station)) throw new StationNotFoundException("Станції з ім'ям " + station + " не існує");

        return new Trains(
                trains.stream()
                        .filter(t -> t.getIntermediateStations().stream().anyMatch(st -> st.name().equals(station))).toList()
        );
    }

    public Trains sortedByStartStation() {
        return new Trains(mergeSort(trains));
    }

    private List<Train> mergeSort(List<Train> trainList) {
        if (trainList.size() <= 1) {
            return trainList;
        }

        int middle = trainList.size() / 2;
        List<Train> left = trainList.subList(0, middle);
        List<Train> right = trainList.subList(middle, trainList.size());

        left = mergeSort(left);
        right = mergeSort(right);

        return mergeByStartStation(left, right);
    }

    private List<Train> mergeByStartStation(List<Train> left, List<Train> right) {
        List<Train> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (Collator.getInstance(new Locale("uk", "UA")).compare(left.get(i).getStartStation(), right.get(j).getStartStation()) < 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i++));
        }

        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }

    public Trains sortedBy(String field, String... stationName) {
        Function<Train, Comparable> function;
        switch (field) {
            case "id":
                function = Train::getId;
                break;
            case "startStation":
                return sortedByStartStation();
            case "endStation":
                Collator collator = Collator.getInstance(new Locale("uk", "UA"));
                return new Trains(trains.stream().sorted((t1, t2) -> collator.compare(t1.getEndStation(), t2.getEndStation())).toList());
            case "departure":
                function = t -> LocalTime.parse(t.getDepartureTime());
                break;
            case "arrival":
                function = t -> LocalTime.parse(t.getArrivalTime());
                break;
            case "avgSpeed":
                function = Train::getAverageSpeed;
                break;
            case "distance":
                function = Train::getDistance;
                break;
            case "stationDeparture","stationArrival":
                return new Trains(trains.stream().sorted(Comparator.comparing(t -> LocalTime.parse(t.getIntermediateStation(stationName[0]).arrival()))).toList());
            default:
                throw new RuntimeException("Сортування за полем " + field + " неможливе");
        }
        return new Trains(trains.stream().sorted(Comparator.comparing(function)).toList());
    }

    public Train getById(int id) {
        return trains.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new TrainNotFoundException("Поїзда з номером " + id + " не знайдено"));
    }

    @Override
    public String toString() {
        return "Trains" + trains;
    }
}
