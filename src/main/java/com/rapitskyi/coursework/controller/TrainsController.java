package com.rapitskyi.coursework.controller;

import com.rapitskyi.coursework.entity.Train;
import com.rapitskyi.coursework.entity.Trains;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TrainsController {
    private final Trains trains = Trains.readFromFile();

    @RequestMapping("/")
    String getTrains(Model model) {
        model.addAttribute("trains", trains);
        return "trains";
    }

    @RequestMapping("/sortByStation")
    String getSortedByStation(Model model) {
        Trains sortedTrains = trains.sortByStartStation();
        model.addAttribute("trains", sortedTrains);
        return "trains";
    }

    @RequestMapping("/findForm")
    String findByStation() {
        return "find-form";
    }
    @RequestMapping("/findByStation")
    String findByStation(Model model,@RequestParam("station") String station) {
        model.addAttribute("trains", trains.getThatRunThrogh(station));
        return "find-form";
    }

    @RequestMapping("/trainDetails/{id}")
    String getIntermidiateStations(Model model, @PathVariable int id) {
        Train train = trains.getById(id);
        model.addAttribute("train", train);
        return "trainDetails";
    }
}
