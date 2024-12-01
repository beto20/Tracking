package com.example.demo.model.mapper;

import com.example.demo.model.dto.TrackerDto;
import com.example.demo.model.dto.TrackerRequestDto;
import com.example.demo.model.dto.TrackerResponseDto;
import com.example.demo.model.entity.TrackerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrackerMapper {
    TrackerMapper mapper = Mappers.getMapper(TrackerMapper.class);

    TrackerResponseDto toResponseDto(TrackerDto trackerDto);
    TrackerDto toDto(TrackerEntity trackerEntity);
    TrackerEntity toEntity(TrackerRequestDto trackerRequestDto);

}
