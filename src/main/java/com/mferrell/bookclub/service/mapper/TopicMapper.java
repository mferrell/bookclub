package com.mferrell.bookclub.service.mapper;


import com.mferrell.bookclub.domain.*;
import com.mferrell.bookclub.service.dto.TopicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Topic} and its DTO {@link TopicDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class, ChapterMapper.class})
public interface TopicMapper extends EntityMapper<TopicDTO, Topic> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "chapter.id", target = "chapterId")
    TopicDTO toDto(Topic topic);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "bookId", target = "book")
    @Mapping(source = "chapterId", target = "chapter")
    Topic toEntity(TopicDTO topicDTO);

    default Topic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Topic topic = new Topic();
        topic.setId(id);
        return topic;
    }
}
