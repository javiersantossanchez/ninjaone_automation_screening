package org.velozient.model;

import lombok.Builder;
import lombok.Data;

/**
 * Business model for movie concept, Define all the properties handled for the movies
 *
 * @author Javier Santos
 */
@Data
@Builder
public class Movie {
    private String title;

    private int year;

    private String[] cast;

    private String[] genres;
}
