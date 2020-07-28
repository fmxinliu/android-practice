package com.example.xinliu.equipment;

import java.io.Serializable;

/**
 * 必须要可序列化，这样才能通过 Intent 进行传递
 */

public class ItemInfo implements Serializable {
    private String name;
    private int attack;
    private int life;
    private int speed;

    public ItemInfo(String name, int attack, int life, int speed) {
        this.name = name;
        this.attack = attack;
        this.life = life;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getLife() {
        return life;
    }

    public int getSpeed() {
        return speed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
