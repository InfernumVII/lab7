package server.managers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import collection.Dragon;
import server.managers.exceptions.DragonFileExistException;
import server.managers.exceptions.DragonFindException;
import server.managers.utility.CSV;
import server.managers.utility.DragonCSVParser;


public class DragonManager {
    private LinkedHashSet<Dragon> dragonSet;
    private LocalDate initializationDate;
    private String dragonFilePath;

    public DragonManager() {
        dragonFilePath = System.getenv("DRAGON_FILE");
        if (dragonFilePath == null || dragonFilePath.isEmpty()) {
            throw new DragonFileExistException();
        }
        dragonSet = new LinkedHashSet<>();
        initializationDate = LocalDate.now();
        addDragonsFromDragonFileEnv();
    }

    public void addDragonsFromDragonFileEnv(){
        List<String[]> fileData = CSV.read(dragonFilePath);
        if (fileData != null){
            collectParsedDragons(fileData);
            System.out.println("Коллекция успешно загружена из файла: " + dragonFilePath);
        }
    }

    public void saveDragonsToCSV(){
        CSV.writeOneLine(dragonFilePath, new String[]{"id", "name", "coordinates", "creationDate", "age", "color", "type", "character", "head"});
        for (Dragon dragon : getSortedDragons()) {
            String[] row = DragonCSVParser.parseRowFromDragon(dragon);
            if (row != null) {
                CSV.writeOneLine(dragonFilePath, row, true);
            }
        }
    }

    public void collectParsedDragons(List<String[]> input){
        for (String[] row : input) {
            Dragon dragon = DragonCSVParser.parseDragonFromRow(row);
            if (dragon != null) {
                dragonSet.add(dragon);
            }
        }
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

    public void addDragon(Dragon e){
        if (dragonSet.add(e) == false){
            System.out.println("Такой дракон уже существует и не был добавлен.");
        }
    }

    public boolean setHaveId(int id) {
        return dragonSet.stream()
                .anyMatch(dragon -> dragon.getId() == id);
    }

    public Dragon returnDragonById(int id) throws DragonFindException {
        return dragonSet.stream()
                .filter(dragon -> dragon.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DragonFindException());
    }
    
    public LinkedHashSet<Dragon> getDragonSet() {
        return dragonSet;
    }

    public List<Dragon> getSortedDragons() {
        return dragonSet.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public Dragon getMinDragonByXAndY(){
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

    public void clearDragonSet(){
        dragonSet.clear();
    }

    public void removeDragon(Dragon e){
        dragonSet.remove(e);
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }
}
