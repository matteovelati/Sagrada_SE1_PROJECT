package it.polimi.ingsw.model;

import java.io.Serializable;

public abstract class Card implements Serializable {

    private String name;
    private String description;
    private Colors color;
    private int idNumber;

    /**
     * creates a new Card object
     */
    public Card(){
    }

    /**
     * gets the name of the card
     * @return the name of the card
     */
    public String getName(){
        return name;
    }

    /**
     * gets the descprition of the card
     * @return the description of the card
     */
    public String getDescription(){
        return description;
    }

    /**
     * gets the idnumber of the card
     * @return the idnumber of the card
     */
    public int getIdNumber() {
        return idNumber;
    }

    /**
     * gets the color of the card
     * @return the color of the card
     */
    public Colors getColor() {
        return color;
    }

    /**
     * sets the name to the card
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the description to the card
     * @param description the description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * sets the idnumber to the card
     * @param idNumber the idnumber to be set
     */
    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * sets the color to the card
     * @param color the color to be set
     */
    public void setColor(Colors color) {
        this.color = color;
    }

}
