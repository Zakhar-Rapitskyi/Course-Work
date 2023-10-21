package com.rapitskyi.coursework;

import com.rapitskyi.coursework.entity.Train;
import com.rapitskyi.coursework.service.Trains;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseWorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseWorkApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//            Trains trains = new Trains(
//                    new Train(1, "Львів", "Коломия", "08:00", "12:00", 5676),
//                    new Train(2, "Тернопіль", "Коломия", "14:30", "20:00", 8735),
//                    new Train(3, "Бережани", "Коломия", "00:00", "14:30", 24657),
//                    new Train(4, "Бережани", "Коломия", "14:30", "18:45", 257),
//                    new Train(5, "Івано-Франківськ", "Коломия", "14:30", "16:29", 87768),
//                    new Train(6, "Київ", "Коломия", "07:00", "22:00", 35));
//            trains.getById(1).addIntermediateStation("Івано-Франківськ", "10:00");
//
//            trains.getById(2).addIntermediateStation("Бережани", "16:00");
//            trains.getById(2).addIntermediateStation("Рогатин", "17:00");
//
//            trains.getById(3).addIntermediateStation("Рогатин", "10:00");
//
//            trains.getById(4).addIntermediateStation("Рогатин", "15:00");
//            trains.getById(4).addIntermediateStation("Галич", "16:00");
//
//            trains.getById(5).addIntermediateStation("Рогатин", "15:00");
//
//            trains.getById(6).addIntermediateStation("Хмельницький", "10:00");
//            trains.getById(6).addIntermediateStation("Вінниця", "12:00");
//            trains.getById(6).addIntermediateStation("Тернопіль", "17:00");
//            trains.getById(6).addIntermediateStation("Рогатин", "18:00");
//
//            trains.writeToFile();
//        };
//    }
}
