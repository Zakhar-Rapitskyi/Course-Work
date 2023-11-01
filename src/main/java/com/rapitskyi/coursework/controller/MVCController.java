package com.rapitskyi.coursework.controller;

import com.rapitskyi.coursework.dto.request.TrainsRequest;
import com.rapitskyi.coursework.service.Trains;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MVCController {
    private final Trains trainsInFile = Trains.readFromFile();
    private Trains trainsInModel = trainsInFile;
    public final List<String> stations = Trains.readStationsFromFile();

    @RequestMapping("/")
    String getMainPage(Model model) {
        model.addAttribute("stations", stations);
        return "main";
    }

    @RequestMapping("/trains")
    String getStationDetail(@ModelAttribute TrainsRequest request, Model model) {
        trainsInModel = trainsInFile.findByRequest(request);
        model.addAttribute("trains", trainsInModel.getTrains());
        model.addAttribute("trainsRequest", request);
        return "trains";
    }

    @RequestMapping("/sort/{field}")
    String getSortedTrains(Model model, @PathVariable String field) {
        model.addAttribute("trains", trainsInModel.sortedBy(field));
        model.addAttribute("trainsRequest", new TrainsRequest(null,null,false,null,null));
        return "trains";
    }

    @RequestMapping("/station/{name}")
    String getStationDetails(Model model, @PathVariable String name) {
        Trains stationTrains = trainsInFile.getThatRunThrough(name);
        model.addAttribute("stationName", name);
        model.addAttribute("trains", stationTrains);
        return "stationDetails";
    }

    @RequestMapping("/trainDetails/{id}")
    String getIntermediateStations(Model model, @PathVariable int id) {
        model.addAttribute("id", id);
        model.addAttribute("train", trainsInFile.getById(id));
        return "trainDetails";
    }

    @RequestMapping("/trains.css")
    String getStyles(){
        return "../static/css/petclinic.css";
    }
}
