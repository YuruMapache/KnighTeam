package com.example.justajuan.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import objeto.Material;

public class BaseItem {
    private String name;
    private Duration timeCraft;
    private ArrayList<Material> materials;
    private int attack;
    private int defense;
    private List<Rol> allowed;

    public BaseItem(){

    }

}
