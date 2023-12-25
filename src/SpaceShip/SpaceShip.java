package SpaceShip;

import GameObjects.Attacker;
import GameObjects.GameObject;

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
        double targetStrength = target.getRemainingStrength();
        target.setRemainingStrength(targetStrength - this.attackPower);
    }
}

//        Разработать класс SpaceShip.SpaceShip (космический корабль).
//        Добавить ему поле "Название" и "Сила атаки". Этот класс должен
//        реализовывать интерфейс Attacker.
//        В методе attack просто повредить target на значение силы атаки.
//        Добавить в этот класс поле "Количество очков", в конструкторе
//        инициализировать его нулем.