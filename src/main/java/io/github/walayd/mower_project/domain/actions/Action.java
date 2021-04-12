package io.github.walayd.mower_project.domain.actions;

// Command pattern used to encapsulate in an object all the data required for performing a given action
// This model allows us to decouple objects that produce the commands from their consumers

public interface Action {
    void execute();

    String toString();
}
