package ie.mid.identityengine.service;


import ie.mid.identityengine.exception.StorageException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class StorageService {

    @Value("${mid.uploadPath}")
    private String uploadPathString = "/home/cillian/uploads/";
    @Value("${mid.eraseOnInit}")
    private boolean eraseOnInit = true;

    private Path uploadPath;
    private Logger logger = LogManager.getLogger(StorageService.class);

    @Autowired
    public StorageService() {
        this.uploadPath = Paths.get(uploadPathString);
    }

    private List<File> getFileList() {
        File f = new File(uploadPathString);
        if (f.exists())
            return new ArrayList<>(Arrays.asList(f.listFiles()));
        else
            return Collections.emptyList();
    }

    public String saveData(String data) {
        String name = getUUID();
        String path = uploadPathString + name;
        logger.debug("Saving data to file " + uploadPathString);

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "utf-8"))) {
            writer.write(data);
        } catch (IOException e) {
            logger.error("Error saving data " + data + " to file " + uploadPathString);
            return null;
        }
        return path;
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    public String loadData(String filePath) {
        logger.debug("Loading data from file " + filePath);
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.error("Error loading data from file " + filePath);
            return null;
        }
        return resultStringBuilder.toString();
    }

    public void deleteAll() {
        if (eraseOnInit) {
            logger.debug("UPLOADED FILES ERASED");
            FileSystemUtils.deleteRecursively(uploadPath.toFile());
        }
    }

    public void init() {
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            logger.error("Could not initialise storeage at path " + uploadPath);
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
