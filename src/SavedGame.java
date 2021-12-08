import java.util.List;

public class SavedGame {
    private List<Map> savedMapList;
    private int savedStageNum;

    public SavedGame(List<Map> savedMapList, int savedStageNum) {
        this.savedMapList = savedMapList;
        this.savedStageNum = savedStageNum;
    }

    public int getSavedStageNum() {
        return savedStageNum;
    }

    public List<Map> getSavedMapList() {
        return savedMapList;
    }
}


