package com.narxoz.rpg.vault;

import com.narxoz.rpg.artifact.Artifact;
import com.narxoz.rpg.artifact.Armor;
import com.narxoz.rpg.artifact.Inventory;
import com.narxoz.rpg.artifact.Potion;
import com.narxoz.rpg.artifact.Ring;
import com.narxoz.rpg.artifact.Scroll;
import com.narxoz.rpg.artifact.Weapon;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.memento.Caretaker;
import com.narxoz.rpg.visitor.CurseDetector;
import com.narxoz.rpg.visitor.EnchantmentScanner;
import com.narxoz.rpg.visitor.GoldAppraiser;
import com.narxoz.rpg.visitor.WeightCalculator;

import java.util.List;

/**
 * Orchestrates the Chronomancer's Vault demo run.
 */
public class ChronomancerEngine {
    private int artifactsAppraised = 0;
    private int mementosCreated = 0;
    private int restoredCount = 0;

    /**
     * Runs the vault sequence for the supplied party.
     *
     * @param party the heroes entering the vault
     * @return a result summarizing the vault run
     */
    public VaultRunResult runVault(List<Hero> party) {
        System.out.println("\nENTERING CHRONOMANCER'S VAULT\n");

        for (Hero hero : party) {
            System.out.println("Hero: " + hero.getName());
            System.out.println("  HP: " + hero.getHp() + ", Mana: " + hero.getMana() + ", Gold: " + hero.getGold());

            Inventory inventory = new Inventory();
            inventory.addArtifact(new Weapon("Dragon Slayer", 500, 8, 15));
            inventory.addArtifact(new Potion("Health Elixir", 50, 1, 40));
            inventory.addArtifact(new Scroll("Teleport Scroll", 100, 1, "Teleport"));
            inventory.addArtifact(new Ring("Ring of Power", 200, 1, 5));
            inventory.addArtifact(new Armor("Mithril Chestplate", 300, 10, 12));
            hero.setInventory(inventory);

            System.out.println("\n Appraising artifacts");

            GoldAppraiser goldAppraiser = new GoldAppraiser();
            inventory.accept(goldAppraiser);
            artifactsAppraised += inventory.size();
            System.out.println("    Total gold value: " + goldAppraiser.getTotalValue());

            WeightCalculator weightCalc = new WeightCalculator();
            inventory.accept(weightCalc);
            System.out.println("    Total weight: " + weightCalc.getTotalWeight() + " kg");

            EnchantmentScanner scanner = new EnchantmentScanner();
            inventory.accept(scanner);

            CurseDetector curseDetector = new CurseDetector();
            inventory.accept(curseDetector);
            if (curseDetector.getCursedCount() > 0) {
                System.out.println("    Found " + curseDetector.getCursedCount() + " cursed items!");
            }

            System.out.println("\n Time Crystal Experiment");
            Caretaker caretaker = new Caretaker();

            com.narxoz.rpg.combatant.HeroMemento snapshot = hero.createMemento();
            caretaker.save(snapshot);
            mementosCreated++;
            System.out.println("    Snapshot saved! Size: " + caretaker.size());

            System.out.println("    A trap hits " + hero.getName() + "!");
            hero.takeDamage(30);
            hero.spendGold(50);
            System.out.println("    After trap: HP=" + hero.getHp() + ", Gold=" + hero.getGold());

            com.narxoz.rpg.combatant.HeroMemento peek = caretaker.peek();
            System.out.println("    Peek at snapshot - can see " + (peek != null ? "saved state exists" : "nothing"));

            com.narxoz.rpg.combatant.HeroMemento restored = caretaker.undo();
            if (restored != null) {
                hero.restoreFromMemento(restored);
                restoredCount++;
                System.out.println("    Rewinding time... HP restored to " + hero.getHp() + ", Gold restored to " + hero.getGold());
            }
            System.out.println("    Final: HP=" + hero.getHp() + ", Mana=" + hero.getMana() + ", Gold=" + hero.getGold());
            System.out.println();
        }

        return new VaultRunResult(artifactsAppraised, mementosCreated, restoredCount);
    }
}