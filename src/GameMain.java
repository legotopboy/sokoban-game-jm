public class GameMain {


    public static void main(String[] args) {

        MapManager mapManager = new MapManager();

        try{
            mapManager.init();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }
}
