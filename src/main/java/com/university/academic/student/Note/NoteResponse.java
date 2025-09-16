package com.university.academic.student.Note;

import com.university.academic.student.Activity.Activity;

import java.math.BigDecimal;

public record NoteResponse(
        BigDecimal value,
        String observation,
        Activity activityId
) {
}
