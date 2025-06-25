package org.server.collections;

import org.shared.exceptions.NotFoundException;
import org.server.database.dbmanager.DBFlatManager;
import org.shared.models.Flat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Manages collection of Flat objects with persistence capabilities.
 * Implements singleton pattern and provides collection operations.
 */
public class CollectionManager implements Saveable, Loadable {
    private transient static CollectionManager instance;
    private final Set<Flat> collection = new HashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private transient DBFlatManager dbManager = DBFlatManager.getInstance();

    /**
     * Private constructor initializes collection handler.
     */
    private CollectionManager() {
        lastInitTime = LocalDateTime.now();
    }

    /**
     * Returns singleton instance.
     *
     * @return CollectionManager instance
     */
    public static CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
        }
        return instance;
    }

    /**
     * Sets singleton instance.
     *
     * @param manager instance to set
     */
    public static void setInstance(CollectionManager manager) {
        instance = manager;
    }

    /**
     * Loads collection from file.
     */
    @Override
    public void load() {
        collection.clear();
        try {
            Set<Flat> flatsFromDB = dbManager.findAll();
            collection.addAll(flatsFromDB);
            System.out.println("Коллекция загружена из базы данных");
            lastInitTime = LocalDateTime.now();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки из БД: " + e.getMessage());
        }
    }


    /**
     * Saves collection to file.
     */
    public void save() {
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Adds flat to collection.
     *
     * @param flat Flat to add
     */
    public void addElement(Flat flat) {
        dbManager.save(flat);
        collection.add(flat);
        save();
    }

    /**
     * Gets flat by ID.
     *
     * @param id ID to search
     * @return found Flat
     * @throws NotFoundException if flat not found
     */
    public Flat getById(int id) throws NotFoundException {
        Optional<Flat> result = collection.stream().filter(element -> element.getId() == id).findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        throw new NotFoundException();
    }

    /**
     * Updates flat properties.
     *
     * @param flat Flat to update
     */
    public void update(Flat oldFlat, Flat newFlat) {
        dbManager.update(newFlat, oldFlat.getId());

        collection.remove(oldFlat);
        collection.add(newFlat);
    }

    /**
     * Removes flat from collection.
     *
     * @param flat Flat to remove
     */
    public void remove(Flat flat) {
        dbManager.removeById(flat.getId());
        collection.remove(flat);
    }

    public void removeIf(Predicate<Flat> predicate) {
        for (Flat flat : collection) {
            if (predicate.test(flat)) {
                dbManager.removeById(flat.getId());
            }
        }
        collection.removeIf(predicate);
    }


    public void clear(int userId) {
        dbManager.removeByUserId(userId);
        Set<Flat> newCollection = collection.stream()
                .filter((el) -> el.getUserId() != userId)
                .collect(Collectors.toSet());
        collection.clear();
        collection.addAll(newCollection);
    }


    /**
     * Gets entire collection.
     *
     * @return Set of Flats
     */
    public Set<Flat> getCollection() {
        return collection;
    }

    /**
     * Gets flat with minimal value.
     *
     * @return minimal Flat
     */
    public Flat getMinFlat() {
        Optional<Flat> minFlat = collection.stream()
                .min(Flat::compareTo);
        return minFlat.orElse(null);

    }

    /**
     * Calculates average number of bathrooms.
     *
     * @return average value
     */
    public float getAverageNumberOfBathrooms() {
        float sum = 0;
        for (Flat flat : collection) {
            sum += (float) flat.getNumberOfBathrooms();
        }
        return sum / collection.size();
    }

    /**
     * Gets flat with minimal number of rooms.
     *
     * @return Flat with minimal rooms
     */
    public Flat getMinByNumberOfRooms() {
        Flat minFlat = collection.iterator().next();
        for (Flat flat : collection) {
            if (flat.getNumberOfRooms() < minFlat.getNumberOfRooms()) {
                minFlat = flat;
            }
        }
        return minFlat;
    }

    /**
     * Filters flats containing name substring.
     *
     * @param name substring to search
     * @return List of matching Flats
     */
    public List<Flat> filterContainsName(String name) {
        List<Flat> flats = new ArrayList<Flat>();
        for (Flat flat : collection) {
            if (flat.getName().contains(name)) {
                flats.add(flat);
            }
        }
        return flats;
    }

    /**
     * Gets last initialization time.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Gets last save time.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Gets collection size.
     *
     * @return size
     */
    public int getColectionSize() {
        return collection.size();
    }

    /**
     * Gets collection type.
     *
     * @return type name
     */
    public String getCollectionType() {
        return collection.getClass().getName();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        String info = "";
        for (Flat flat : collection) {
            info += flat.toString();
            info += ";\n";
        }
        return info;
    }

}
