package de.presti.vkntf.backend.api.request;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public record AppointmentCreateRequest(String classroomId, LocalDateTime date, List<String> students) {
}