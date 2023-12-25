package SpaceShip;

import GameObjects.Attacker;
import GameObjects.GameObject;
import Obstacles.Asteroid;

public class SpaceShip implements Attacker {
    private String name;
    private double attackPower;

    private  double points;


    public SpaceShip(String name) {
        this.name = name;
        this.points=0;
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
      target.hurt( this.attackPower);
      if(target instanceof Asteroid && !target.isAlive()) {
          this.points = ((Asteroid) target).mine();
      }
    }
}

