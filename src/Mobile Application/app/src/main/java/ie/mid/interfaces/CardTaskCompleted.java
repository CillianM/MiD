package ie.mid.interfaces;

import java.util.List;

import ie.mid.model.CardType;

/**
 * Created by Cillian on 08/02/2018.
 */

public interface CardTaskCompleted {
    void onTaskComplete(List<CardType> cardTypes);
}
