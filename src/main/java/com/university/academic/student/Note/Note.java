package com.university.academic.student.Note;

import com.university.academic.student.Activity.Activity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value", precision = 7, scale = 4, nullable = false)
    private BigDecimal value;

    @Column(name = "observation", length = 250, nullable = false)
    private String observation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "activity_id")
    private Activity activityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }
}
