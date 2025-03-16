package needlewunsch.controller;

public abstract class AllowsIntermediateDecorator implements MatrixDecorator {
    private final MatrixDecorator next;

    public AllowsIntermediateDecorator() {
        this.next = null;
    }

    public AllowsIntermediateDecorator(MatrixDecorator next) {
        this.next = next;
    }

    @Override
    public MatrixDecorator next() {
        return next;
    }
}
