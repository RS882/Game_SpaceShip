package GameObjects;

abstract public class GameObject {
    private double strength;

    public GameObject(double remainingStrength) {
        this.strength = remainingStrength;
    }

    public double getStrength() {
        return this.strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

     public abstract void destroy();


    public abstract void hurt(double amount);

    public boolean isAlive() {
        return this.strength > 0;
    }


}
