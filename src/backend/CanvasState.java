package backend;

import backend.model.Figure;

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

    //Agregamos al final de la lista las figuras que se quiere mover hacia adelante
    public void moveToFront(List<Figure> figures){
        for(Figure figure: figures) {
            list.remove(figure);
            list.offerLast(figure);
        }
    }

    //Agregamos al principio de la lista las figuras que se quiere mover hacia atras
    public void moveToBack(List<Figure> figures){
        for(Figure figure : figures){
            list.remove(figure);
            list.offerFirst(figure);
        }
    }
}
