package br.com.layon.movielist.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movielist")
@Entity
public class Movie {
    @Column(name = "year")
    Integer year;
    @Column(name = "title")
    String title;
    @Column(name = "studios")
    String studios;
    @Column(name = "producers")
    String producers;
    @Column(name = "winner", nullable = true)
    Boolean winner;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Override
    public String toString() {
        return "Movie{" +
                "year=" + year +
                ", title='" + title + '\'' +
                ", studios='" + studios + '\'' +
                ", producers='" + producers + '\'' +
                ", winner=" + winner +
                '}';
    }
}
