package proglab;

import proglab.application.CollectionManager;
import proglab.application.IDGenerator;
import proglab.application.IncrementalIDGenerator;
import proglab.controller.Runner;
import proglab.controller.commands.AddCommand;
import proglab.controller.commands.AddIfMaxCommand;
import proglab.controller.commands.AddIfMinCommand;
import proglab.controller.commands.AverageOfEmployeesCountCommand;
import proglab.controller.commands.ClearCommand;
import proglab.controller.commands.EmptyCommand;
import proglab.controller.commands.ExecuteScriptCommand;
import proglab.controller.commands.ExitCommand;
import proglab.controller.commands.FilterStartsWithFullNameCommand;
import proglab.controller.commands.GeneralCommandParser;
import proglab.controller.commands.HelpCommand;
import proglab.controller.commands.InfoCommand;
import proglab.controller.commands.PrintFieldDescendingAnnualTurnoverCommand;
import proglab.controller.commands.RemoveByIDCommand;
import proglab.controller.commands.RemoveLowerCommand;
import proglab.controller.commands.SaveCommand;
import proglab.controller.commands.ShowCommand;
import proglab.controller.commands.UpdateCommand;
import proglab.exceptions.RepositoryAccessDeniedException;
import proglab.exceptions.RepositoryDataCorruptedException;
import proglab.repository.xml.XmlRepository;
import proglab.view.View;
import proglab.view.cli.Cli;

public class Main {
    public static void main(String[] args) {
        View view = new Cli();

        String repoPath = System.getenv("REPO_PATH");
        if (repoPath == null) {
            view.error("Файл репозитория не найден."
                + " Необходимо установить переменную окружения `REPO_PATH`.");

            return;
        }

        IDGenerator idGen = new IncrementalIDGenerator();
        XmlRepository repo = new XmlRepository(repoPath);
        CollectionManager collection;
        try {
            collection = new CollectionManager(idGen, repo);
        } catch (RepositoryAccessDeniedException | RepositoryDataCorruptedException e) {
            view.error(e.getMessage());

            return;
        }

        GeneralCommandParser cmdParser = new GeneralCommandParser();
        Runner runner = new Runner(view, collection, cmdParser);

        cmdParser.register("", new EmptyCommand.Parser());
        cmdParser.register("help", new HelpCommand.Parser());
        cmdParser.register("info", new InfoCommand.Parser());
        cmdParser.register("show", new ShowCommand.Parser());
        cmdParser.register("add", new AddCommand.Parser());
        cmdParser.register("update", new UpdateCommand.Parser());
        cmdParser.register("remove_by_id", new RemoveByIDCommand.Parser());
        cmdParser.register("clear", new ClearCommand.Parser());
        cmdParser.register("save", new SaveCommand.Parser());
        cmdParser.register("execute_script", new ExecuteScriptCommand.Parser());
        cmdParser.register("exit", new ExitCommand.Parser());
        cmdParser.register("add_if_max", new AddIfMaxCommand.Parser());
        cmdParser.register("add_if_min", new AddIfMinCommand.Parser());
        cmdParser.register("remove_lower", new RemoveLowerCommand.Parser());
        cmdParser.register("average_of_employees_count",
            new AverageOfEmployeesCountCommand.Parser());
        cmdParser.register("filter_starts_with_full_name",
            new FilterStartsWithFullNameCommand.Parser());
        cmdParser.register("print_field_descending_annual_turnover",
            new PrintFieldDescendingAnnualTurnoverCommand.Parser());

        runner.run();
    }
}