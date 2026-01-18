package finance.managers;

import finance.interfaces.IDataManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager implements IDataManager {
    private String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    /** 
     * @param data
     * @return boolean
     */
    @Override
    public boolean saveData(List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /** 
     * @return List<String>
     */
    @Override
    public List<String> loadData() {
        List<String> data = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            return data;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return data;
    }

    /** 
     * @return String
     */
    public String getFilePath() {
        return filePath;
    }

    /** 
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
