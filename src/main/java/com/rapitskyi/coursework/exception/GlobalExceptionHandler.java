package com.rapitskyi.coursework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message",ex.getMessage());
        return "errorpage";
    }
    @ExceptionHandler(TrainNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTrainNotFoundException(Exception ex, Model model) {
        model.addAttribute("message",ex.getMessage());
        return "trainDetails";
    }
}