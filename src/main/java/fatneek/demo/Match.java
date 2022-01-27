package fatneek.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Match {


    private final String id;
    private String[] summonerNames;
    private Summoner s;
    private String key = Main.getKey();
    private String bigReg;
    private int gameTime;
    private String[] championName;
    private String[] champLevel;
    private int[] kills;
    private int[] deaths;
    private int[] assists;
    private int[][] items;
    private String[] kda;
    private int[][] summs;
    private int[] visionScore;
    private int[] cs;
    private int[] totalDc;
    private String win;
    private String sumKills;
    private String sumDeaths;
    private String sumAssists;
    private String sumChampName;
    private String sumHighestKillStreak;
    private String sumSumm1Id;
    private String sumSumm2Id;
    private String sumPos;

    public Match(String id, Summoner s){
        this.id = id;
        this.bigReg = s.getBigReg();
        this.s = s;

       // getMatchData();
        JSONObject json;
        try {
            json = JsonReader.readJsonFromUrl("https://" + bigReg + ".api.riotgames.com/lol/match/v5/matches/" + id + "?api_key=" + key);
            //System.out.println((int)json.getJSONObject("info").get("queueId"));

            String[] summonerNames = new String[10];
            String[] championName = new String[10];
            String[] champLevel = new String[10];
            int[] kills = new int[10];
            int[] deaths = new int[10];
            int[] assists = new int[10];
            int[][] items = new int[10][7];
            String[] kda = new String[10];
            int[][] summs = new int[10][2];
            int[] visionScore = new int[10];
            int[] cs = new int[10];
            int[] totalDC = new int[10];
            String win;
            //MORE STATS TO BE ADDED
            for (int i = 0; i < 10; i++) {
                this.gameTime = (int)json.getJSONObject("info").get("gameDuration");
                JSONObject tmp = json.getJSONObject("info").getJSONArray("participants").getJSONObject(i);
                if(tmp.get("summonerName").equals(s.getName())) {
                    if ((boolean) json.getJSONObject("info").getJSONArray("participants").getJSONObject(i).get("win")) {
                        this.win = "Win";
                    } else {
                        this.win = "Loss";
                    }
                }
                summonerNames[i] = tmp.getString("summonerName");
                kills[i] = (int) tmp.get("kills");
                deaths[i] = (int) tmp.get("deaths");
                assists[i] = (int) tmp.get("assists");
                championName[i] = tmp.getString("championName");
                for (int j = 0; j < 7; j++) {
                    String temp = "item" + j;
                    items[i][j] = (int) tmp.get(temp);
                }
                if(deaths[i] == 0){
                    kda[i] = "Perfect";
                }else {
                    kda[i] = "" + ((kills[i] + assists[i]) /(double) deaths[i]);
                }
                for (int j = 0; j < 2; j++) {
                    summs[i][j] = (int) tmp.get("summoner" + (j + 1) + "Id");
                }
                visionScore[i] = (int) tmp.get("visionScore");
                cs[i] = (int) tmp.get("neutralMinionsKilled") + (int) tmp.get("totalMinionsKilled");
                totalDC[i] = (int) tmp.get("totalDamageDealtToChampions");
                //MORE STATS TO BE ADDED

                this.summonerNames = summonerNames;
                this.championName = championName;
                this.kills = kills;
                this.deaths = deaths;
                this.assists = assists;
                this.items = items;
                this.champLevel = champLevel;
                this.kda = kda;
                this.summs = summs;
                this.visionScore = visionScore;
                this.cs = cs;
                this.totalDc = totalDC;
            }
        } catch (JSONException | IOException e) {
            throw new java.lang.IllegalArgumentException("Smth went wrong:"+e.fillInStackTrace());
        }


        String[] sumData = getSummonerData(id);
        this.sumAssists = sumData[3];
        this.sumKills = sumData[1];
        this.sumDeaths = sumData[2];
        this.sumChampName = sumData[5];
        this.sumPos = sumData[6];
        this.sumSumm1Id = sumData[7];
        this.sumSumm2Id = sumData[8];
        String multikill = "";
        if(!sumData[12].equals("0")){
            multikill = "Penta Kill!";
        }else if(!sumData[11].equals("0")){
            multikill = "Quadra Kill!";
        }else if(!sumData[10].equals("0")){
            multikill = "Triple Kill!";
        }else if(!sumData[9].equals("0")){
            multikill = "Double Kill!";
        }
        this.sumHighestKillStreak = multikill;
    }

    public String[] getSummonerData(String matchId){
        String [] data = new String[14];
        JSONObject json;
        try {
            json = JsonReader.readJsonFromUrl("https://"+bigReg+".api.riotgames.com/lol/match/v5/matches/"+matchId+"?api_key="+key);
            //System.out.println((int)json.getJSONObject("info").get("queueId"));
            int queueId=(int)json.getJSONObject("info").get("queueId");
            if(queueId == 420){
                data[13] = "Ranked Solo/Duo";
            }else if(queueId==440){
                data[13] = "Ranked Flex";
            }else if(queueId==450){
                data[13] = "ARAM";
            }else if(queueId==700){
                data[13] = "Clash";
            }else if(queueId==400){
                data[13] = "Draft Pick";
            }else if(queueId==430){
                data[13] = "Blind Pick";
            }else if(queueId==400){
                data[13] = "Draft Pick";
            }else{
                data[13]="Special";
            }
            for(int i = 0; i<10;i++){
                JSONObject tmp = json.getJSONObject("info").getJSONArray("participants").getJSONObject(i);
                if(tmp.get("summonerName").equals(s.getName())){
                    if((boolean)json.getJSONObject("info").getJSONArray("participants").getJSONObject(i).get("win")){
                        data[0] = "Win"; //win/loss
                    }else {
                        data[0] = "Loss";
                    }
                    data[1] = ""+(int)tmp.get("kills"); //kills
                    data[2] = ""+(int)tmp.get("deaths"); //deaths
                    data[3] = ""+(int)tmp.get("assists");
                    data[4] = ""+(int)tmp.get("champLevel");
                    data[5] = (String)tmp.get("championName");
                    data[6] = (String)tmp.get("teamPosition");
                    data[7] = ""+(int)tmp.get("summoner1Id");
                    data[8] = ""+(int)tmp.get("summoner2Id");
                    data[9] = ""+(int)tmp.get("doubleKills");
                    data[10] = ""+(int)tmp.get("tripleKills");
                    data[11] = ""+(int)tmp.get("quadraKills");
                    data[12] = ""+(int)tmp.get("pentaKills");
                    //runes?
                }
            }
            return data;

        } catch (JSONException | IOException e) {
            throw new java.lang.IllegalArgumentException("Smth went wrong:");
        }
    }

    public String toString(){
        String []data = getSummonerData(id);
        String multikill ="";
        if(!data[12].equals("0")){
            multikill = "Penta Kill!";
        }else if(!data[11].equals("0")){
            multikill = "Quadra Kill!";
        }else if(!data[10].equals("0")){
            multikill = "Triple Kill!";
        }else if(!data[9].equals("0")){
            multikill = "Double Kill!";
        }
        return data[0]+" "+data[13]+" Champion: "+data[5]+" Role: "+data[6]+" Lvl"+data[4]+ " K/D/A: "+data[1]+"/"+data[2]+"/"+data[3]+" "+multikill;
    }

    public String[] getSummonerNames(){
        return this.summonerNames;
    }
    public String[] getChampionName(){
        return championName;
    }
    public String[] getChampLevel(){
        return champLevel;
    }
    public int[] getKills(){
        return kills;
    }
    public int[] getAssists(){
        return assists;
    }
    public int[] getDeaths(){
        return deaths;
    }
    public int[] getCs(){
        return cs;
    }
    public int[] getVisionScore(){
        return visionScore;
    }
    public int[] getTotalDc(){
        return totalDc;
    }
    public String getWin(){
        return win;
    }
    public int getGameTime(){
        return gameTime;
    }
    public int[][] getSumms(){
        return summs;
    }
    public int [][] getItems(){
        return items;
    }
    public String getSumKills(){
        return sumKills;
    }
    public String getSumDeaths(){
        return sumDeaths;
    }
    public String getSumAssists(){
        return sumAssists;
    }
    public String getSumChampName(){
        return sumChampName;
    }
    public String getSumHighestKillStreak(){
        return sumHighestKillStreak;
    }
    public String getSumPos(){
        return sumPos;
    }
    public String getSumSumm1Id(){
        return sumSumm1Id;
    }
    public String getSumSumm2Id(){
        return sumSumm2Id;
    }
    public String[] getKda(){
        return kda;
    }

    public String toStringMatchData(Match m){
        StringBuilder sb = new StringBuilder();
        for(int j =0; j<10;j++) {
            sb.append(getSummonerNames()[j]+" ");
            sb.append(getChampionName()[j]+" ");
            sb.append(getKills()[j]+"/"+getDeaths()[j]+"/"+getAssists()[j]+" Kda: "+getKda()[j]);
            double gameTimeInMin = getGameTime()/(double)60;
            sb.append(" Cs/min: "+(double)Math.round((getCs()[j]/gameTimeInMin)*10d)/10d+" ");
            sb.append("Summoner Ids: "+getSumms()[j][0]+" "+getSumms()[j][1]+" ");
            sb.append(System.lineSeparator());
            sb.append("Item ids: "+getItems()[j][0]+" "+getItems()[j][1]+" "+getItems()[j][2]+" "+getItems()[j][3]+" "+getItems()[j][4]+" "+getItems()[j][5]+" "+getItems()[j][6]+" ");
            sb.append(System.lineSeparator());
            sb.append("Total Damage to champions: "+getTotalDc()[j]);
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}