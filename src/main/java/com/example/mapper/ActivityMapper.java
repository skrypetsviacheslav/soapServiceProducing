package com.example.mapper;

import activity.soapservice.ActivityDto;
import com.example.entity.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActivityMapper {
    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    ActivityDto activityToActivityDto(Activity activity);

    Activity activityDtoToActivity(ActivityDto activityDto);
}
