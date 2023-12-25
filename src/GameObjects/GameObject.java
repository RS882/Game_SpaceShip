package GameObjects;

abstract public class GameObject {
    private double remainingStrength;

    public GameObject(double remainingStrength) {
        this.remainingStrength = remainingStrength;
    }

    public double getRemainingStrength() {
        return this.remainingStrength;
    }

    public void setRemainingStrength(double remainingStrength) {
        this.remainingStrength = remainingStrength;
    }

    abstract void destroy();

    abstract void hurt(double amount);

    public boolean isAlive() {
        return this.remainingStrength > 0;
    }
}
