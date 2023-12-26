package Obstacles;

import GameObjects.Attacker;
import GameObjects.GameObject;

public class Enemy extends Obstacle implements Attacker {
    private double attackPower;

    public Enemy(double remainingStrength, double attackPower) {
        super(remainingStrength);
        this.attackPower = attackPower;
    }

    @Override
    public void encounter() {
        System.out.println("=============");
        System.out.printf("We have met the enemy! Attack power : <%.2f>.Strength : <%.2f>.%n",
                this.attackPower, this.getStrength());
        System.out.println("=============");
    }

    @Override
    public void attack(GameObject target) {
        target.hurt(this.attackPower);
    }

    @Override
    public void destroy() {
        setStrength(0);
        System.out.println("------------------");
        System.out.println("Enemy destroyed you got 200 points!");
        System.out.println("------------------");
    }

    @Override
    public void hurt(double amount) {
        super.hurt(amount);
        if (!isAlive()) this.destroy();
    }

    @Override
    public String toString() {
        return String.format("Enemy{attack=%.2f, %s}%n", this.attackPower, super.toString());
    }
}
