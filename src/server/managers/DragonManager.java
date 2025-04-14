package server.managers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


import collection.Dragon;
import server.managers.exceptions.DragonFindException;
import server.managers.utility.CSV;
import server.managers.utility.DragonCSVParser;


public class DragonManager {
    private Set<Dragon> dragonSet;
    private LocalDate initializationDate;
    
    public DragonManager() {
        dragonSet = Collections.synchronizedSet(new LinkedHashSet<>());
        initializationDate = LocalDate.now();
    }
    
    private int getUniqueId(int lastId){
        if (setHaveId(lastId)){
            return getUniqueId(lastId + 1);
        } else {
            return lastId;
        }
    }

    public int getUniqueId(){
        return getUniqueId(1);
    }


    public String getTypeName(){
        return dragonSet.getClass().getSimpleName();
    }

    public synchronized void addDragon(Dragon e){
        if (dragonSet.add(e) == false){
            System.out.println("Такой дракон уже существует и не был добавлен.");
        }
    }

    public synchronized boolean setHaveId(int id) {
        return dragonSet.stream()
                .anyMatch(dragon -> dragon.getId() == id);
    }

    public synchronized Dragon returnDragonById(int id) throws DragonFindException {
        return dragonSet.stream()
                .filter(dragon -> dragon.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DragonFindException());
    }
    
    public synchronized Set<Dragon> getDragonSet() {
        return dragonSet;
    }

    public synchronized List<Dragon> getSortedDragons() {
        return dragonSet.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public synchronized Dragon getMinDragonByXAndY(){
        Dragon minDragon = Collections.min(getDragonSet(), new Comparator<Dragon>() {
                @Override
                public int compare(Dragon d1, Dragon d2) {
                    int xCompare = Long.compare(d1.getCoordinates().getX(), d2.getCoordinates().getX());
                    if (xCompare != 0) {
                        return xCompare; 
                    }
                    return Long.compare(d1.getCoordinates().getY(), d2.getCoordinates().getY());
                }
            });
        return minDragon;
    }

    public synchronized void clearDragonSet(){
        dragonSet.clear();
    }

    public synchronized void removeDragon(Dragon e){
        dragonSet.remove(e);
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }
}
