package it.paleocapa.daniellol;

import java.util.ArrayList;

public class Utente {
    private String nomeUtente;
    private ArrayList<String> foodList;

    Utente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
        this.foodList = new ArrayList<>();
    }

    public void addFood(String food) {
        foodList.add(food);
    }
    public void removeFood(String food) {
        foodList.remove(food);
    }

    public String getNomeUtente() {
        return nomeUtente;
    }
    public ArrayList<String> getFoodList() {
        return foodList;
    }

    public Double getTotPrice() {
        return foodList.stream().map(food -> Double.parseDouble(food.split(" - ")[1].replaceAll("[€$]", ""))).mapToDouble(Double::doubleValue).sum();
    }
}
