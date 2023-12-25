import Obstacles.Asteroid;
import Obstacles.Enemy;
import Obstacles.Obstacle;
import SpaceShip.SpaceShip;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random;

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

//        String name = "Ship";
//        random = new Random();

        Obstacle[] arrOfObstacle = new Obstacle[5 + random.nextInt(15)];

        for (int i = 0; i < arrOfObstacle.length; i++) {

            arrOfObstacle[i] = (random.nextInt(10) > 3) ?
                    new Enemy(150 + random.nextDouble(350),
                            10 + random.nextDouble(90)) :
                    new Asteroid(150 + random.nextDouble(350),
                            100 + random.nextDouble(900));
        }
        //System.out.println(Arrays.toString(arrOfObstacle));

        SpaceShip spaceShip = new SpaceShip(name,
                40 + random.nextDouble(20),
                400 + random.nextDouble(200));

        System.out.println("You have space ship!");

        System.out.println(spaceShip);

        playGame(spaceShip, arrOfObstacle, random);

        sc.close();
    }

    public static void playGame(SpaceShip ship, Obstacle[] obstacles, Random random) {
        Scanner scann = new Scanner(System.in);

        boolean skipObstacle = false;

        for (Obstacle obst : obstacles) {

            if (skipObstacle) {
                skipObstacle = false;
                continue;
            }

            obst.encounter();
            if (obst instanceof Asteroid) {

                System.out.println("Mine this asteroid or fly around?");
                System.out.println("(Flying around an asteroid also allows you to skip the following obstacle.)");
                System.out.println("Mine : y; Fly around : another  key :");
                char key = scann.nextLine().charAt(0);

                if (key == 'y') {
                    while (obst.isAlive()) {
                        ship.attack(obst);
                    }
                } else skipObstacle = true;

            }
            if (obst instanceof Enemy) {
                while (ship.isAlive() && obst.isAlive()) {
                    System.out.print("Enter your attack key (integer 1 - 5 : ");
                    char attackKey = ((1 + random.nextInt(5)) + "").charAt(0);
                    char userKey = scann.nextLine().charAt(0);

                    double shipAttack = ship.getAttackPower();
                    if (attackKey == userKey) ship.setAttackPower(shipAttack * 2);
                    else if (Math.abs(attackKey - userKey) != 1) ship.setAttackPower(0);

                    System.out.print(ship);
                    System.out.print(obst);

                    ship.attack(obst);
                    ship.setAttackPower(shipAttack);
                    ((Enemy) obst).attack(ship);
                }
            }
            if (!ship.isAlive()) return;
        }

        ship.getWin();

        scann.close();
    }


}


//        Идеи:
//        - Сделать сразу несколько врагов за один бой
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
//        - Сделать сразу несколько врагов за один бой
//        - Учесть в счете время прохождения
//        - Ввести систему энергии или топлива корабля
//        - Сделать аномалии, которые при встрече просто наносят урон