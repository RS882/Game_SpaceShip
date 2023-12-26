package SpaceShip;

import GameObjects.Attacker;
import GameObjects.GameObject;
import Obstacles.Asteroid;
import Obstacles.Enemy;

public class SpaceShip extends GameObject implements Attacker {
    private String name;
    private double attackPower;

    private double points;

    private double energy;

    public SpaceShip(String name, double attackPower, double remainingStrength, double energy) {
        super(remainingStrength);
        this.attackPower = attackPower;
        this.name = name;
        this.energy = energy;
        this.points = 0;
    }


    public void setAttackPower(double attackPower) {
        this.attackPower = attackPower;
    }

    public double getAttackPower() {
        return this.attackPower;
    }

    public double getEnergy() {
        return this.energy;
    }

    public boolean isEnergy() {
        return this.energy > 0;
    }


    public void addStrength(double add) {
        this.setStrength(getStrength() + add);
    }

    public void addEnergy(double add) {
        this.energy += add;
    }

    public void reduceEnergy(double reduce) {
        this.energy -= reduce;
    }

    public void getWin(double time) {
        double timeBonus = 0;
        if (time < 20) timeBonus = 2000;
        else if (time < 30) timeBonus = 1000;
        else if (time < 40) timeBonus = 500;
        if (timeBonus != 0) System.out.printf("You nave time bonus <%.2f> points!%n", timeBonus);
        System.out.println("------------------");
        System.out.printf("You win! You score : %.2f%n", this.points + getStrength() * 5 + timeBonus);
        System.out.println("------------------");
    }

    @Override
    public void attack(GameObject target) {
        target.hurt(this.attackPower);
        if (target instanceof Asteroid && !target.isAlive()) {
            this.points += ((Asteroid) target).mine();
        } else if (target instanceof Enemy && !target.isAlive()) {
            this.points += 200;
        }
    }

    @Override
    public void destroy() {
        setStrength(0);
        this.energy=0;
        System.out.println("------------------");
        System.out.println("Your space ship is destroyed. You have lost.(");
        System.out.println("------------------");
    }

    @Override
    public void hurt(double amount) {
        double targetStrength = this.getStrength() - amount;
        this.setStrength(targetStrength);
        if (!isAlive()) {
            System.out.println("---------");
            this.destroy();
        }
    }

    @Override
    public String toString() {
        return String.format(
                "SpaceShip{ name=%s, attack=%.2f, %s, points=%.2f, energy=%.2f}%n",
                this.name, this.attackPower, super.toString(), this.points, this.energy);
    }
}

