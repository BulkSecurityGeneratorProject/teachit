package com.teachit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ContentView.
 */
@Entity
@Table(name = "content_view")
public class ContentView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "view")
    private Boolean view;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Content content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isView() {
        return view;
    }

    public void setView(Boolean view) {
        this.view = view;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentView contentView = (ContentView) o;
        if(contentView.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contentView.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ContentView{" +
            "id=" + id +
            ", view='" + view + "'" +
            '}';
    }
}
