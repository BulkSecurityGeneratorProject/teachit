package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.ContentViewDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ContentView and its DTO ContentViewDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContentViewMapper {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "content.id", target = "contentId")
    @Mapping(source = "content.description", target = "contentDescription")
    ContentViewDTO contentViewToContentViewDTO(ContentView contentView);

    List<ContentViewDTO> contentViewsToContentViewDTOs(List<ContentView> contentViews);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "contentId", target = "content")
    ContentView contentViewDTOToContentView(ContentViewDTO contentViewDTO);

    List<ContentView> contentViewDTOsToContentViews(List<ContentViewDTO> contentViewDTOs);

    default Person personFromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }

    default Content contentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Content content = new Content();
        content.setId(id);
        return content;
    }
}
