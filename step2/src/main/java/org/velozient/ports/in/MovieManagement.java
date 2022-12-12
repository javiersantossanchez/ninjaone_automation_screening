package org.velozient.ports.in;

import org.velozient.filter.Decade;

/**
 * Service interface definition to expose all the user cases available to the application
 *
 * @author Javier Santos
 */
public interface MovieManagement {

    /**
     * Search all the movies created into a specific decade and exporting the result
     * Feature: <b>Filter movies by decade</b>
     * @param decade - decade use to filter the movies
     */
    void exportMoviesByDecade(int century, Decade decade);
}
