public interface IState<B, D> {
    B getBoard();
    IState getParent();
    D getOriginPath();
}