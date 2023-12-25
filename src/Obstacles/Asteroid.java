package Obstacles;

public class Asteroid extends Obstacle {
    private double value;

    public Asteroid(double remainingStrength, double value) {
        super(remainingStrength);
        this.value = value;
    }

    public double mine() {
        double res =this.value;
        destroy();
        System.out.printf("Asteroid mined, (%,.2f) points gained!%n", res);
        return res;

    }

    @Override
    public void encounter() {
        System.out.println("You have found an asteroid with resources!");
    }

    @Override
    public void destroy() {
        this.value=0;
    }

    @Override
    public String toString() {
        return String.format("Asteroid{value=%.2f, %s}%n", this.value, super.toString());


    }
}

