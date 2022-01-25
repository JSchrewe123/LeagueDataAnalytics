package fatneek.demo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Match {


    private final String id;
    private Summoner s;
    private String key = "RGAPI-91eb14e9-5317-4aaf-9e9d-69434e9862e5";
    private String bigReg;
    private boolean win;
    private String gameTime;
    private String[] matchIds;

    public Match(String id, Summoner s){
        this.id = id;
        this.bigReg = s.getBigReg();
        this.s = s;
       // String[] data = getSummonerData(id);

    }

    public String[] getSummonerData(String matchId){
        String [] data = new String[14];
        JSONObject json;
        try {
            json = JsonReader.readJsonFromUrl("https://"+bigReg+".api.riotgames.com/lol/match/v5/matches/"+matchId+"?api_key="+key);
           int queueId=(int)json.getJSONObject("info").get("queueId");
           if(queueId == 400){
               data[13] = "Ranked Solo/Duo";
           }else if(queueId==440){
               data[13] = "Ranked Flex";
           }else if(queueId==450){
               data[13] = "ARAM";
           }else if(queueId==700){
               data[13] = "Clash";
           }else{
               data[13] = "Normal";
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
        return data[13]+" Champion: "+data[5]+" Role: "+data[6]+" Lvl"+data[4]+ " K/D/A: "+data[1]+"/"+data[2]+"/"+data[3]+" "+multikill;
    }


}
