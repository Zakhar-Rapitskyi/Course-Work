package com.rapitskyi.coursework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseWorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseWorkApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//            Trains trains = new Trains(
//                    new Train(1, "Lviv", "Kolomuya", "08:00", "12:00", 5676),
//                    new Train(2, "Ternopil", "Kolomuya", "14:30", "20:00", 8735),
//                    new Train(3, "Berezhany", "Kolomuya", "00:00", "14:30", 24657),
//                    new Train(4, "Zboriv", "Kolomuya", "14:30", "14:30", 257),
//                    new Train(5, "Ivano-Frankivsk", "Kolomuya", "14:30", "14:30", 87768),
//                    new Train(6, "Kyiv", "Kolomuya", "07:00", "22:00", 35));
//            trains.getById(1).addIntermediateStation("Ivano-Frankivsk", "10:00");
//            trains.getById(2).addIntermediateStation("Berezhany", "16:00");
//            trains.getById(2).addIntermediateStation("Rohatun", "17:00");
//            trains.getById(3).addIntermediateStation("Rohatun", "10:00");
//            trains.getById(4).addIntermediateStation("Berezhany", "10:00");
//            trains.getById(6).addIntermediateStation("Khmelnytskyi", "10:00");
//            trains.getById(6).addIntermediateStation("Vinnytsya", "12:00");
//            trains.getById(6).addIntermediateStation("Ternopil", "17:00");
//            trains.writeToFile();
//        };
//    }
}
