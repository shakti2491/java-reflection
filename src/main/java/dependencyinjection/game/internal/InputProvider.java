package dependencyinjection.game.internal;


interface InputProvider {
    BoardLocation provideNextMove(Board board);
}
