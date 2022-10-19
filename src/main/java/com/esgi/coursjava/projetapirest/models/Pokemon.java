package com.esgi.coursjava.projetapirest.models;

public class Pokemon {
    private long id;
    private String name;
    private int hp, attack, defense, atkSpe, defSpe, speed;

    public Pokemon() {}

    public Pokemon(long id, String name, int hp, int attack, int defense, int atkSpe, int defSpe, int speed) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.atkSpe = atkSpe;
        this.defSpe = defSpe;
        this.speed = speed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAtkSpe() {
        return atkSpe;
    }

    public void setAtkSpe(int atkSpe) {
        this.atkSpe = atkSpe;
    }

    public int getDefSpe() {
        return defSpe;
    }

    public void setDefSpe(int defSpe) {
        this.defSpe = defSpe;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
