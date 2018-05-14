package equeue;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

@SpringBootApplication
public class EqueueApplication {

    private static final Logger LOG = LoggerFactory.getLogger(EqueueApplication.class);

    public static void main(String[] args) {

        Option shellOption = Option.builder("shell").desc("Run interactive shell").build();
        Options options = new Options().addOption(shellOption);

        CommandLine cmd;
        try {
            cmd = new DefaultParser().parse(options, args);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return;
        }

        SpringApplication app = new SpringApplication(EqueueApplication.class);

        Map<String, Object> properties = new HashMap<>();

        final boolean runShell = cmd.hasOption(shellOption.getOpt());
        properties.put(InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED, runShell);
        properties.put(ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED, runShell);
        if (runShell) {
            app.setAdditionalProfiles(CDI.Profiles.SHELL);
        }

        app.setDefaultProperties(properties);
        app.run(args);
    }
}
