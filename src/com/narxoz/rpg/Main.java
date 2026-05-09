package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.vault.ChronomancerEngine;
import com.narxoz.rpg.vault.VaultRunResult;

import java.util.List;

/**
 * Entry point for Homework 9 — Chronomancer's Vault: Visitor + Memento.
 *
 * The scaffold prints the banner only; students fill in the vault demo.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 9 Demo: Visitor + Memento ===\n");

        // 1. Create at least 2 heroes with different starting states.
        Hero warrior = new Hero("Grom", 150, 0, 25, 12, 200, null);
        Hero mage = new Hero("Elara", 100, 50, 20, 8, 150, null);
        List<Hero> party = List.of(warrior, mage);

        System.out.println("--- Heroes Enter the Vault ---");
        for (Hero h : party) {
            System.out.println(h.getName() + ": HP=" + h.getHp() + ", Mana=" + h.getMana() + ", Gold=" + h.getGold());
        }

        // 2. Build an artifact inventory and exercise the visitor interface.
        // 3. Capture a hero snapshot through the memento workflow.
        // 4. Rewind the hero after a vault trap changes state.
        // 5. Run the ChronomancerEngine demo sequence.
        ChronomancerEngine engine = new ChronomancerEngine();
        VaultRunResult result = engine.runVault(party);

        // 6. Print a final VaultRunResult summary.
        System.out.println("\n=== VAULT RUN RESULT ===");
        System.out.println("Artifacts appraised: " + result.getArtifactsAppraised());
        System.out.println("Mementos created: " + result.getMementosCreated());
        System.out.println("Restorations performed: " + result.getRestoredCount());

        System.out.println("\n=== Demo Complete ===");
    }
}
