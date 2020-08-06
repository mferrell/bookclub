package com.mferrell.bookclub.service.mapper;


import com.mferrell.bookclub.domain.*;
import com.mferrell.bookclub.service.dto.BookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BookMapper extends EntityMapper<BookDTO, Book> {

    @Mapping(source = "user.id", target = "userId")
    BookDTO toDto(Book book);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "topics", ignore = true)
    @Mapping(target = "removeTopic", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "removeChapter", ignore = true)
    Book toEntity(BookDTO bookDTO);

    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
