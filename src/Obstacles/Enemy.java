package Obstacles;

import GameObjects.Attacker;
import GameObjects.GameObject;

public class Enemy extends Obstacle implements Attacker {
    private double attackPower;

    public Enemy(double remainingStrength) {
        super(remainingStrength);
    }

    @Override
    void encounter() {
        System.out.println("We have met the enemy!");
        System.out.printf("Attack power : %,2f.Strength : %,2f.%n",
                this.attackPower, this.getStrength());
    }

    @Override
    public void attack(GameObject target) {
        target.hurt(this.attackPower);
    }

    @Override
    public void destroy() {

        System.out.println("Enemy destroyed you got 200 points!");
    }

    @Override
    public void hurt(double amount) {
        super.hurt(amount);
        if (!isAlive()) destroy();
    }

}
