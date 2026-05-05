package com.narxoz.rpg.memento;

import com.narxoz.rpg.combatant.HeroMemento;
import java.util.Stack;

/**
 * Stores hero snapshots for the Chronomancer's Vault rewind mechanic.
 *
 * This class intentionally sits in a different package from {@link HeroMemento}
 * so it can only treat mementos as opaque values.
 */
public class Caretaker {
    private final Stack<HeroMemento> history = new Stack<>();

    /**
     * Saves a snapshot to the caretaker history.
     *
     * @param memento the snapshot to store
     */
    public void save(HeroMemento memento) {
        if (memento != null) {
            history.push(memento);
        }
    }

    /**
     * Removes and returns the most recent snapshot.
     *
     * @return the latest stored snapshot, or null if none
     */
    public HeroMemento undo() {
        if (history.isEmpty()) {
            return null;
        }
        return history.pop();
    }

    /**
     * Returns the most recent snapshot without removing it.
     *
     * @return the latest stored snapshot, or null if none
     */
    public HeroMemento peek() {
        if (history.isEmpty()) {
            return null;
        }
        return history.peek();
    }

    /**
     * Reports how many snapshots are stored.
     *
     * @return the number of saved snapshots
     */
    public int size() {
        return history.size();
    }
}