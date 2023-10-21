package com.rapitskyi.coursework.dto.request;

public record TrainsRequest(String fromStation, String toStation, Boolean isDeparture, String timeFrom, String timeTo) {
}
