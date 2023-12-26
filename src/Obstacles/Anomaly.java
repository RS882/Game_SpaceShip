package Obstacles;

import GameObjects.Attacker;
import GameObjects.GameObject;

public class Anomaly extends Obstacle implements Attacker {

    private double damage;
    public Anomaly(double damage) {
        super(Double.MAX_VALUE);
      this.damage =damage;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void encounter() {
        System.out.printf("Danger! You have encountered an anomaly! You took <%.2f> damage.%n",this.damage);
    }

    @Override
    public void hurt(double amount) {

    }

    @Override
    public void setStrength(double strength) {
        super.setStrength(Double.MAX_VALUE);
    }

    @Override
    public void attack(GameObject target) {
        target.hurt(this.damage);
    }
    @Override
    public String toString() {
        return String.format("Anomaly{damage=%.2f}%n", this.damage);
    }
}
