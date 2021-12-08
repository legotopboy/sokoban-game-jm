import java.util.ArrayList;
import java.util.List;

public class GameMenuUtil {

    static GameMenuUtil gameMenuUtil;

    private SavedGame[] savedGameArray = new SavedGame[5];



    public static GameMenuUtil getInstance() {
        if(gameMenuUtil ==null){
            gameMenuUtil = new GameMenuUtil();
        }
        return gameMenuUtil;
    }


    public boolean saveGame(List<Map> mapList, int index, int savedStage){

        savedGameArray[index] = new SavedGame(mapList, savedStage);
        //ArrayList<Map> list = (ArrayList<Map>)mapList.clone();
        //return this.savedGameList.add(list);
        return true;
    }
    public SavedGame loadGame(int index){

        return savedGameArray[index];

    }


}
