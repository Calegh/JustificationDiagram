package models;

import export.*;

public interface Visitable {
    void accept(GraphDrawer visitor);
}