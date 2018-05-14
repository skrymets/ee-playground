/*
 * Copyright 2018 skrymets.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package equeue.shell;

import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import equeue.CDI;
import equeue.NodeControl;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@Profile(CDI.Profiles.SHELL)
public class NodeShell implements PromptProvider {

    @Autowired
    NodeControl nodeControl;

    private static final String CMD_HELP_STATUS = "Show current node status";
    private static final String CMD_OFFLINE = "Turn the node into offline mode";
    private static final String CMD_ONLINE = "Connect the node to a cluster";

    @ShellMethod(CMD_HELP_STATUS)
    public String status() {
        return "Node is " + ((nodeControl.isOnline()) ? "online" : "offline");
    }

    @ShellMethod(CMD_ONLINE)
    @ShellMethodAvailability("goOnlineAviability")
    public void goOnline(@ShellOption(value = {"-c", "--cluster"}, defaultValue = "equeue-cluster") String clusterName) {
        nodeControl.play(clusterName);
    }

    public Availability goOnlineAviability() {
        return !nodeControl.isOnline()
                ? Availability.available()
                : Availability.unavailable("The node is alredy online at this moment");
    }

    @ShellMethod(CMD_OFFLINE)
    @ShellMethodAvailability("goOfflineAviability")
    public void goOffline() {
        nodeControl.pause();
    }

    public Availability goOfflineAviability() {
        return nodeControl.isOnline()
                ? Availability.available()
                : Availability.unavailable("The node is offline at this moment");
    }

    @Override
    public AttributedString getPrompt() {
        return (nodeControl.isOnline())
                ? new AttributedString(
                        "@" + nodeControl.getClusterName() + ":>",
                        AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold()
                )
                : new AttributedString(
                        "offline:>",
                        AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).boldOff()
                );

    }

}
