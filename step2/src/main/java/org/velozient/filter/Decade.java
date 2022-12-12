package org.velozient.filter;

public enum Decade {
    noughties(0),
    teenies(10),
    twenties(20),
    Thirties(30),
    forties(40),
    fifties(50),
    sixties(60),
    seventies(70),
    eighties(80),
    nineties(90);

    private final int value;
    Decade(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

}
