public class GameMain {


    public static void main(String[] args) {

        MapManager mapManager = new MapManager();

        try{
            mapManager.init();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        GameManager gameManager = new GameManager(mapManager);
        try {
            gameManager.run();
        }catch(IndexOutOfBoundsException e){
            System.out.println("모든 Stage를 클리어 했습니다!");
            System.out.println("--게임을 종료합니다--");
            return;
        }


    }
}
