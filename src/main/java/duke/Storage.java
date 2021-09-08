package duke;

import duke.exceptions.DukeException;
import duke.exceptions.LoadingFileError;
import duke.exceptions.EmptyFileError;
import duke.exceptions.SaveDirectoryError;
import duke.exceptions.SaveFileError;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.util.regex.Pattern;


/**
 * Allows reading and saving of tasks to a given file.
 * @author Ruth Poh
 */
public class Storage {
    private String filePath;

    /**
     * Constructor for DataEditor
     * @param filePath
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads txt file from path given and returns an ArrayList of the tasks inside.
     * @return ArrayList of tasks.
     * @throws DukeException Exception if any when reading file.
     */
    public TaskList loadData() throws DukeException {
        String homeDir = System.getProperty("user.dir");
        Path path = Paths.get(homeDir, this.filePath);
        boolean directoryExists = Files.exists(path);

        if (directoryExists) {
            try {
                TaskList tasklist = new TaskList();
                List<String> lines = Files.readAllLines(path);

                for (String str : lines) {
                    String[] strparse = str.split(Pattern.quote(" | "));
                    if (strparse.length < 3 || strparse.length > 5) {
                        continue;
                        // incorrect task listed for some reason
                    } else if (strparse[0].equals("T")) {
                        tasklist.addReadTodo(strparse[2], Integer.parseInt(strparse[1]));
                    } else if (strparse[0].equals("D")) {
                        if (strparse.length < 4) {
                            tasklist.addReadDeadline(strparse[2],
                                    Integer.parseInt(strparse[1]), strparse[3], null);
                        } else {
                            tasklist.addReadDeadline(strparse[2],
                                    Integer.parseInt(strparse[1]), strparse[3], strparse[4]);
                        }
                    } else if (strparse[0].equals("E")) {
                        if (strparse.length < 4) {
                            tasklist.addReadEvent(strparse[2],
                                    Integer.parseInt(strparse[1]), strparse[3], null);
                        } else {
                            tasklist.addReadEvent(strparse[2],
                                    Integer.parseInt(strparse[1]), strparse[3], strparse[4]);
                        }                    }
                }
                return tasklist;
            } catch (DukeException e) {
                throw e;
            } catch (Exception e) {
                throw new LoadingFileError();
            }
        } else {
            throw new EmptyFileError();
        }
    }

    /**
     * Saves tasklist in String form to txt file. If file does not exist, one is created.
     * @param tasklist tasklist to be converted into txt file, then saved.
     * @throws DukeException Exception if any when saving file.
     */
    public void saveData(TaskList tasklist) throws DukeException {
        String textToSave = tasklist.saveAsString();
        String homeDir = System.getProperty("user.dir");
        Path path = Paths.get(homeDir, this.filePath);
        boolean directoryExists = Files.exists(path);

        if (!directoryExists) {
            try {
                Files.createDirectories(path.getParent());
            } catch(IOException e){
                throw new SaveDirectoryError();
            }
        }

        try {
            Files.write(Paths.get(homeDir, this.filePath), textToSave.getBytes());
        } catch (IOException e){
            throw new SaveFileError();
        }

    }
}
