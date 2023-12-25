package Obstacles;

public class Asteroid extends Obstacle {
    private double value;

    public Asteroid(double remainingStrength, double value) {
        super(remainingStrength);
        this.value = value;
    }

    public double mine() {
        destroy();
        System.out.printf("Asteroid mined, (%,.2f) points gained!", this.value);
        return this.value;

    }

    @Override
    void encounter() {
        System.out.println("You have found an asteroid with resources!");
    }

    @Override
    public void destroy() {
        super.destroy();
        this.value=0;
    }
}

//   В класс астероид добавить поле "ценность" и метод "добыть", который
//        вернет ценность этого астероида и вызовет
//        destroy у этого астероида.
//        При встрече с астероидом должно быть выведено сообщение "Вы нашли
//        астероид с ресурсами!"
//        При разрушении астероида, должно выводиться сообщение "Астероид добыт,
//        получено (ценность) очков!"
//        При этом, полученные из астероида очки должны прибавляться к очкам
//        у корабля.