public class Map {

    private int stageNum;
    private int[][] map ;

    public Map(int stageNum) {
        this.stageNum = stageNum;

    }

    public Map(int stageNum, int[][] map) {
        this.stageNum = stageNum;
        this.map = map;
    }
}
