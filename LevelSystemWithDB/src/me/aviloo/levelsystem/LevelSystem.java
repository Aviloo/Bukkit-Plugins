package me.aviloo.levelsystem;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LevelSystem{

    private static final ConcurrentHashMap<UUID,Integer> PlayerLevel= new ConcurrentHashMap<>();

    public static void addValue(UUID uuid, Integer value){
        PlayerLevel.put(uuid, PlayerLevel.getOrDefault(uuid,0) + value);
    }
    public static Integer getValue(UUID uuid){
        return PlayerLevel.getOrDefault(uuid, 0);
    }
    public static void updateValue(UUID uuid,Integer value){
        PlayerLevel.put(uuid,value);
    }
}
