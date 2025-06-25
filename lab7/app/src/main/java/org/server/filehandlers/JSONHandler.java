package org.server.filehandlers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * A generic JSON file handler that provides read/write operations for JSON files.
 * Supports custom type handling through Gson and includes file metadata operations.
 *
 * @param <T> the type of object this handler will work with
 */
public class JSONHandler<T> extends Handler<T> {
    /**
     * Gson instance configured with pretty printing and custom type adapters.
     */
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    /**
     * Type token used for generic type information.
     */
    private final TypeToken<T> typeToken;

    /**
     * Path to the JSON file this handler operates on.
     */
    private final String filePath;

    /**
     * Constructs a new JSONHandler for the specified type and file path.
     *
     * @param typeToken the TypeToken representing the generic type T
     * @param filePath  the path to the JSON file to handle
     */
    public JSONHandler(TypeToken<T> typeToken, String filePath) {
        this.filePath = filePath;
        this.typeToken = typeToken;
    }

    /**
     * Reads and parses the JSON file into an object of type T.
     *
     * @return the parsed object from the JSON file
     * @throws FileNotFoundException if the file does not exist
     * @throws RuntimeException      if there's an error reading or parsing the file
     */
    @Override
    public T read() throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден: " + filePath);
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            return gson.fromJson(reader, typeToken.getType());
        } catch (IOException | JsonSyntaxException e) {
            throw new RuntimeException("Ошибка чтения файла: " + e.getMessage(), e);
        }
    }

    /**
     * Gets the last modified date and time of the file.
     *
     * @return LocalDateTime representing when the file was last modified,
     * or null if the file doesn't exist or an error occurs
     */
    public LocalDateTime getModifDateTime() {
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }
        try {
            BasicFileAttributes fileAttrs = Files.readAttributes(Path.of(filePath), BasicFileAttributes.class);
            return fileAttrs.lastModifiedTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Writes the given object to the JSON file.
     * Creates parent directories if they don't exist.
     *
     * @param data the object to serialize to JSON
     * @throws RuntimeException if there's an error writing to the file
     */
    @Override
    public void write(T data) {
        File file = new File(filePath);

        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8")) {

            gson.toJson(data, writer);
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи файла: " + e.getMessage(), e);
        }
    }

    /**
     * Sets the last modified date of the file.
     *
     * @param dateTime the new last modified date and time to set
     */
    public void setModifiedDate(LocalDateTime dateTime) {
        FileTime newFileTime = FileTime.from(
                dateTime.atZone(ZoneId.systemDefault()).toInstant()
        );
        try {
            Files.setLastModifiedTime(Path.of(filePath), newFileTime);
        } catch (IOException e) {
            // Silently ignore if modification fails
        }
    }

    /**
     * Deletes the file if it exists.
     *
     * @throws FileNotFoundException if the file exists but cannot be deleted
     */
    public void removeFile() throws FileNotFoundException {
        File file = new File(filePath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new FileNotFoundException("Не удалось удалить файл: " + filePath);
            }
        }
    }

    /**
     * Checks if the file exists.
     *
     * @return true if the file exists, false otherwise
     */
    public Boolean checkFilesExistence() {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Custom TypeAdapter for LocalDateTime serialization/deserialization.
     * Uses ISO-8601 format for date-time representation.
     */
    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        /**
         * Formatter for ISO-8601 date-time format.
         */
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        /**
         * Serializes LocalDateTime to JSON.
         *
         * @param out   the JsonWriter to write to
         * @param value the LocalDateTime value to serialize
         * @throws IOException if writing fails
         */
        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(value));
            }
        }

        /**
         * Deserializes LocalDateTime from JSON.
         *
         * @param in the JsonReader to read from
         * @return the parsed LocalDateTime, or null if the value is null
         * @throws IOException if reading fails
         */
        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            if (in.peek() == null) {
                return null;
            }
            String dateTimeString = in.nextString();
            return LocalDateTime.parse(dateTimeString, formatter);
        }
    }
}