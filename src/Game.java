public class Game {
    public Game() {
        Level level = new Level(true);
        User user = new User();

        user.selectLevel(level);
//        State state = new State(user.getRows(), user.getColumns());
//        Movement move = new Movement(user.getRows(), user.getColumns());

//        user.initialState(state.getBoard(), move);
//        state.initialState(user.getBoard(), move);
//        user.inputMood();
    }
}
