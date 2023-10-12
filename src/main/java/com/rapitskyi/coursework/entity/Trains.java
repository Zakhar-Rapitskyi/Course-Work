package com.rapitskyi.coursework.entity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapitskyi.coursework.exception.TrainNotFoundException;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Trains {


    private final List<Train> trains;

    public Trains getThatRunThrogh(String station) {
        return new Trains(
                trains.stream()
                        .filter(train -> train.getIntermediateStations() != null &&
                                train.getIntermediateStations().stream()
                                        .anyMatch(st ->
                                                st.name().equals(station))).toList()
        );
    }

    private static final String nameOfFile = "trains.json";

    public static Trains readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nameOfFile))) {
            String json = reader.readLine();
            Gson gson = new Gson();

            Trains trainsFromFile = gson.fromJson(json, Trains.class);
            System.out.println("Data has been read from " + nameOfFile);
            return Objects.requireNonNullElseGet(trainsFromFile, Trains::new);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage() + "\n(Помилка відкриття файлу)");
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e.getMessage() + "\n(Не правильний вміст файлу)");
        }
    }

    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameOfFile))) {
            Gson gson = new Gson();
            String json = gson.toJson(this);

            writer.write(json);
            System.out.println("Data has been written to " + nameOfFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage() + "\n(Помилка відкриття файлу)");
        }
    }

    public Trains sortByStartStation() {
        List<Train> sortedTrains = mergeSortByStartStation(trains);
        return new Trains(sortedTrains);
    }

    private List<Train> mergeSortByStartStation(List<Train> trainList) {
        if (trainList.size() <= 1) {
            return trainList;
        }

        int middle = trainList.size() / 2;
        List<Train> left = trainList.subList(0, middle);
        List<Train> right = trainList.subList(middle, trainList.size());

        left = mergeSortByStartStation(left);
        right = mergeSortByStartStation(right);

        return merge(left, right);
    }

    private List<Train> merge(List<Train> left, List<Train> right) {
        List<Train> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getStartStation().compareTo(right.get(j).getStartStation()) <= 0) {
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


    private int size() {
        return trains.size();
    }

    private void set(int i, Train train) {
        trains.set(i, train);
    }

    private Train get(int i) {
        return trains.get(i);
    }

    @Override
    public String toString() {
        return "Trains" + trains;
    }

    public Trains() {
        trains = new ArrayList<>();
    }

    public Trains(Train... train) {
        trains = new ArrayList<>();
        trains.addAll(List.of(train));
    }

    public Trains(List<Train> trains) {
        this.trains = trains;
    }

    public Train getById(int id) {
        try {
            return trains.stream().filter(t -> t.getId() == id).findFirst().orElseThrow(TrainNotFoundException::new);
        } catch (TrainNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
