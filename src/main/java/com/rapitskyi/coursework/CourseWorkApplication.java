package com.rapitskyi.coursework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseWorkApplication {
    @Value("${outputFileName}")
    private static String nameOfFile;
    public static void main(String[] args) {
        SpringApplication.run(CourseWorkApplication.class, args);
    }
//    @Bean
//    CommandLineRunner commandLineRunner(){
//        return args -> {
//            Trains trains = new Trains(
//                    new Train(1,"Lviv","Kolomuya", List.of("Ivano-Frankivsk","Galych"),new Date(),new Date(),5676),
//                    new Train(2,"Ternopil","Kolomuya",List.of("Berezhany","Kozova"),new Date(),new Date(),8735),
//                    new Train(3,"Berezhany","Kolomuya",List.of("Burshtun","Galych"),new Date(),new Date(),24657),
//                    new Train(4,"Zboriv","Kolomuya",List.of("Ivano-Frankivsk","Berezhany"),new Date(),new Date(),257),
//                    new Train(5,"Ivano-Frankivsk","Kolomuya",List.of("Ivano-Frankivsk","Galych"),new Date(),new Date(),87768),
//                    new Train(6,"Kyiv","Kolomuya",List.of("Khmelnytskyi","Galych"),new Date(),new Date(),35));
//            trains.writeToFile(nameOfFile);
//        };
//    }
}
