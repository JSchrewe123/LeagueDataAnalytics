package fatneek.demo;

public class Player {
    
    String playerName;
    String server;
    int numWins;
    int numLosses;

    Player(){
    }

    Player(String playerName, String server, int numWins, int numLosses){
        this.playerName = playerName;
        this.server = server;
        this.numWins = numWins;
        this.numLosses = numLosses;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getServer(){
        return server;
    }

    public void setServer(String server){
        this.server = server;
    }

    public int getNumWins(){
        return numWins;
    }

    public void setNumWins(int numWins){
        this.numWins = numWins;
    }

    public int getNumLosses(){
        return numLosses;
    }

    public void setNumLosses(int numLosses){
        this.numLosses = numLosses;
    }

    @Override
    public String toString(){
        return "Name: " +  this.playerName + "Server: " + this.server + "Wins: " + this.numWins + "Losses: " + this.numLosses;
    }

}
