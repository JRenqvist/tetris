package tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HighscoreList {
    private List<ScoreEntry> highscores = new ArrayList<>();

    // Main function
    public void addScore(ScoreEntry newScore) {

	try {
	    readHighscoresFromFile();
	} catch (final IOException e) {
	    JOptionPane.showMessageDialog(null, "Could not read file\n"+e.getMessage(),
					  "Error", JOptionPane.INFORMATION_MESSAGE);
	}

	if (highscores == null) {
	    highscores = new ArrayList<>();
	}

	highscores.add(newScore);

	// Sorts and reverses the list highscores
	highscores.sort(Comparator.comparingInt(ScoreEntry::getPoints));
	Collections.reverse(highscores);

	// If length of list > 3, remove last element
	if (highscores.size() > 3) highscores.remove(highscores.size() - 1);

	try {
	    saveHighscoresToFile();
	} catch (final IOException e) {
	    JOptionPane.showMessageDialog(null, "Could not write to file\n"+e.getMessage(),
					  "Error", JOptionPane.INFORMATION_MESSAGE);
	}
    }

    private void saveHighscoresToFile() throws FileNotFoundException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	Path currentDirectory = Paths.get("");

	String filePath = currentDirectory.toAbsolutePath() + File.separator + "highscores.json";

	try (PrintWriter writer = new PrintWriter(filePath)) {
	    // Write to json
	    String json = gson.toJson(highscores);
	    writer.println(json);
	} catch (IOException e) {
	    e.printStackTrace();
	}



    }

    public int getLowestScore() {
	if (highscores.isEmpty()) {
	    return 0; // Return a default value or handle accordingly for an empty list
	}

	// Read the JSON file and update the highscores list
	try {
	    readHighscoresFromFile();
	} catch (final IOException e) {
	    System.out.println("reads");
	    JOptionPane.showMessageDialog(null, "Could not read file\n"+e.getMessage(),
					  "Error", JOptionPane.INFORMATION_MESSAGE);
	}

	// Sort the list in ascending order
	highscores.sort(Comparator.comparingInt(ScoreEntry::getPoints));

	// Get the lowest score
	return highscores.get(0).getPoints();
    }

    private void readHighscoresFromFile() throws FileNotFoundException {
	// Reads the highscores from json file and puts them in highscores variable
	Gson gson = new Gson();
	Path currentDirectory = Paths.get("");
	String filePath = currentDirectory.toAbsolutePath() + File.separator + "highscores.json";

	try (FileReader reader = new FileReader(filePath)) {
	    Type type = new TypeToken<List<ScoreEntry>>(){}.getType();
	    highscores = gson.fromJson(reader, type);
	} catch (IOException e) {
	    e.printStackTrace();
	}



    }
}
