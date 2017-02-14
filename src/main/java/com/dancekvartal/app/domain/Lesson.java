package com.dancekvartal.app.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "lesson")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lesson")
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_lesson")
    private ZonedDateTime startLesson;

    @Column(name = "end_lesson")
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

    public Lesson startLesson(ZonedDateTime startLesson) {
        this.startLesson = startLesson;
        return this;
    }

    public void setStartLesson(ZonedDateTime startLesson) {
        this.startLesson = startLesson;
    }

    public ZonedDateTime getEndLesson() {
        return endLesson;
    }

    public Lesson endLesson(ZonedDateTime endLesson) {
        this.endLesson = endLesson;
        return this;
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
        Lesson lesson = (Lesson) o;
        if (lesson.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + id +
            ", startLesson='" + startLesson + "'" +
            ", endLesson='" + endLesson + "'" +
            '}';
    }
}
