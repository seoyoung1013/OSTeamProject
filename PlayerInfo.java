public class PlayerInfo {
<<<<<<< sangil
    String name;
    int score;

    PlayerInfo(String name, int score){
        this.name = name;
        this.score = score;
    }

    public int compareTo(PlayerInfo playerInfo) {
        if(playerInfo.score>this.score)
            return 1;
        else
            return 0;
    }

    public void printInfo() {
        System.out.println("Name: " + name + ", SCORE: " + score);
    }
    public String getName() {
        return name;
    }
    public int get() {
        return score;
    }
}
=======
  String name;
  int score;

  PlayerInfo(String name, int score){
      this.name = name;
      this.score = score;
  }

  public int compareTo(PlayerInfo playerInfo) {
      if(playerInfo.score>this.score)
          return 1;
      else
          return 0;
  }

  public void printInfo() {
      System.out.println("Name: " + name + ", SCORE: " + score);
  }
  public String getName() {
      return name;
  }
  public int get() {
      return score;
  }
}
>>>>>>> main
