package Obstacles;

import GameObjects.GameObject;

abstract public class Obstacle extends GameObject {

    public Obstacle(double remainingStrength) {
        super(remainingStrength);
    }

    abstract void encounter();
}
