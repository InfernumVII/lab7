package managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import collection.Dragon;
import utility.DragonCSVParser;


/**
 * Класс для управления коллекцией драконов.
 * Реализует добавление, удаление, поиск и сортировку драконов.
 */
public class DragonManager {
    private LinkedHashSet<Dragon> dragonSet;
    private LocalDate initializationDate;

    /**
     * Конструктор для создания менеджера коллекции драконов.
     * Инициализирует коллекцию и запоминает дату создания.
     */
    public DragonManager() {
        dragonSet = new LinkedHashSet<>();
        initializationDate = LocalDate.now();
    }

    /**
     * Заполняет коллекцию драконами, полученными из парсинга CSV-файла.
     *
     * @param input список строк, содержащих данные о драконах.
     */
    public void collectParsedDragons(List<String[]> input){
        for (String[] row : input) {
            Dragon dragon = DragonCSVParser.parseDragonFromRow(row);
            if (dragon != null) {
                dragonSet.add(dragon);
            }
        }
    }
    
    /**
     * Возвращает уникальный ID для нового дракона.
     *
     * @param lastId последний использованный ID.
     * @return уникальный ID.
     */
    private int getUniqueId(int lastId){
        if (setHaveId(lastId)){
            return getUniqueId(lastId + 1);
        } else {
            return lastId;
        }
    }

    /**
     * Возвращает имя типа коллекции.
     *
     * @return имя типа коллекции.
     */
    public String getTypeName(){
        return dragonSet.getClass().getSimpleName();
    }

    /**
     * Возвращает уникальный ID для нового дракона, начиная с 1.
     *
     * @return уникальный ID.
     */
    public int getUniqueId(){
        return getUniqueId(1);
    }

    /**
     * Добавляет дракона в коллекцию.
     *
     * @param e объект дракона.
     */
    public void addDragon(Dragon e){
        int length = dragonSet.size();
        dragonSet.add(e);
        if (dragonSet.size() == length){
            System.out.println("Такой дракон уже сущствует и не был добавлен.");
        }
    }

    /**
     * Проверяет, существует ли в коллекции дракон с указанным ID.
     *
     * @param id ID дракона.
     * @return {@code true}, если дракон с таким ID существует, иначе {@code false}.
     */
    public boolean setHaveId(int id) {
        return dragonSet.stream()
                .anyMatch(dragon -> dragon.getId() == id);
    }

    /**
     * Возвращает дракона по его ID.
     *
     * @param id ID дракона.
     * @return объект дракона, если он найден, иначе {@code null}.
     * @throws Exception 
     */
    public Dragon returnDragonById(int id) throws Exception {
        return dragonSet.stream()
                .filter(dragon -> dragon.getId() == id)
                .findFirst()
                .orElseThrow(() -> new Exception("Дракона с данным ID не найдено."));
    }
    
    /**
     * Возвращает коллекцию драконов.
     *
     * @return коллекция драконов.
     */
    public LinkedHashSet<Dragon> getDragonSet() {
        return dragonSet;
    }

    /**
     * Возвращает отсортированный список драконов.
     *
     * @return отсортированный список драконов.
     */
    public List<Dragon> getSortedDragons() {
        return dragonSet.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Очищает коллекцию драконов.
     */
    public void clearDragonSet(){
        dragonSet.clear();
    }

    /**
     * Удаляет указанного дракона из коллекции.
     *
     * @param e объект дракона.
     */
    public void removeDragon(Dragon e){
        dragonSet.remove(e);
    }

    /**
     * Возвращает дату инициализации коллекции.
     *
     * @return дата инициализации коллекции.
     */
    public LocalDate getInitializationDate() {
        return initializationDate;
    }
}
