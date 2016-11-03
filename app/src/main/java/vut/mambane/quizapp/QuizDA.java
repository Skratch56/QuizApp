package vut.mambane.quizapp;

import java.util.ArrayList;

/**
 * Created by CE on 2016-10-10.
 */

public class QuizDA {
    private static ArrayList<Quiz> arQuiz;


    // Perform initialisation processing
    static void initialse() {
        arQuiz = new ArrayList<Quiz>();
        arQuiz.add(new Quiz("What is this place?", "Taj Mahal", "Gupta Mansion", "Holy Mosque", "Kuzco", "button", "tajmahal"));
        arQuiz.add(new Quiz("The Statue in the picture is found in Israel", "False", "Trues", "Cheese", "Kuzco", "radiobuttons", "rio"));
        arQuiz.add(new Quiz("Where is this place located?", "Rome", "Greece", "Athens", "Egypt", "button", "colosseum"));
        arQuiz.add(new Quiz("Select the name of the tribe that built this place and the name of the place", "Incas", "Machi Pichu", "Pompei", "Romans", "checkboxes", "machpichu"));
        arQuiz.add(new Quiz("What is this structure called?", "StoneHenge", "God's Pillars", "Stanenges", "Sarsen stones", "button", "stonehenge"));
        arQuiz.add(new Quiz("The Grand Canyon is located in Arizona", "True", "False", "Cheese", "Kuzco", "radiobuttons", "grandcanyon"));
        arQuiz.add(new Quiz("What is my name", "Thubalethu", "Mambane", "Cheese", "Kuzco", "checkboxes", "none"));
        arQuiz.add(new Quiz("What is this place called", "Niagra Falls", "Victoria Falls", "Gullfos", "Iguazu Falls", "button", "niagra"));

        arQuiz.add(new Quiz("Lizards are also part of which subgroup", "Reptiles", "Amphibians", "Plants", "Insects", "button", "lizards"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "radiobuttons", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));

        arQuiz.add(new Quiz("Lizards are also part of which subgroup", "Reptiles", "Amphibians", "Plants", "Insects", "button", "lizards"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
        arQuiz.add(new Quiz("This animal is the fastest land animal", "True", "Amphibians", "Plants", "Insects", "button", "cheetah"));
    }

    static ArrayList<Quiz> getAll() {
        return arQuiz;  //return a shallow copy of the data
    }

}
