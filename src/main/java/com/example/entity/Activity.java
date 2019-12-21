package com.example.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String owner;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private boolean active;

    private boolean closed;

    public Activity() {
    }

    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;
        Activity activity = (Activity) o;
        return getId().equals(activity.getId()) &&
                isActive() == activity.isActive() &&
                isClosed() == activity.isClosed() &&
                getName().equals(activity.getName()) &&
                getDescription().equals(activity.getDescription()) &&
                Objects.equals(getOwner(), activity.getOwner()) &&
                getStartDateTime().equals(activity.getStartDateTime()) &&
                Objects.equals(getEndDateTime(), activity.getEndDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getOwner(), getStartDateTime(), getEndDateTime(), isActive(), isClosed());
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", active=" + active +
                ", closed=" + closed +
                '}';
    }


    public static final class ActivityBuilder {
        private Integer id;
        private String name;
        private String description;
        private String owner;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private boolean active;
        private boolean closed;

        private ActivityBuilder() {
        }

        public static ActivityBuilder anActivity() {
            return new ActivityBuilder();
        }

        public ActivityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ActivityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ActivityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ActivityBuilder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public ActivityBuilder withStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        public ActivityBuilder withEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        public ActivityBuilder withActive(boolean active) {
            this.active = active;
            return this;
        }

        public ActivityBuilder withClosed(boolean closed) {
            this.closed = closed;
            return this;
        }

        public Activity build() {
            Activity activity = new Activity();
            activity.setId(id);
            activity.setName(name);
            activity.setDescription(description);
            activity.setOwner(owner);
            activity.setStartDateTime(startDateTime);
            activity.setEndDateTime(endDateTime);
            activity.setActive(active);
            activity.setClosed(closed);
            return activity;
        }
    }
}
