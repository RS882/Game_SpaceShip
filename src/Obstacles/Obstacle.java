package Obstacles;

import GameObjects.GameObject;

abstract public class Obstacle extends GameObject {

    public Obstacle(double remainingStrength) {
        super(remainingStrength);
    }

    abstract void encounter();
    @Override
    public void destroy() {

        this.setRemainingStrength(0);
    }

    @Override
    public void hurt(double amount) {
        double targetStrength = this.getRemainingStrength() - amount;
        this.setRemainingStrength(targetStrength);

    }
}
