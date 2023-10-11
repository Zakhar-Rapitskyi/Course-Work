package com.rapitskyi.coursework.entity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapitskyi.coursework.exception.TrainNotFoundException;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Trains {

    private static final String nameOfFile = "trains.json";

    private List<Train> trains;

    public Trains getThatRunThrogh(String station){
        return new Trains(
                trains.stream().filter(train -> trains.contains(station)).toList()
        );
    }
    public static Trains readFromFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader(nameOfFile))) {
            String json = reader.readLine();
            Gson gson = new Gson();

            Trains trainsFromFile = gson.fromJson(json, Trains.class);
            System.out.println("Data has been read from " + nameOfFile);
            if(trainsFromFile==null){
                return new Trains();
            }
            return trainsFromFile;
        } catch (IOException e){
            throw new RuntimeException(e.getMessage()+"\n(Помилка відкриття файлу)");
        } catch (JsonSyntaxException e){
            throw new RuntimeException(e.getMessage()+"\n(Не правильний вміст файлу)");
        }
    }
    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameOfFile))) {
            Gson gson = new Gson();
            String json = gson.toJson(this);

            writer.write(json);
            System.out.println("Data has been written to " + nameOfFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage()+"\n(Помилка відкриття файлу)");
        }
    }
    public void sortByStartStation() {
        mergeSortByStartStation();
    }
    private void mergeSortByStartStation() {
        if (trains.size() <= 1) {
            return;
        }

        int middle = trains.size() / 2;
        Trains left = new Trains(trains.subList(0, middle));
        Trains right = new Trains(trains.subList(middle, trains.size()));

        left.mergeSortByStartStation();
        right.mergeSortByStartStation();

        merge(left, right);
    }

    private void merge(Trains left, Trains right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getStartStation().compareTo(right.get(j).getStartStation()) < 0) {
                trains.set(k++, left.get(i++));
            } else {
                trains.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            trains.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            trains.set(k++, right.get(j++));
        }
    }

    private int size() {
        return trains.size();
    }

    private void set(int i, Train train) {
        trains.set(i,train);
    }

    private Train get(int i) {
        return trains.get(i);
    }

    @Override
    public String toString() {
        return "Trains"+ trains;
    }

    public Trains() {
        trains = new ArrayList<>();
    }
    public Trains(Train ... train){
        trains = new ArrayList<>();
        trains.addAll(List.of(train));
    }
    public Trains(List<Train> trains) {
        this.trains = trains;
    }

    public Train getById(int id){
        try {
            return trains.stream().filter(t->t.getId()==id).findFirst().orElseThrow(TrainNotFoundException::new);
        } catch (TrainNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
