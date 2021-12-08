import java.io.*;
import java.nio.charset.StandardCharsets;

public class GameManager {


    private MapManager mapManager;

    private BufferedReader inputBr = new BufferedReader(new InputStreamReader(System.in));

    private int[] dy; //상하 이동을 위한 배열
    private int[] dx; //좌우 이동을 위한 배열

    public GameManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    /*
    상하좌우 입력 키(wsad) 에 따른 이용방향을 초기화합니다.
     */
    private void init(){
        dy = new int[(int)'Z' - (int)'A' + 5];
        dx = new int[(int)'Z' - (int)'A' + 5];
        dy[(int)'w' - (int)'a'] = -1; //
        dy[(int)'s' - (int)'a'] = 1;
        dx[(int)'a' - (int)'a'] = -1;
        dx[(int)'d' - (int)'a'] = 1;

    }

    /*
    전체적인 게임 진행
     */
    public void run() throws IndexOutOfBoundsException{

        init();

        int stageNum = 1;
        while(true){
            Map map = mapManager.loadMap(stageNum);

            mapManager.printSokoban(map);
            String command = getCommand();
            if("END".equals(command)) {
                System.out.println("게임을 종료합니다 또 만나요");
                return;
            }
            //q 입력되면 종료

            if(isAnswer(playSokoban(map, command, 0))){
                congratulate(map.getStageNum(), map.getTurnCnt());
                stageNum++;
            }

        }
    }

    /*
    프롬프트로 입력받은 커맨드를 하나씩 읽어서 이동체크 및 이동기능을 수행합니다.
    여러개의 커맨드를 하나씩 재귀적으로 호출하도록 구현했습니다.
     */
    private Map playSokoban(Map map, String command, int commandIdx){
        if(isAnswer(map) || commandIdx >= command.length()) {

            return map;
        }
        if(!moveChecker(map, command.charAt(commandIdx))) {
            mapManager.printSokoban(map);
            System.out.println("해당 명령을 수행할 수 없습니다.");
            return playSokoban(map, command, commandIdx+1);
        }

        move(map.getMap(), map.getPy(), map.getPx(), command.charAt(commandIdx));
        mapManager.printSokoban(map);

        printCommand(command.charAt(commandIdx));
        //System.out.println((dy[command.charAt(commandIdx) - (int)'a']));
        //System.out.println((dx[command.charAt(commandIdx) - (int)'a']));
        map.movePy(dy[command.charAt(commandIdx) - (int)'a']);
        map.movePx(dx[command.charAt(commandIdx) - (int)'a']);
        map.increTurnCnt();
        return playSokoban(map, command, commandIdx+1);
    }

    /*
    플레이어의 현재 좌표를 기준으로 입력받은 커맨드로 이동합니다. 이동전에 이동가능한지를 체크합니다.
     */
    private int[][] move(int[][] arr, int y, int x, char dir){

        int _y = y + dy[(int)dir - (int)'a'];
        int _x = x + dx[(int)dir - (int)'a'];
        if(arr[_y][_x] == ctoi(' ') || arr[_y][_x] == ctoi('O')){
            if(checkTarget(arr, y, x)) arr[y][x] = ctoi('O');
            else arr[y][x] = ctoi(' ');
            if(checkTarget(arr, _y, _x)) arr[_y][_x] = ctoi('Q');
            else arr[_y][_x] = ctoi('P');
        }
        if(arr[_y][_x] == ctoi('o') || arr[_y][_x] == ctoi('0')){
            if(checkTarget(arr, y, x)) arr[y][x] = ctoi('O');
            else arr[y][x] = ctoi(' ');
            if(checkTarget(arr, _y, _x)) arr[_y][_x] = ctoi('Q');
            else arr[_y][_x] = ctoi('P');
            int __y = _y + dy[(int)dir - (int)'a'];
            int __x = _x + dx[(int)dir - (int)'a'];
            if(checkTarget(arr, __y, __x)) arr[__y][__x] = ctoi('0');
            else arr[__y][__x] = ctoi('o');
        }
        return arr;
    }


    private boolean checkTarget(int[][] arr, int y, int x){
        return (arr[y][x] >= (int)'a' && arr[y][x] <= (int)'z') || arr[y][x] == ctoi('O');
    }

    private void printCommand(char oneCmd){
        System.out.printf("%c : %s",oneCmd , getDirectionNm(oneCmd) + " 이동합니다.");
        System.out.println();

    }

    private String getDirectionNm(char c){
        String ret = "";
        switch(c){
            case 'w':
                ret = "위로";
                break;
            case 's':
                ret = "아래로";
                break;
            case 'a':
                ret = "왼쪽으로";
                break;
            case 'd':
                ret = "오른쪽으로";
                break;

        }
        return ret;
    }

    private String getCommand(){
        System.out.print("SOKOBAN>");

        String cmd="";
        try{
            cmd = inputBr.readLine();
        } catch (IOException IOE){}

        if("q".equals(cmd)) cmd = "END";

        return cmd;
    }


    private boolean isAnswer(Map map){
        for(int i = 0; i < map.getHeight(); i++)
            for(int j = 0; j < map.getWidth(); j++)
                if(map.getMap()[i][j] == ctoi('O')) return false; // 'O'

        return true;
    }
    private void congratulate(int stageNum, int turnCnt){
        System.out.println("빠밤!! Stage "+ stageNum +" 클리어!!");
        System.out.println("턴수 : " + turnCnt);

    }

    /*
    현재 플레이어의 위치와 커맨드를 입력받아서 이동할 위치에 어떤물체가 있고 이동 가능한지를 판단합니다.
     */
    private boolean moveChecker(Map map, char dir){

        int _y = map.getPy() + dy[(int)dir - (int)'a'];
        int _x = map.getPx() + dx[(int)dir - (int)'a'];

        if(!outOfIndex(map,_y, _x) && map.getMap()[_y][_x] != ctoi('o') && map.getMap()[_y][_x] != ctoi('0') && map.getMap()[_y][_x] != ctoi('#')) {

            return true;
        }

        if(!outOfIndex(map,_y, _x) && (map.getMap()[_y][_x] == ctoi('o') || map.getMap()[_y][_x] == ctoi('0'))){ // 다음 위치가 공인경우
            int __y = _y + dy[(int)dir - (int)'a'];
            int __x = _x + dx[(int)dir - (int)'a'];

            if(outOfIndex(map,__y, __x) || map.getMap()[__y][__x] == ctoi('o') || map.getMap()[__y][__x] == ctoi('0') || map.getMap()[__y][__x] == ctoi('#')) {

                return false; // 이동위치가 공이거나 벽이라면 false
            }

            return true;
        }

        return false;
    }

    /*
    Stage 맵을 벗어났는지 판단합니다.
     */
    private boolean outOfIndex(Map map, int y, int x){
        return (y < 1 || x < 1 || y >= map.getHeight() || x >= map.getWidth());
    }

    private int ctoi(char c) {
        int ret = 0;
        switch (c) {
            case '#':
                ret = 0;
                break;
            case 'O':
                ret = 1;
                break;
            case 'o':
                ret = 2;
                break;
            case 'P':
                ret = 3;
                break;
            case '=':
                ret = 4;
                break;
            case '0':
                ret = 5;
                break;
            case 'Q':
                ret = 6;
                break;
            case ' ':
                ret = 9;
                break;
            default:
                throw new IllegalArgumentException("잘못된 입력입니다");

        }
        return ret;
    }
}
