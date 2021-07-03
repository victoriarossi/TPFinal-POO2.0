package backend;

import backend.model.Figure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CanvasState {

    private final LinkedList<Figure> list = new LinkedList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void deleteFigure(Figure figure){
        list.remove(figure);
    }

    public Iterable<Figure> figures() {
        return list;
    }

    public void moveToFront(List<Figure> figures){
        for(Figure figure: figures) {
            list.remove(figure);
            list.offerFirst(figure);
        }
    }
    public void moveToBack(List<Figure> figures){
        for(Figure figure : figures){
            list.remove(figure);
            list.offerLast(figure);
        }
    }
}
