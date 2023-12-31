import Obstacles.Anomaly;
import Obstacles.Asteroid;
import Obstacles.Enemy;
import Obstacles.Obstacle;
import SpaceShip.SpaceShip;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random;
        final int NUM_OF_ENEMY = 3;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your space ship name: ");
        String name = sc.nextLine();
        System.out.println();

        System.out.print("Enter game generation key(integer): ");

        if (sc.hasNext()) {
            final int gKey = sc.nextInt();
            random = new Random(gKey);
        } else {
            System.out.println("Generation key is wrong. The generation key will be 0");
            random = new Random();
        }

        Obstacle[][] arrOfObstacle = new Obstacle[5 + random.nextInt(15)][];

        for (int i = 0; i < arrOfObstacle.length; i++) {

            double rNum = random.nextDouble(12);

            if (rNum > 4.8) {
                int enemys = 1 + random.nextInt(NUM_OF_ENEMY);
                arrOfObstacle[i] = new Obstacle[enemys];

                for (int j = 0; j < enemys; j++) {
                    double str = (150 + random.nextDouble(500)) / enemys;
                    double att = (10 + random.nextDouble(90)) / enemys;
                    arrOfObstacle[i][j] = new Enemy(str, att);
                }

            } else if (rNum > 1.2) {
                arrOfObstacle[i] = new Obstacle[1];
                arrOfObstacle[i][0] = new Asteroid(
                        150 + random.nextDouble(350),
                        100 + random.nextDouble(900));
            } else {
                arrOfObstacle[i] = new Obstacle[1];
                arrOfObstacle[i][0] = new Anomaly(50 + random.nextDouble(100));
            }
        }
//        System.out.println("---------------");
//        for (Obstacle[] el : arrOfObstacle) {
//            System.out.println(Arrays.toString(el));
//        }
//        System.out.println("---------------");

        SpaceShip spaceShip = new SpaceShip(name,
                40 + random.nextDouble(20),
                400 + random.nextDouble(200),
                400 + random.nextDouble(400));

        System.out.println("You have space ship!");

        System.out.println(spaceShip);

        playGame(spaceShip, arrOfObstacle, random);

        sc.close();
    }

    public static void playGame(SpaceShip ship, Obstacle[][] obstacles, Random random) {
        Scanner scann = new Scanner(System.in);
        LocalTime currentTime = LocalTime.now();

        boolean skipObstacle = false;

        for (Obstacle[] obst : obstacles) {
            if (!ship.isEnergy()) {
                ship.destroy();
                return;
            }
            ship.reduceEnergy(10 + random.nextDouble(10));

            if (skipObstacle) {
                skipObstacle = false;
                if (!ship.isEnergy()) {
                    ship.destroy();
                    return;
                }
                ship.reduceEnergy(30 + random.nextDouble(20));
                continue;
            }

            if (obst[0] instanceof Asteroid) {
                meetAsteroid((Asteroid) obst[0], ship, random, scann);
                if (!ship.isEnergy()) return;
           }
            if (obst[0] instanceof Anomaly) {
                obst[0].encounter();
                ((Anomaly) obst[0]).attack(ship);
            }
            if (obst[0] instanceof Enemy) {
                meetEnemy( obst,ship,random,scann);
            }
            if (!ship.isAlive()) return;
        }
        Duration duration = Duration.between(currentTime, LocalTime.now());

        ship.getWin(duration.toSeconds());
    }

    public static boolean meetAsteroid(Asteroid ob, SpaceShip ship, Random random, Scanner scann) {
        boolean res = false;
        ob.encounter();
        System.out.println("Mine this asteroid or fly around?");
        System.out.println("(Flying around an asteroid also allows you to skip the following obstacle.)");
        System.out.println("Mine : y; Fly around : another  key :");
        char key = scann.nextLine().charAt(0);

        if (key == 'y') {
            while (ob.isAlive()) {
                if (!ship.isEnergy()) {
                    ship.destroy();
                    return res;
                }
                ship.attack(ob);
                ship.reduceEnergy(5 + random.nextDouble(7));
            }

        } else res = true;
        getBonus(ship, random);
        System.out.print(ship);

        return res;
    }
    public static void meetEnemy(Obstacle[] obst, SpaceShip ship, Random random, Scanner scann){
        for (Obstacle elem : obst) {
            elem.encounter();
        }
        System.out.printf("You're being attacked by <%d> enemies!%n", obst.length);


        boolean isObstAlive = true;
        while (ship.isAlive() && isObstAlive) {

            System.out.print("Enter your attack key (integer 1 - 5) : ");
            char userKey = scann.nextLine().charAt(0);

            for (Obstacle el : obst) {

                System.out.print(ship);
                System.out.print(el);

                if (!el.isAlive()) continue;
                char attackKey = ((1 + random.nextInt(5)) + "").charAt(0);
                double shipAttack = ship.getAttackPower();
                if (attackKey == userKey) ship.setAttackPower(shipAttack * 2);
                else if (Math.abs(attackKey - userKey) != 1) ship.setAttackPower(0);

                if (!ship.isEnergy()) {
                    ship.destroy();
                    return;
                }
                ship.attack(el);
                ship.reduceEnergy(5 + random.nextDouble(7));
                ship.setAttackPower(shipAttack);
                ((Enemy) el).attack(ship);
                if (!el.isAlive()) getBonus(ship, random);

            }
            isObstAlive = false;

            for (Obstacle el : obst) {
                if (el.isAlive()) {
                    isObstAlive = true;
                    break;
                }
            }
        }
    }
    public static void getBonus(SpaceShip ship, Random random) {
        switch (random.nextInt(10)) {
            case 0:
                double rAtt = ship.getAttackPower() * 2 * (1 + random.nextInt(2));
                ship.setAttackPower(rAtt);
                System.out.printf("You have bonus! Attack power <%.2f>!%n", rAtt);
                break;
            case 1, 2, 3:
                double rEner = 100 + random.nextDouble(200);
                ship.addEnergy(rEner);
                System.out.printf("You have bonus! Energy + <%.2f>!%n", rEner);
                break;
            case 4, 5, 6:
                double rStr = 100 + random.nextDouble(200);
                ship.addStrength(rStr);
                System.out.printf("You have bonus! Strength + <%.2f>!%n", rStr);
                break;
        }
    }
}


//        Идеи:
//        + Сделать сразу несколько врагов за один бой
//        - Учесть в счете время прохождения
//        - Ввести систему энергии или топлива корабля
//        - Сделать аномалии, которые при встрече просто наносят урон

//Сложность: средне (6/10)
//
//        Игра "космические приключения"
//
//        Разработать абстрактный класс GameObject, для него определить абстрактные
//        :
//        1 void destroy() - разрушить игровой объект
//        2 void hurt(double amount) - повредить игровой объект
//        Добавить защищенное поле "оставшаяся прочность", добавить аксесоры и
//        конструктор.
//        Добавить метод boolean isAlive(), который возвращает true если прочность
//        больше нуля.
//
//        Добавить интерфейс Attacker, в котором определить метод
//        void attack(GameObject target)
//
//        Разработать абстрактный класс "Припятствие", который
//        наследовать класс игровой объект
//        В этом классе описать абстрактный метод void encounter() - встретить/найти.
//        task part 2
//
//        Сложность: средне (6/10)
//
//        Разработать класс SpaceShip.SpaceShip (космический корабль).
//        Добавить ему поле "Название" и "Сила атаки". Этот класс должен
//        реализовывать интерфейс Attacker.
//        В методе attack просто повредить target на значение силы атаки.
//        Добавить в этот класс поле "Количество очков", в конструкторе
//        инициализировать его нулем.
//
//        Разработать класс "Враг" и "Астероид", которые будут наследовать класс
//        Препятствие.
//        В класс астероид добавить поле "ценность" и метод "добыть", который
//        вернет ценность этого астероида и вызовет
//        destroy у этого астероида.
//        При встрече с астероидом должно быть выведено сообщение "Вы нашли
//        астероид с ресурсами!"
//        При разрушении астероида, должно выводиться сообщение "Астероид добыт,
//        получено (ценность) очков!"
//        При этом, полученные из астероида очки должны прибавляться к очкам у корабля.
//
//        В классе Враг добавить поле "сила атаки". Этот класс должен реализовывать
//        интерфейс Attacker.
//        В методе attack просто повредить target на значение силы атаки.
//        При встрече с врагом вывести в консоль его силу атаки и прочность.
//        task part 3
//
//        Сложность: средне (6/10)
//
//        Добавить класс Main.
//        В этом классе создать метод main. В этом методе провести подготовку к игре:
//        1 Спросить у пользователя имя корабля
//        2 Спросить ключ генерации игры.
//
//        Разработать метод, который вернет массив объектов типа Препятствие и
//        принимает ключ генерации (тип int).
//        Создать генератор случайных чисел как статическое поле класса,
//        инициализировать его в меине,
//        указать переданное число в качестве ключа.
//        Если число равное 0, не указывать ключ для генератора вообще.
//        Далее использовать именно этот генератор случайных чисел для всей
//        генерации случайных чисел в программе.
//
//        С помощью этого генератора создать массив от 5 до 20 элементов.
//        Заполнить массив так, чтобы в нем было примерно 30% астероидов
//        и 70% врагов.
//        Для астероидов указывать значение "ценность" в диапазоне от 100 до 1000.
//        Для врагов указывать атаку в диапазоне от 10 до 100, а здоровье от 150 до
//        500.
//
//        Создать космический корабль игрока, который будет иметь от 40 до 60 очков
//        атаки и от 400 до 600
//        очков прочности (также определить случайно по ключу генерации).
//
//        Создать метод playGame, который приймет космический корабль и массив
//        препятствий.
//        task part 4
//
//        Сложность: средне (6/10)
//
//        Разработать метод playGame.
//        Игра должна происходить таким образом: игрок по очереди стыкается с каждым
//        препятствием.
//        Если препятствием был астероид, игрок должен выбрать, добыть астероид или
//        его.
//        Облетание астероида позволяет также пропустить следующее препятствие.
//        Если игрок встречает врага, должен начаться бой, который заканчивается
//        тогда,
//        когда один из кораблей будет разрушен.
//        Во время атаки, игроку предлагается угадать загаданное программой число
//        от 1 до 5. Если игрок
//        угадывает число верно, врагу нанесется двойной урон от силы атаки игрока.
//        Если игрок скажет число на 1 больше или меньше, нанесется обычный урон.
//        Во всех остальных случаях считаем что игрок промазал, урон не наносится.
//
//        Если корабль игрока уничтожен, игра проиграна. Если вражеский корабль
//        уничтожен, игрок переходит к следующему препятствию.
//        За уничтожение врага дается 200 очков.
//        Когда препятствия закончатся, игра считается выигранной, нужно подсчитать
//        счет игрока по такой формуле:
//
//        счет = полученные очки + оставшееся здоровье * 5
//        По выполненю всех задач, реализовать любую новую механику на ваш вкус.
//        Идеи:
//        - Сделать сразу несколько врагов за один бой ( до 3 одновременно)
//        - Учесть в счете время прохождения (бонус до 20с +2000очков. до 30с -100, до 40 -500)
//        - Ввести систему энергии или топлива корабля
//(старовый заряд энергии от 400 до 800. стоимость перемещение -от 10 до 20, одна атака - от 5 до 7,
// облет астероида - от 30-до50)
//         - добавлен вероятность получения бонуса после уничтожения препядcтвия( + к атаке, прочночти или енергии)
//        - Сделать аномалии, которые при встрече просто наносят урон (от 50 до 150)