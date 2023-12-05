package de.presti.vkntf.backend.api.request;

import java.sql.Timestamp;
import java.util.List;

public record AppointmentCreateRequest(String classromId, Timestamp date, List<String> students) {
}