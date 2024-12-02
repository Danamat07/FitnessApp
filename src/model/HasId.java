package model;

import java.io.Serializable;

public interface HasId extends Serializable {
    int getId();
    void setId(int id);
}