package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.exceptions.DukeException;
import duke.exceptions.IncorrectInputException;

public class ClearallRejectCommand extends Command {
    public ClearallRejectCommand(Storage storage, TaskList taskList, String[] strParse) {
        super(storage, taskList, strParse);
    }

    @Override
    public String execute() {
            return ("Alright. Pwease continue on!");
    }
}
