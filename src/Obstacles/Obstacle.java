package Obstacles;

import GameObjects.GameObject;

abstract public class Obstacle extends GameObject {

    public Obstacle(double remainingStrength) {
        super(remainingStrength);
    }

    abstract void encounter();


    @Override
    public void hurt(double amount) {
        double targetStrength = this.getStrength() - amount;
        this.setStrength(targetStrength);
    }
}
