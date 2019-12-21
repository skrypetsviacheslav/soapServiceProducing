package com.example.endpoint;

import activity.soapservice.*;
import com.example.mapper.ActivityMapper;
import com.example.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Endpoint
public class ActivityEndpoint {
    private static final Logger log = LoggerFactory.getLogger(ActivityEndpoint.class);

    private static final String NAMESPACE_URL = "http://soapservice.activity";

    private ActivityService activityService;

    @Autowired
    public ActivityEndpoint(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getActivitiesRequest")
    @ResponsePayload
    public GetActivitiesResponse getActivities() {
        ActivityMapper mapper = ActivityMapper.INSTANCE;

        return StreamSupport.stream(activityService.getAllActivities().spliterator(), false)
                .map(mapper::activityToActivityDto)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        (List<ActivityDto> activityDtos) -> {
                            GetActivitiesResponse response = new GetActivitiesResponse();
                            response.getActivity().addAll(activityDtos);
                            return response;
                        }));
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getActivityRequest")
    @ResponsePayload
    public GetActivityResponse getActivity(@RequestPayload GetActivityRequest request) {
        ActivityMapper mapper = ActivityMapper.INSTANCE;

        return activityService.getActivityById(request.getId())
                .map(mapper::activityToActivityDto)
                .map(x -> {
                    GetActivityResponse response = new GetActivityResponse();
                    response.setActivity(x);
                    return response;
                })
                .orElseGet(GetActivityResponse::new);
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "postActivityRequest")
    @ResponsePayload
    public PostActivityResponse postActivity(@RequestPayload PostActivityRequest request) {
        ActivityMapper mapper = ActivityMapper.INSTANCE;
        return activityService.createActivity(mapper.activityDtoToActivity(request.getActivity()))
                .map(mapper::activityToActivityDto)
                .map(x -> {
                    PostActivityResponse response = new PostActivityResponse();
                    response.setActivity(x);
                    return response;
                })
                .orElseGet(PostActivityResponse::new);
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "putActivityRequest")
    @ResponsePayload
    public PutActivityResponse putActivity(@RequestPayload PutActivityRequest request) {
        ActivityMapper mapper = ActivityMapper.INSTANCE;
        return activityService.updateActivity(request.getId(), mapper.activityDtoToActivity(request.getActivity()))
                .map(mapper::activityToActivityDto)
                .map(x -> {
                    PutActivityResponse response = new PutActivityResponse();
                    response.setActivity(x);
                    return response;
                })
                .orElseGet(PutActivityResponse::new);
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "deleteActivityRequest")
    @ResponsePayload
    public DeleteActivityResponse deleteActivity(@RequestPayload DeleteActivityRequest request) {
        ActivityMapper mapper = ActivityMapper.INSTANCE;

        activityService.deleteActivity(request.getId());

        DeleteActivityResponse response = new DeleteActivityResponse();
        response.setDeleted(true);
        return response;
    }
}
