package backend;

import backend.model.Figure;


import backend.model.Figure;
import java.util.ArrayList;
import java.util.List;

public class CanvasState {
    private final List<Figure> list = new ArrayList();

    public CanvasState() {
    }

    public void addFigure(Figure figure) {
        this.list.add(figure);
    }

    public void deleteFigure(Figure figure) {
        this.list.remove(figure);
    }

    public Iterable<Figure> figures() {
        return this.list;
    }
}
