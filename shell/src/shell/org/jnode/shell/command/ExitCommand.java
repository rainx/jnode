package org.jnode.shell.command;

import java.io.InputStream;
import java.io.PrintStream;

import org.jnode.naming.InitialNaming;
import org.jnode.shell.AbstractCommand;
import org.jnode.shell.CommandLine;
import org.jnode.shell.CommandShell;
import org.jnode.shell.ShellManager;

/**
 * @author Levente S\u00e1ntha
 */
public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("Exit the current shell");
    }

    public static void main(String[] args) throws Exception {
        new ExitCommand().execute(args);
    }

    /**
     * Execute this command
     */
    public void execute(CommandLine cmdLine,
                        InputStream in, PrintStream out, PrintStream err) throws Exception {
        ShellManager sm = InitialNaming.lookup(ShellManager.NAME);
        ((CommandShell) sm.getCurrentShell()).exit();
    }
}
