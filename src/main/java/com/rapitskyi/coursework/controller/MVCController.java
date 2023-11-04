package com.rapitskyi.coursework.controller;

import com.rapitskyi.coursework.dto.request.TrainsRequest;
import com.rapitskyi.coursework.service.Trains;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MVCController {
    public static final List<String> stations = Trains.readStationsFromFile();
    private static final Trains trainsInFile = Trains.readFromFile();

    @RequestMapping("/")
    String getMainPage(Model model) {
        model.addAttribute("stations", stations);
        return "main";
    }

    @RequestMapping("/trains")
    String getStationDetail(@ModelAttribute TrainsRequest request, Model model, @PathParam("field") String field, HttpSession httpSession) {
        Trains trains;
        if (field != null) {
            request = (TrainsRequest) httpSession.getAttribute("trainsRequest");
            trains = ((Trains) httpSession.getAttribute("trainsInModel"))
                    .sortedBy(field, field.equals("stationArrival") ? request.toStation() : request.fromStation());
        } else {
            trains = trainsInFile.findByRequest(request);
            httpSession.setAttribute("trainsRequest", request);
            httpSession.setAttribute("trainsInModel", trains);
        }

        model.addAttribute("trains", trains);
        model.addAttribute("request", request);
        return "trains";
    }

    @RequestMapping("/station/{name}")
    String getStationDetails(Model model, @PathVariable String name, @PathParam("field") String field) {
        Trains stationTrains = trainsInFile.getThatRunThrough(name);

        if (field != null) stationTrains = stationTrains.sortedBy(field, name);

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
    String getStyles() {
        return "../static/css/trains.css";
    }
}



