package com.example.service;

import com.example.entity.Activity;
import com.example.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ActivityService {
    private ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Iterable<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(Integer activityId) {
        return activityRepository.findById(activityId);
    }

    public Optional<Activity> createActivity(Activity activity) {
        return Optional.of(activityRepository.save(activity));
    }

    @Transactional
    public Optional<Activity> updateActivity(Integer activityId, Activity activity) {
        activity.setId(activityId);
        return activityRepository.findById(activityId).isPresent()
                ? Optional.of(activityRepository.save(activity))
                : Optional.empty();
    }

    public void deleteActivity(Integer activityId) {
        activityRepository.deleteById(activityId);
    }
}
