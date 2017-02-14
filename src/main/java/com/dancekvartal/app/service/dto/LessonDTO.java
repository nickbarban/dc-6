package com.dancekvartal.app.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Lesson entity.
 */
public class LessonDTO implements Serializable {

    private Long id;

    private ZonedDateTime startLesson;

    private ZonedDateTime endLesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getStartLesson() {
        return startLesson;
    }

    public void setStartLesson(ZonedDateTime startLesson) {
        this.startLesson = startLesson;
    }
    public ZonedDateTime getEndLesson() {
        return endLesson;
    }

    public void setEndLesson(ZonedDateTime endLesson) {
        this.endLesson = endLesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LessonDTO lessonDTO = (LessonDTO) o;

        if ( ! Objects.equals(id, lessonDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LessonDTO{" +
            "id=" + id +
            ", startLesson='" + startLesson + "'" +
            ", endLesson='" + endLesson + "'" +
            '}';
    }
}
