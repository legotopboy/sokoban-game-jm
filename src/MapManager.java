import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapManager {

    private List<Map> mapList = new ArrayList<>();

    /*
    1단계 요구사항에 따라 문자열로 지도를 입력받기위한 입력데이터 초기화 및 inputData메서드 호출
     */
    public void init() throws IOException {

        String stage = "Stage 1\n#####\n#OoP#\n#####\n=====\n" +
                "Stage 2\n  #######  \n###  O  ###\n#    o    #\n# Oo P oO #\n###  o  ###\n #   O  #  \n ########  \n===";

        inputData(stage);
        for(int k=0; k<mapList.size(); k++) {
            printSokoban(mapList.get(k));
            printMapInfo(mapList.get(k));
        }
    }

    /*
    문자열 데이터를 읽어서 2차원 배열을 생성하고 List<Map> mapList에 add합니다.
     */
    public void inputData(String stage) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(stage.getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        int stageNum=0;
        int height=0;
        int width=0;
        int scanHeight=0; //
        int[][] tmpMap=null;
        int[] size = new int[2];
        String str="";
        while((str = br.readLine())!=null){
            if(str.indexOf("Stage")>-1 ){
                scanHeight = 0; //초기화

                br.mark(100);
                stageNum = Integer.parseInt(str.substring(6));
                size = scanSize(br);
                br.reset();
                height = size[0]+1;
                width = size[1]+1;
                tmpMap = new int[height][width];

                for(int i=0; i<tmpMap.length; i++)
                {
                    Arrays.fill(tmpMap[i], 9);
                }
            } else if(str.indexOf("=")>-1 || str==null){

                Map map = new Map(stageNum, tmpMap);
                mapList.add(map);
                if(str==null) break;
            } else {
                scanHeight++;
                for(int j=1; j<width; j++){
                    tmpMap[scanHeight][j] = ctoi(str.charAt(j-1));

                }
            }
        }
    }

    /*
    각 스테이지의 높이(세로)와 폭(가로)를 구해서 반환합니다.
     */
    private int[] scanSize(BufferedReader br) throws IOException{
        String str="";

        int[] size= new int[2];
        int height=0;
        int width=0;
        while((str = br.readLine())!=null){
            if(str.indexOf("=")>-1){
                size[0] = height;
                size[1] = width;
                return size;
            }
            if(str.indexOf("Stage")==-1 && str.indexOf("=")==-1){
                height++;
                width = Math.max(width, str.length());
            }
        }
        size[0] = height;
        size[1] = width;
        return size;

    }

    /*
    맵을 입력받아서 콘솔에 char형으로 보이게 출력합니다.
     */
    public void printSokoban(Map map){
        System.out.println();

        for(int i=1; i<map.getHeight(); i++){
            for(int j=1; j<map.getWidth(); j++){
                System.out.printf("%c", itoc(map.getMap()[i][j]));
            }
            System.out.println();
        }

    }

    public void printMapInfo(Map map){
        System.out.println();
        System.out.println("가로크기 : "+ (map.getWidth()-1));
        System.out.println("세로크기 : "+ (map.getHeight()-1));
        System.out.println("구멍의 수 : "+ map.getHallNum());
        System.out.println("공의 수 : "+ map.getBallNum());
        System.out.println("플레이어 위치 ("+ map.getPy()+ ", "+ map.getPx()+ ")");
    }

    public Map loadMap(int stageNum) throws IndexOutOfBoundsException{

        return mapList.get(stageNum);
    }

    /*
    스테이지 맵에 int형으로 저장하기위해 char를 int로 변환합니다.
     */
    private int ctoi(char c){
        int ret=0;
        switch(c){
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
            case 'Q':
                ret = 6;
            case ' ':
                ret = 9;
                break;
            default:
                throw new IllegalArgumentException("잘못된 입력입니다");

        }

        return ret;
    }

    private int itoc(int i){
        char ret='0';
        switch(i){
            case 0:
                ret = '#';
                break;
            case 1:
                ret = 'O';
                break;
            case 2:
                ret = 'o';
                break;
            case 3:
                ret = 'P';
                break;
            case 4:
                ret = '=';
                break;
            case 5:
                ret = '0';
                break;
            case 6:
                ret = 'Q';
                break;
            case 9:
                ret = ' ';
                break;

            default:
                throw new IllegalArgumentException("잘못된 입력입니다");

        }

        return ret;
    }



}
