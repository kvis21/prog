package org.server.collections;

import com.google.gson.reflect.TypeToken;

import org.shared.exceptions.NotFoundException;
import org.client.Configuration;
import org.server.filehandlers.JSONHandler;
import org.shared.models.Flat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Manages collection of Flat objects with persistence capabilities.
 * Implements singleton pattern and provides collection operations.
 */
public class CollectionManager implements Saveable, Loadable {
    private transient static CollectionManager instance;
    private final Set<Flat> collection = new HashSet<>();
    private int currentId = 1;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private transient JSONHandler<SaveData> jsonHandler;

    /**
     * Private constructor initializes collection handler.
     */
    private CollectionManager() {
        lastInitTime = LocalDateTime.now();
        jsonHandler = new JSONHandler<SaveData>(new TypeToken<SaveData>(){}, Configuration.COLLECTION_FILE_PATH);
    }

    /**
     * Generates and returns next available ID.
     * @return generated ID
     */
    public int generateId() {
        return currentId++;
    }

    /**
     * Sets current ID counter.
     * @param currentId new ID value
     */
    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    /**
     * Gets current ID counter.
     * @return current ID value
     */
    public int getId() {
        return currentId;
    }

    /**
     * Returns singleton instance.
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
     * @param manager instance to set
     */
    public static void setInstance(CollectionManager manager) {
        instance = manager;
    }
    
    /**
     * Loads collection from file.
     */
    public void load() {
        collection.clear();
        try {
            SaveData saveData = jsonHandler.read();
            setCurrentId(saveData.getCurrentId());
            
            collection.addAll(saveData.getCollection());
            System.out.println("Коллекция была загружена");
            
        } catch (FileNotFoundException e) {
            //if file not found, create new empty collection file
            jsonHandler.write(new SaveData(collection, currentId));
        }
        lastInitTime = LocalDateTime.now();
    }

    /**
     * Loads collection without modification check.
     */
    public void loadModifiedFile() {
        try {
            SaveData saveData = jsonHandler.read();
            setCurrentId(saveData.getCurrentId());
            collection.addAll(saveData.getCollection());
        } catch (IOException e) {}
    }

    /**
     * Saves collection to file.
     */
    public void save() {
        lastSaveTime = LocalDateTime.now();
        jsonHandler.write(new SaveData(collection, currentId));
        jsonHandler.setModifiedDate(lastSaveTime);
    }

    /**
     * Adds flat to collection.
     * @param flat Flat to add
     */
    public void addElement(Flat flat) {
        collection.add(flat);
    }

    /**
     * Gets flat by ID.
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
     * @param flat Flat to update
     */
    public void update(Flat oldFlat, Flat newFlat) {
        remove(oldFlat);
        addElement(newFlat);
    }

    /**
     * Removes flat from collection.
     * @param flat Flat to remove
     */
    public void remove(Flat flat) {
        collection.remove(flat);
    }

    /**
     * Clears collection and resets ID counter.
     */
    public void clear() {
        collection.clear();
        setCurrentId(1);
    }
    
    /**
     * Gets entire collection.
     * @return Set of Flats
     */
    public Set<Flat> getCollection() {
        return collection;
    }

    /**
     * Gets flat with minimal value.
     * @return minimal Flat
     */
    public Flat getMinFlat() {
        Optional<Flat> minFlat = collection.stream()
                                            .min(Flat::compareTo);
        return minFlat.orElse(null);
       
    }

    /**
     * Calculates average number of bathrooms.
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
     * Gets JSON handler instance.
     * @return JSONHandler
     */
    public JSONHandler<SaveData> getJsonHandler() {
        return jsonHandler;
    }

    /**
     * Gets last initialization time.
     * @return LocalDateTime
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }
    
    /**
     * Gets last save time.
     * @return LocalDateTime
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }
    
    /**
     * Gets collection size.
     * @return size
     */
    public int getColectionSize() {
        return collection.size();
    }

    /**
     * Gets collection type.
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

    /**
     * Inner class for collection serialization.
     */
    public class SaveData {
        private Set<Flat> collection;
        private int currentId;
        private LocalDateTime lastSaveTime;
    
        /**
         * Creates SaveData instance.
         * @param collection Set of Flats
         * @param currentId current ID counter
         */
        public SaveData(Set<Flat> collection, int currentId) {
            this.collection = collection;
            this.currentId = currentId;
            this.lastSaveTime = LocalDateTime.now();
        }

        /**
         * Gets current ID.
         * @return ID
         */
        public int getCurrentId() {
            return currentId;
        }

        /**
         * Gets collection.
         * @return Set of Flats
         */
        public Set<Flat> getCollection() {
            return collection;
        }

        /**
         * Gets last save time.
         * @return LocalDateTime
         */
        public LocalDateTime getLastSaveTime() {
            return lastSaveTime;
        }
    }
}
