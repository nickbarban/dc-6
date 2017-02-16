package com.dancekvartal.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotNull
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "phone_1", nullable = false)
    private String phone1;

    @NotNull
    @Column(name = "phone_2", nullable = false)
    private String phone2;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "student_subjects",
        joinColumns = @JoinColumn(name = "students_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "subjects_id", referencedColumnName = "id"))
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "payer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pay> pays = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "student_parents",
        joinColumns = @JoinColumn(name = "students_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "parents_id", referencedColumnName = "id"))
    private Set<Parent> parents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public Student firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Student lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Student birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public Student address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone1() {
        return phone1;
    }

    public Student phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public Student phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public Student email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActive() {
        return active;
    }

    public Student active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Student photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public Student userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public Student password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public Student subjects(Set<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public Student addSubjects(Subject subject) {
        this.subjects.add(subject);
        subject.getStudents().add(this);
        return this;
    }

    public Student removeSubjects(Subject subject) {
        this.subjects.remove(subject);
        subject.getStudents().remove(this);
        return this;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Pay> getPays() {
        return pays;
    }

    public Student pays(Set<Pay> pays) {
        this.pays = pays;
        return this;
    }

    public Student addPays(Pay pay) {
        this.pays.add(pay);
        // TODO: 2/16/2017 add List<Person> payers to Pay
//        pay.setPayer(this);
        return this;
    }

    public Student removePays(Pay pay) {
        this.pays.remove(pay);
        // TODO: 2/16/2017 add List<Person> payers to Pay
//        pay.setPayer(null);
        return this;
    }

    public void setPays(Set<Pay> pays) {
        this.pays = pays;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Student lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Student addLessons(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.getStudents().add(this);
        return this;
    }

    public Student removeLessons(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.getStudents().remove(this);
        return this;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<Parent> getParents() {
        return parents;
    }

    public Student parents(Set<Parent> parents) {
        this.parents = parents;
        return this;
    }

    public Student addParents(Parent parent) {
        this.parents.add(parent);
        parent.getChildren().add(this);
        return this;
    }

    public Student removeParents(Parent parent) {
        this.parents.remove(parent);
        parent.getChildren().remove(this);
        return this;
    }

    public void setParents(Set<Parent> parents) {
        this.parents = parents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        if (student.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", firstname='" + firstname + "'" +
            ", lastname='" + lastname + "'" +
            ", birthday='" + birthday + "'" +
            ", address='" + address + "'" +
            ", phone1='" + phone1 + "'" +
            ", phone2='" + phone2 + "'" +
            ", email='" + email + "'" +
            ", active='" + active + "'" +
            ", photoUrl='" + photoUrl + "'" +
            ", userName='" + userName + "'" +
            ", password='" + password + "'" +
            '}';
    }
}
