package com.rapitskyi.coursework;

import com.rapitskyi.coursework.entity.Train;
import com.rapitskyi.coursework.service.Trains;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class CourseWorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseWorkApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//            initializeFileWithTrains(1000);
//        };
//    }

    private static void initializeFileWithTrains(int count) {
        List<String> stationNames = Trains.readStationsFromFile();
        Trains trains = new Trains();
        Train train = new Train(1, "Коломия", "Київ", "01:23", "09:43", 657);
        train.addIntermediateStation("Тернопіль", "03:35");
        train.addIntermediateStation("Хмельницький", "04:41");
        train.addIntermediateStation("Вінниця", "06:18");
        trains.add(train);

        train = new Train(2, "Івано-Франківськ", "Одеса", "14:30", "21:00", 462);
        train.addIntermediateStation("Львів", "16:35");
        train.addIntermediateStation("Тернопіль", "18:07");
        train.addIntermediateStation("Хмельницький", "19:43");
        train.addIntermediateStation("Жмеринка", "20:36");
        trains.add(train);

        Random random = new Random();

        for (int i = 3; i <= count; i++) {
            String startStation = stationNames.get(random.nextInt(stationNames.size()));
            String endStation = stationNames.get(random.nextInt(stationNames.size()));

            while (startStation.equals(endStation))
                endStation = stationNames.get(random.nextInt(stationNames.size()));


            String departureTime = String.format("%02d:%02d", random.nextInt(24), random.nextInt(60));
            double distance = random.nextInt(1000);

            List<Train.IntermediateStation> intermediateStations = new LinkedList<>();
            // Додамо випадкову кількість проміжних станцій
            int numStations = random.nextInt(5) + 1;
            String currentTime = departureTime;
            for (int j = 0; j < numStations; j++) {
                String intermediateStationName = stationNames.get(random.nextInt(stationNames.size()));
                while (containsName(train, intermediateStationName))
                    intermediateStationName = stationNames.get(random.nextInt(stationNames.size()));

                int hours = random.nextInt(3);
                int minutes = random.nextInt(60);
                LocalTime intermediateArrivalTime = LocalTime.parse(currentTime).plusHours(hours).plusMinutes(minutes);
                intermediateStations.add(new Train.IntermediateStation(intermediateStationName, intermediateArrivalTime.toString()));
                currentTime = intermediateArrivalTime.toString();
            }
            train = new Train(i, startStation, endStation, departureTime, LocalTime.parse(currentTime).plusHours(1).plusMinutes(23).toString(), distance);
            train.addIntermediateStations(intermediateStations);
            trains.add(train);
        }
        trains.writeToFile();
    }

    private static boolean containsName(Train train, String intermediateStationName) {
        return train.getIntermediateStations().stream().anyMatch(st -> st.name().equals(intermediateStationName));
    }
}
