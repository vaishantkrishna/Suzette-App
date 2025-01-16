package com.example.suzette.sqlrepository;

import java.util.List;

/***
 * Interface for SQLRepository class that will query database. Pertaining to Epic 1, methods query
 * for Recipe and Recipe details only
 */
public interface iSQLRepository {

    /***
     * Retrieves all recipe names and ids from the database.
     * Creates a connection to the database and disconnects after query has been resolved.
     *
     * Return: List of Recipe objects.
     */
//    public List<Recipe> getAllRecipeNames ();

    /***
     * Retrieves a recipe's description, tags and genre based on the recipe ID passed.
     * Creates a connection to the database and disconnects after query has been resolved.
     *
     * Param: recipeId : ID of recipe to fetch extra details for.
     * Return: Recipe object with added detail.
     */
//    public Recipe getRecipeDetailsById (int recipeId);

}
