package sc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The implementation of a file reader.
 * 
 * @author Jan Boockmann
 * 
 */
public class FileReaderHelper {

	/**
	 * Reads a file and returns an array containing all the lines from the file.
	 * The element at the n-th position in the array is equal to the n-th line
	 * of the file.
	 * 
	 * @param path
	 *            the path pointing to the file to be read in
	 * @return an string array with a string for each line
	 */
	public static List<String> readFileToStringList(String path) throws IOException {
		return Files.readAllLines(Paths.get(path));
	}

}
