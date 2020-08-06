package com.mferrell.bookclub.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "image")
    private String image;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Chapter> chapters = new HashSet<>();

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

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public Book url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public Book image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public Book user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public Book topics(Set<Topic> topics) {
        this.topics = topics;
        return this;
    }

    public Book addTopic(Topic topic) {
        this.topics.add(topic);
        topic.setBook(this);
        return this;
    }

    public Book removeTopic(Topic topic) {
        this.topics.remove(topic);
        topic.setBook(null);
        return this;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public Book chapters(Set<Chapter> chapters) {
        this.chapters = chapters;
        return this;
    }

    public Book addChapter(Chapter chapter) {
        this.chapters.add(chapter);
        chapter.setBook(this);
        return this;
    }

    public Book removeChapter(Chapter chapter) {
        this.chapters.remove(chapter);
        chapter.setBook(null);
        return this;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", url='" + getUrl() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
