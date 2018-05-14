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

import equeue.CDI;
import equeue.NodeControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 *
 * @author skrymets
 */
@ShellComponent
@Profile(CDI.Profiles.SHELL)
public class Quit implements org.springframework.shell.standard.commands.Quit.Command {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public interface Command {
    }

    @ShellMethod(value = "Exit the shell.", key = {"quit", "exit"})
    public void quit() {
        eventPublisher.publishEvent(new NodeControl.ShutdownNodeEvent(this));

        throw new ExitRequest();
    }

}
