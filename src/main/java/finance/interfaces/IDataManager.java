package finance.interfaces;

import java.util.List;

public interface IDataManager {
    boolean saveData(List<String> data);
    List<String> loadData();
}
