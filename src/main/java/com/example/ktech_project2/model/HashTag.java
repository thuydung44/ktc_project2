package com.example.ktech_project2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
@Getter
@Entity
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter String tag;

    @OneToMany(mappedBy = "hashTag")
    private List<ArticleHashTag> articleHasTag;

    public HashTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashTag hashTag = (HashTag) o;
        return Objects.equals(id, hashTag.id) && Objects.equals(tag, hashTag.tag) && Objects.equals(tag, hashTag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tag);
    }
}
