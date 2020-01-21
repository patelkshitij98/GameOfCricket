package com.example.my_classes;

public class Helper {

    /*Wide, No balls, free hits, extra runs for overthrow, Run out are not considered*/
    public static void play_inning_randomly(Team team_batting, Team team_bowling, int total_overs, int target) {
        int range_of_runs = 8;  // { range_of_runs = [0,7] } ==>> [[ 0-6 for runs, 7 is for wicket ]]
        int player_on_strike = 0, player_on_non_strike = 1, player_next = 2, current_over = 0;

        int bowler_index = 5,range_of_bowler_index=6;
        Player[] batting_players = team_batting.getPlayers_list();
        Player[] bowling_players = team_bowling.getPlayers_list();

        //Run this loop till overs are left and target is not achieved with wickets in hand
        while (current_over <= total_overs && team_batting.getScore() < target && team_batting.getWickets_lost()<10) {
            int t = ((int) (Math.random() * range_of_bowler_index))+ 5;
            if(bowler_index == t){
                bowler_index = (bowler_index + 1)%11;
                if(bowler_index<=0 && bowler_index<=4) bowler_index+=5;
            }
            else bowler_index = t;
            int runs_in_current_over = 0;

            //Iterate for 6 balls in an over
            for (int i = 0; i < 6 && team_batting.getScore() < target; i++) {
                int runs_on_ball = (int) (Math.random() * range_of_runs);
                if (runs_on_ball == 7) {
                    team_batting.setWickets_lost(team_batting.getWickets_lost()+1); //Wicket is fallen
                    //increment balls played by batsman
                    batting_players[player_on_strike].setNoOfBalls_Played(batting_players[player_on_strike].getNoOfBalls_Played()+1);

                    player_on_strike = player_next;  //new player comes on strike
                    player_next++;

                    //increment wickets taken by a bowler
                    bowling_players[bowler_index].setNoOfWickets_Taken(bowling_players[bowler_index].getNoOfWickets_Taken() + 1);

                    //if team is all-out break out of loop
                    if (team_batting.getWickets_lost() == 10) break;
                } else {

                    //increment runs score by batsman along with # of balls played, batting team score, runs given by bowler
                    batting_players[player_on_strike].setNoOfRuns_Scored(batting_players[player_on_strike].getNoOfRuns_Scored()+runs_on_ball);

                    batting_players[player_on_strike].setNoOfBalls_Played(batting_players[player_on_strike].getNoOfBalls_Played()+1);

                    team_batting.setScore(team_batting.getScore() + runs_on_ball);

                    bowling_players[bowler_index].setNoOfRuns_Given(bowling_players[bowler_index].getNoOfRuns_Given() + runs_on_ball);
                    runs_in_current_over += runs_on_ball;
                    if (runs_on_ball % 2 == 1) {           //swap players as strike changes
                        int temp = player_on_strike;
                        player_on_strike = player_on_non_strike;
                        player_on_non_strike = temp;
                    }
                    else if(runs_on_ball==4 || runs_on_ball==6){
                        //increment boundaries scored by batsman
                        batting_players[player_on_strike].setNoOfBoundaries(batting_players[player_on_strike].getNoOfBoundaries()+1);
                    }
                }


            }

            //swap players as strike changes
            int temp = player_on_strike;
            player_on_strike = player_on_non_strike;
            player_on_non_strike = temp;

            if (runs_in_current_over == 0) {
                //increment maiden overs by the bowler
                bowling_players[bowler_index].setNoOfMaiden_Overs(bowling_players[bowler_index].getNoOfMaiden_Overs() + 1);
            }

            bowling_players[bowler_index].setNoOfOvers(bowling_players[bowler_index].getNoOfOvers() + 1);

            current_over++;
        }

    }

    /*For console output only!     To use: uncomment Helper.print_me on play_random() function in Match class*/
    /*
    public static void print_me(Team team_batting, Team team_bowling, int inning) {
        System.out.println("=========== "+team_batting.score +" /"+team_batting.wickets_lost+" ==========");
        System.out.println("Batting Scorecard, Inning "+inning);
        System.out.println("Player    RunsScored    Balls   Boundaries");
        for(int i=0;i<11;i++){
            Player batsman = team_batting.players_list[i];
            System.out.println("Player:"+ (i+1) +" "+batsman.noOfRuns_Scored + " "+batsman.noOfBalls_Played+ " " +batsman.noOfBoundaries);

        }
        System.out.println("Bowling Scorecard, Inning "+inning);
        System.out.println("Player   Overs    RunsGiven    Wickets   Maiden");
        for(int i=0;i<11;i++){
            Player bowler = team_bowling.players_list[i];
            System.out.println("Player:"+ (i+1) +" "+bowler.noOfOvers + " "+bowler.noOfRuns_Given+ " " +bowler.noOfWickets_Taken+" "+bowler.noOfMaiden_Overs);

        }
    }
*/


    //Function to return the html output in string format
    public static String getScoreCardHtml(Match m){
        Team team1 = m.getTeam1();
        Team team2 = m.getTeam2();
        String s = "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<title>Match Result</title>\n" +
                "</head>\n" +
                "<body>" +
                "<h1>Scorecard</h1>\n" +
                "MatchResult:<br>" +
                "Team1 = '" + team1.getName() + "'," +
                " Score = " + team1.getScore() + "/" + team1.getWickets_lost() + "<br>"+
                "Team2 = '" + team2.getName() + "'," +
                " Score = " + team2.getScore() + "/" + team2.getWickets_lost() + "<br>"+
                "<b>Result = " + m.getResult() +
                "</b> "+
                "\n" +
                "<h2>Inning: 1</h2>"+
                "<h3>Batting Team : "+team1.getName()+"</h3>"+
                " Score = " + team1.getScore() + "/" + team1.getWickets_lost() + "<br>"+
                "\t\t\t<table style=\"with: 50%\">\n"+
                "<tr>\n" +
                "                <th>Player</th>\n" +
                "                <th>RunsScored</th>\n" +
                "                <th>Balls</th>\n" +
                "                <th>Boundaries(4's and 6's)</th>\n" +
                "                <th>Strike Rate</th>\n" +
                "              </tr>";
        StringBuilder str1 = new StringBuilder();
        for(int i=0;i<11;i++){
            Player batsman = team1.getPlayers_list()[i];
            int score = batsman.getNoOfRuns_Scored(),balls = batsman.getNoOfBalls_Played();
            str1.append("<tr><td>Player:").append(i + 1).append("</td> <td>").append(score).append("</td> <td>").append(balls).append("</td> <td>").append(batsman.getNoOfBoundaries()).append("</td><td>").append((balls==0)?"-":(((float)score/balls))*100).append("</td></tr>");

        }
        s= s + str1 +"</table><h3>Bowling Team : "+team2.getName()+" </h3>";
        s+="\t\t\t<table style=\"with: 50%\">\n"+
                "<tr>\n" +
                "                <th>Player</th>\n" +
                "                <th>Overs</th>\n" +
                "                <th>RunsGiven</th>\n" +
                "                <th>Wickets</th>\n" +
                "                <th>Maidens</th>\n" +
                "                <th>Economy</th>\n" +
                "              </tr>";
        StringBuilder str2 = new StringBuilder();
        for(int i=0;i<11;i++){
            Player bowler = team2.getPlayers_list()[i];
            int overs = bowler.getNoOfOvers(),runs_given = bowler.getNoOfRuns_Given();
            str2.append("<tr><td>Player:").append(i + 1).append("</td> <td>").append(overs).append("</td> <td>").append(runs_given).append("</td> <td>").append(bowler.getNoOfWickets_Taken()).append("</td><td>").append(bowler.getNoOfMaiden_Overs()).append("</td><td>").append((overs==0)?"-":((float)runs_given/overs)).append("</td></tr>");

        }
        s+=  str2+     "\t\t\t\t</table>\n";
        s+="<h2>Inning: 2</h2>"+
                "<h3>Batting Team : "+team2.getName()+ "</h3>"+
                " Score = " + team2.getScore() + "/" + team2.getWickets_lost() + "<br>"+
                "\t\t\t<table style=\"with: 50%\">\n"+
                "<tr>\n" +
                "                <th>Player</th>\n" +
                "                <th>RunsScored</th>\n" +
                "                <th>Balls</th>\n" +
                "                <th>Boundaries</th>\n" +
                "                <th>Strike Rate</th>\n" +
                "              </tr>";
        StringBuilder str3 = new StringBuilder();
        for(int i=0;i<11;i++){
            Player batsman = team2.getPlayers_list()[i];
            int score = batsman.getNoOfRuns_Scored(),balls = batsman.getNoOfBalls_Played();
            str3.append("<tr><td>Player:").append(i + 1).append("</td> <td>").append(score).append("</td> <td>").append(balls).append("</td> <td>").append(batsman.getNoOfBoundaries()).append("</td><td>").append((balls==0)?"-":(((float)score/balls))*100).append("</td></tr>");

        }
        s+= str3 + "</table><h3>Bowling Team :"+team1.getName()+"</h3>";
        s+="\t\t\t<table style=\"with: 50%\">\n"+
                "<tr>\n" +
                "                <th>Player</th>\n" +
                "                <th>Overs</th>\n" +
                "                <th>RunsGiven</th>\n" +
                "                <th>Wickets</th>\n" +
                "                <th>Maidens</th>\n" +
                "                <th>Economy</th>\n" +
                "              </tr>";
        StringBuilder str4 = new StringBuilder();
        for(int i=0;i<11;i++){
            Player bowler = team1.getPlayers_list()[i];
            int overs = bowler.getNoOfOvers(),runs_given = bowler.getNoOfRuns_Given();
            str4.append("<tr><td>Player:").append(i + 1).append("</td> <td>").append(overs).append("</td> <td>").append(runs_given).append("</td> <td>").append(bowler.getNoOfWickets_Taken()).append("</td><td>").append(bowler.getNoOfMaiden_Overs()).append("</td><td>").append((overs==0)?"-":((float)runs_given/overs)).append("</td></tr>");

        }
        s+= str4 + "\t\t\t\t</table>\n" +

                "\n" +
                "</body>\n" +
                "</html>";
        return s;
    }

}
