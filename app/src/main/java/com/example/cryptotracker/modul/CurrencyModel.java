package com.example.cryptotracker.modul;

public class CurrencyModel {
    private String id;
    private String name;
    private String symbol;
    private String price;

    public CurrencyModel(String name, String symbol, String price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String _getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public CurrencyModel() {
    }
}
