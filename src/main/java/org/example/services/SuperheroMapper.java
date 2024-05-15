package org.example.services;

import org.example.model.Superhero;
import org.example.services.restclient.SuperheroData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SuperheroMapper {
    @Mapping(target = "identity", source = "biography.fullName")
    Superhero toSuperheroEntity(SuperheroData superheroData);

    List<Superhero> toSuperheroEntityList(List<SuperheroData> enterpriseEntityList);
}
