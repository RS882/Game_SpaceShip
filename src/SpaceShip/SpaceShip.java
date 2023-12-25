package SpaceShip;

import GameObjects.Attacker;
import GameObjects.GameObject;
import Obstacles.Asteroid;
import Obstacles.Enemy;

public class SpaceShip extends GameObject implements Attacker {
    private String name;
    private double attackPower;

    private double points;

    public SpaceShip(String name, double attackPower, double remainingStrength) {
        super(remainingStrength);
        this.attackPower = attackPower;
        this.name = name;
        this.points = 0;

    }


    public void setAttackPower(double attackPower) {
        this.attackPower = attackPower;
    }

    public double getAttackPower() {
        return attackPower;
    }

    public String getName() {
        return name;
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
        System.out.println("Your space ship is destroyed. You have lost.(");
    }

    @Override
    public void hurt(double amount) {
        double targetStrength = this.getStrength() - amount;
        this.setStrength(targetStrength);
        if (!isAlive()) destroy();
    }

    @Override
    public String toString() {
        return String.format(
                "SpaceShip{ name=%s, attackPower=%.2f, %s, points=%.2f}%n",
                this.name, this.attackPower, super.toString(), this.points);


    }
}

