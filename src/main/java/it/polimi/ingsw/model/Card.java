package it.polimi.ingsw.model;

public abstract class Card {

    private String name;
    private String description;
    private Colors color;
    private int idNumber;

    public Card(){

    }

    public Card(int idNumber){
        this.idNumber = idNumber;
    }

    public Card(Colors color){
        this.color = color;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public Colors getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

}
