public class Map {

    private int stageNum;
    private int[][] map ;
    private int playerY, playerX;

    public Map(int stageNum) {
        this.stageNum = stageNum;

    }

    public Map(int stageNum, int[][] map) {
        this.stageNum = stageNum;
        this.map = map;
        scanPlayer();
    }

    public int[][] getMap() {
        return map;
    }

    public int getStageNum() {
        return stageNum;
    }

    public int getHallNum(){
        int hallCnt=0;
        for(int i=0; i<this.getHeight(); i++)
            for(int j=0; j<this.getWidth(); j++){
                if(map[i][j] == 1) hallCnt++;
            }

        return hallCnt;
    }

    public int getBallNum(){
        int ballCnt=0;
        for(int i=0; i<this.getHeight(); i++)
            for(int j=0; j<this.getWidth(); j++){
                if(map[i][j] == 2) ballCnt++;
            }

        return ballCnt;
    }

    public int getHeight(){
        return map.length;
    }

    public int getWidth(){
        return map[0].length;
    }

    private void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    private void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPy() {
        return playerY;
    }

    public int getPx() {
        return playerX;
    }

    public void scanPlayer(){
        for(int i=0; i<this.getHeight(); i++)
            for(int j=0; j<this.getWidth(); j++)
            {
                if(this.getMap()[i][j]==3)
                {
                    this.setPlayerY(i);
                    this.setPlayerX(j);
                }
            }
    }

}
