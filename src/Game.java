public class Game {
    public Game() {
        Level level = new Level(true);
        User user = new User();

        user.selectLevel(level);
    }
}
