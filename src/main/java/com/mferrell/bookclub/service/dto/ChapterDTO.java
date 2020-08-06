package com.mferrell.bookclub.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mferrell.bookclub.domain.Chapter} entity.
 */
public class ChapterDTO implements Serializable {
    
    private Long id;

    private String title;


    private Long userId;

    private Long bookId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChapterDTO)) {
            return false;
        }

        return id != null && id.equals(((ChapterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChapterDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", userId=" + getUserId() +
            ", bookId=" + getBookId() +
            "}";
    }
}
