package com.mferrell.bookclub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chapter.
 */
@Entity
@Table(name = "chapter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "chapter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "chapter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Topic> topics = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "chapters", allowSetters = true)
    private Book book;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Chapter title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public Chapter user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Chapter comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Chapter addComment(Comment comment) {
        this.comments.add(comment);
        comment.setChapter(this);
        return this;
    }

    public Chapter removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setChapter(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public Chapter topics(Set<Topic> topics) {
        this.topics = topics;
        return this;
    }

    public Chapter addTopic(Topic topic) {
        this.topics.add(topic);
        topic.setChapter(this);
        return this;
    }

    public Chapter removeTopic(Topic topic) {
        this.topics.remove(topic);
        topic.setChapter(null);
        return this;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Book getBook() {
        return book;
    }

    public Chapter book(Book book) {
        this.book = book;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chapter)) {
            return false;
        }
        return id != null && id.equals(((Chapter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chapter{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
