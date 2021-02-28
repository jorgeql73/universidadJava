package com.challenge.university.utils;


public class ElementsPage {
    
    private int numero;
    private boolean actual;

    public int getNumero() {
        return numero;
    }

    public boolean isActual() {
        return actual;
    }

    public ElementsPage() {
    }

    public ElementsPage(int numero, boolean actual) {
        this.numero = numero;
        this.actual = actual;
    }
    
}
