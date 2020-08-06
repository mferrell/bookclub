package com.mferrell.bookclub.service.mapper;


import com.mferrell.bookclub.domain.*;
import com.mferrell.bookclub.service.dto.ChapterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chapter} and its DTO {@link ChapterDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface ChapterMapper extends EntityMapper<ChapterDTO, Chapter> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "book.id", target = "bookId")
    ChapterDTO toDto(Chapter chapter);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(target = "topics", ignore = true)
    @Mapping(target = "removeTopic", ignore = true)
    @Mapping(source = "bookId", target = "book")
    Chapter toEntity(ChapterDTO chapterDTO);

    default Chapter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chapter chapter = new Chapter();
        chapter.setId(id);
        return chapter;
    }
}
