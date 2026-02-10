package com.keshav.smartpay.core.user;

import com.keshav.smartpay.api.auth.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RegisterRequestMapper {
    RegisterRequestMapper INSTANCE = Mappers.getMapper(RegisterRequestMapper.class);

    @Mapping(target = "hashedPassword", source = "password")
    User registerRequestToUser(RegisterRequest registerRequest);
}
