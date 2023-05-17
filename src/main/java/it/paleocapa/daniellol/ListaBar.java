package it.paleocapa.daniellol;

import java.util.ArrayList;

public class ListaBar {
    private ArrayList<Utente> listaBar;

    public ListaBar() {
        listaBar = new ArrayList<>();
    }

    private void addUser(String nome) {
        if (!listaBar.stream().anyMatch(utente -> utente.getNomeUtente().equals(nome))) {
            listaBar.add(new Utente(nome));
        }
    }

    public void addFood(String nome, String food) {
        if (!listaBar.stream().anyMatch(utente -> utente.getNomeUtente().equals(nome))) {
            addUser(nome);
        }
        listaBar.stream().filter(utente -> utente.getNomeUtente().equals(nome)).forEach(utente -> utente.addFood(food));
    }

    public void removeFood(String nome, String food) {
        if (listaBar.stream().anyMatch(utente -> utente.getNomeUtente().equals(nome))) {
            listaBar.stream().filter(utente -> utente.getNomeUtente().equals(nome)).forEach(utente -> utente.removeFood(food));
        }
    }

    public ArrayList<Utente> getListaBar() {
        return listaBar;
    }

    public String getListaBarString() {
        return listaBar.stream().map(utente -> utente.getNomeUtente() + ": " + utente.getFoodList().toString() + "\n").reduce("", String::concat);
        //return getUserNameList().toString();
    }

    public ArrayList<String> getFoodList(String nome) {
        return listaBar.stream().filter(utente -> utente.getNomeUtente().equals(nome)).findFirst().get().getFoodList();
    }

    public ArrayList<String> getUserNameList() {
        return listaBar.stream().map(Utente::getNomeUtente).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public Double getTotMyPrice(String nome) {
        return listaBar.stream().filter(utente -> utente.getNomeUtente().equals(nome)).findFirst().get().getTotPrice();
    }

    public Double getTotPrice() {
        return listaBar.stream().map(Utente::getTotPrice).mapToDouble(Double::doubleValue).sum();
    }
}
