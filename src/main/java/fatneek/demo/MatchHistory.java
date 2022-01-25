package fatneek.demo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

public class MatchHistory {
    private String key = "RGAPI-91eb14e9-5317-4aaf-9e9d-69434e9862e5";
    private Match[] match;

    public MatchHistory(){

    }

    public MatchHistory(Summoner s) throws JSONException, IOException {
        LinkedList<String> matches = s.getMatchIds();
        Match [] matchArr = new Match[matches.size()];
        for(int i =0; i<matches.size();i++){
            matchArr[i] = new Match(matches.get(i), s);
        }
        this.match = matchArr;
    }
    public String toString(){
        String matches="";
        for (int i = 0;i<match.length;i++){
            matches = matches+this.match[i]+"\r\n";
        }
        return matches;
    }
}


