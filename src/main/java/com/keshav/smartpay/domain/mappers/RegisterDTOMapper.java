package com.keshav.smartpay.domain.mappers;

import com.keshav.smartpay.domain.dtos.RegisterDTO;
import com.keshav.smartpay.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegisterDTOMapper {
    RegisterDTOMapper INSTANCE = Mappers.getMapper(RegisterDTOMapper.class);

    @Mapping(target = "hashedPassword", source = "password")
    User registerDtoToUser(RegisterDTO registerDTO);
}
