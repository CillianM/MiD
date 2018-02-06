package ie.mid.identityengine.service;


import ie.mid.identityengine.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class StorageService {

    private String uploadPathString = "uploads/";
    private Path uploadPath;

    @Autowired
    public StorageService() {
        this.uploadPath = Paths.get(uploadPathString);
    }

    private List<File> loadAll() {
        File f = new File(uploadPathString);
        if (f.exists())
            return new ArrayList<File>(Arrays.asList(f.listFiles()));
        else
            return null;
    }

    public String saveData(String data) {
        String name = UUID.randomUUID().toString();
        String path = uploadPathString + name;
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "utf-8"))) {
            writer.write(data);
        } catch (IOException e) {
            return null;
        }
        return path;
    }

    public String loadData(String filePath) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            return null;
        }
        return resultStringBuilder.toString();
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(uploadPath.toFile());
    }

    public void init() {
        try {
            Files.createDirectories(uploadPath);
            List<File> fileList = loadAll();
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
