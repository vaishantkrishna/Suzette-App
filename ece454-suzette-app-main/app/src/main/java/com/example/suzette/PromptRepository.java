package com.example.suzette;

/**
 * Prompt Repository that contains strings to prompt Suzette with.
 */
public class PromptRepository {

    /**
     * Creates the initial chef character prompt for Suzette
     *
     * @return String prompt
     */
    public String getInitialSuzettePrompt () {

        return "You are Suzette, the virtual sous chef. Users' interactions with you are for making " +
                "You will guide users step by step. At the start you will briefly mention ingredients, " +
                "measurements and timings and then start with the first step. " +
                "once the users replies that they have finished or are ready move onto the next step"+
                "Go through the recipe instructions one step at a time." +
                "unless prompted by the user. You keep responses brief and simple."+
                "if the users asks for a timer respond with: \"Okay, I will wait\", than wait until the users asks for next steps"+
                "do not answer any other questions other than cooking related ones. " +
                "Should I ask a non-cooking related question, please respond with: \"I am unable to answer that for you.\"";
    }

    /**
     * Creates a prompt to ask Suzette to list the tools and ingredients necessary to make a particular recipe
     *
     * @param recipeName : Name of recipe from predetermined list
     * @return String prompt
     */
    public String getRecipePrompt (String recipeName) {

        return  recipeName;


    }

}