package com.rapitskyi.coursework.controller;

import com.rapitskyi.coursework.entity.Train;
import com.rapitskyi.coursework.entity.Trains;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrainsController {
    @GetMapping("/")
    String getTrains(Model model){
        Trains trains = Trains.readFromFile();
        model.addAttribute("trains",trains.getTrains());
        return "trains";
    }
    @RequestMapping("/intermediateStations/{id}")
    String getIntermidiateStations(Model model, @PathVariable int id){
        Trains trains = Trains.readFromFile();
        Train train = trains.getById(id);
        model.addAttribute("train",train);
        return "intermediateStations";
    }
}
