package com.example.endpoint;

import activity.soapservice.*;
import com.example.mapper.ActivityMapper;
import com.example.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Endpoint
public class ActivityEndpoint {
    private static final Logger log = LoggerFactory.getLogger(ActivityEndpoint.class);

    private static final String NAMESPACE_URL = "http://soapservice.activity";
    private final ActivityMapper mapper = ActivityMapper.INSTANCE;
    private final ObjectFactory factory = new ObjectFactory();

    private ActivityService activityService;

    public ActivityEndpoint(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getActivitiesRequest")
    @ResponsePayload
    public GetActivitiesResponse getActivities() {
        Function<List<ActivityDto>, GetActivitiesResponse> GetActivitiesResponseWithListOfActivityDto = (List<ActivityDto> activityDtos) -> {
            GetActivitiesResponse response = new GetActivitiesResponse();
            response.getActivity().addAll(activityDtos);
            return response;
        };

        return StreamSupport.stream(activityService.getAllActivities().spliterator(), false)
                .map(mapper::activityToActivityDto)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        GetActivitiesResponseWithListOfActivityDto));
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getActivityRequest")
    @ResponsePayload
    public GetActivityResponse getActivity(@RequestPayload GetActivityRequest request) {
        Function<ActivityDto, GetActivityResponse> GetActivityResponseWithActivityDto = activityDto -> {
            GetActivityResponse response = new GetActivityResponse();
            response.setActivity(activityDto);
            return response;
        };

        return activityService.getActivityById(request.getId())
                .map(mapper::activityToActivityDto)
                .map(GetActivityResponseWithActivityDto)
                .orElseGet(GetActivityResponse::new);
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "postActivityRequest")
    @ResponsePayload
    public PostActivityResponse postActivity(@RequestPayload PostActivityRequest request) {
        Function<ActivityDto, PostActivityResponse> postActivityResponseWithActivityDto = activityDto -> {
            PostActivityResponse response = new PostActivityResponse();
            response.setActivity(activityDto);
            return response;
        };

        return activityService.createActivity(mapper.activityDtoToActivity(request.getActivity()))
                .map(mapper::activityToActivityDto)
                .map(postActivityResponseWithActivityDto)
                .orElseGet(PostActivityResponse::new);
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "putActivityRequest")
    @ResponsePayload
    public PutActivityResponse putActivity(@RequestPayload PutActivityRequest request) {
        Function<ActivityDto, PutActivityResponse> putActivityResponseWithActivityDto = activityDto -> {
            PutActivityResponse response = factory.createPutActivityResponse();
            response.setActivity(activityDto);
            return response;
        };

        return activityService.updateActivity(request.getId(), mapper.activityDtoToActivity(request.getActivity()))
                .map(mapper::activityToActivityDto)
                .map(putActivityResponseWithActivityDto)
                .orElseGet(PutActivityResponse::new);
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "deleteActivityRequest")
    @ResponsePayload
    public DeleteActivityResponse deleteActivity(@RequestPayload DeleteActivityRequest request) {
        activityService.deleteActivity(request.getId());

        DeleteActivityResponse response = factory.createDeleteActivityResponse();
        response.setDeleted(true);
        return response;
    }
}
