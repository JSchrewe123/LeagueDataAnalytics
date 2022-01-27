package fatneek.demo;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONException;

public class Main {

    public static void main(String[] args)
            throws IOException, JSONException, NotValidRegionException, NotValidNameException {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter Region: ");
        String reg = s.next();
        System.out.println("Enter Gamer: ");
        s.nextLine();
        String name = s.nextLine();
        String apiName = NameCheck.toUnicode(name);
        Summoner sum = new Summoner(name, reg, apiName);
         //String puuid = sum.getSummonerData("puuid");
         //System.out.println(puuid);
        // String id = sum.getSummonerData("puuid");
        System.out.println(sum);

        MatchHistory mh = new MatchHistory(sum);
        System.out.println(mh);
        Match[]matches = mh.getMatches();
        System.out.println(matches[0].toStringMatchData(matches[0]));
    }
    public static String getKey(){
        return "RGAPI-49321d83-5b12-47ed-88e8-f45cf313fc99";
    }

}