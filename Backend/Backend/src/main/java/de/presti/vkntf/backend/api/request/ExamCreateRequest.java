package de.presti.vkntf.backend.api.request;

import java.sql.Timestamp;
import java.util.List;

public record ExamCreateRequest(String classromId, Timestamp missedAt, List<String> students) {
}