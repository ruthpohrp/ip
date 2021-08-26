import java.lang.reflect.Array;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Modified version of Duke (Personal Assistant Chatbot). Speaks owo language.
 * @author Ruth Poh (Lab 10H)
 */

public class Duke {

    public static void main(String[] args) throws DukeException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String linebreakstart = "~~*********___\\(owo)/___\\(owo)/___*********~~\n";
        String linebreakend = "~~*********___\\(owo)/___\\(owo)/___*********~~";

        System.out.println(linebreakstart
                + "\nHewwo!! From:\n"
                + logo
                + "How mayw Iw hewlp youw, Mastwer? Pwease type 'help' for a list of commandws uwu\n");

        Scanner in = new Scanner(System.in);

        DataEditor dataEditor = new DataEditor("data/tasklist.txt");
        TaskList tasklist;
        try {
            tasklist = dataEditor.loadData();
            System.out.println("Loadiwng file for you. . . Loaded!\n");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            tasklist = new TaskList(); // creates new tasklist if one cannot be found.
        }

        System.out.println(linebreakend);

        boolean continueLoop = true;
        // for checking loop

        while (continueLoop) {
            // initiates loop.
            String str = in.nextLine();
            String[] strparse = str.split(" ");
            // splits input to parse for keywords.

            if (strparse[0].equalsIgnoreCase("bye")) {
                // breaks loop, closes chatbot.
                try {
                    if (strparse.length > 1) {
                        throw new IncorrectInputException("bye", "'bye'");
                    }
                    continueLoop = false;
                } catch (DukeException e) {
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }
            } else if (strparse[0].equalsIgnoreCase("help")) {
                // lists command list
                try {
                    if (strparse.length > 1) {
                        throw new IncorrectInputException("help", "'help'");
                    }
                    System.out.println(linebreakstart);
                    System.out.println("Commandws supported:\n");
                    System.out.println("- bye\n- help\n- list\n- todo\n- event\n- deadline\n");
                    System.out.println(linebreakend);
                } catch (DukeException e) {
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }


            } else if (strparse[0].equalsIgnoreCase("list")) {
                // lists history of current tasks.
                try {
                    if (strparse.length > 1) {
                        throw new IncorrectInputException("list", "'list'");
                    }
                    System.out.println(linebreakstart);
                    System.out.println("Uwu! Herw arw yourw taskws:\n");

                    if (tasklist.getTaskCounter() == 0) {
                        System.out.println("Itw seewsm like youw wist is emptwy! Congwats!\n");
                    } else {
                        System.out.println(tasklist.displayList());
                    }
                    System.out.println(linebreakend);
                } catch (DukeException e) {
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }
            } else if (strparse[0].equalsIgnoreCase("todo")) {
                // adds a todo task to the list.
                try {
                    tasklist.addTodo(strparse);
                    dataEditor.saveData(tasklist);

                    System.out.println(linebreakstart);
                    System.out.println("Uwu! Addewd yourw taskws:\n");
                    System.out.println(tasklist.lastAddedTask() + '\n');
                    System.out.println("Youw noww havew "
                            + (tasklist.getTaskCounter())
                            + " taskw(s) inw thew wist! uwu\n");
                    System.out.println(linebreakend);
                } catch (DukeException e) {
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }
            } else if (strparse[0].equalsIgnoreCase("deadline")) {
                // adds a deadline task to the list.
                try {
                    tasklist.addDeadline(strparse);
                    dataEditor.saveData(tasklist);

                    System.out.println(linebreakstart);
                    System.out.println("Uwu! Addewd yourw taskws:\n");
                    System.out.println(tasklist.lastAddedTask() + '\n');
                    System.out.println("Youw noww havew "
                            + (tasklist.getTaskCounter())
                            + " taskw(s) inw thew wist! uwu\n");
                    System.out.println(linebreakend);
                } catch (DukeException e) {
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }
            } else if (strparse[0].equalsIgnoreCase("event")) {
                // adds an event to the list. pretty much like deadline.
                try {
                    tasklist.addEvent(strparse);
                    dataEditor.saveData(tasklist);

                    System.out.println(linebreakstart);
                    System.out.println("Uwu! Addewd yourw taskws:\n");
                    System.out.println(tasklist.lastAddedTask() + '\n');
                    System.out.println("Youw noww havew "
                            + (tasklist.getTaskCounter())
                            + " taskw(s) inw thew wist! uwu\n");
                    System.out.println(linebreakend);
                } catch (DukeException e){
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }
            } else if (strparse[0].equalsIgnoreCase("done")) {
                // marks a task as done.
                try {
                    int i = tasklist.markDone(strparse);
                    System.out.println("Task done is " + i);
                    dataEditor.saveData(tasklist);

                    System.out.println(linebreakstart);
                    System.out.println("Thanwk youw forw youwr serwwice! Thwis taskw isw downe:\n");
                    System.out.println("   "
                            + tasklist.getTaskDescr(i)
                            + '\n');
                    System.out.println(linebreakend);

                } catch (DukeException e) {
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }
            } else if (strparse[0].equalsIgnoreCase("delete")) {
                // deletes corresponding task on list.
                try {
                    Task t = tasklist.delete(strparse);
                    dataEditor.saveData(tasklist);

                    System.out.println(linebreakstart);
                    System.out.println("Thanwk youw forw youwr serwwice! Thwis taskw hasw beenw deweted:\n");
                    System.out.println("   "
                            + t.toString()
                            + '\n');
                    System.out.println("Youw noww havew "
                            + (tasklist.getTaskCounter())
                            + " taskw(s) inw thew wist! uwu\n");
                    System.out.println(linebreakend);
                } catch (DukeException e) {
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }
            } else {
                try {
                    throw new InvalidInputException();
                } catch (DukeException e) {
                    System.out.println(linebreakstart);
                    System.out.println(e.getMessage());
                    System.out.println(linebreakend);
                }
            }
        }
        System.out.println(linebreakstart
                + "\nGoodbywe, Mastwer! Seew youw soown!\n\n"
                + linebreakend);
        // closing lines
    }
}