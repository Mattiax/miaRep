
-----------------------------------com.example.Example.java-----------------------------------

package com.example;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

@SerializedName("name")
@Expose
private String name;
@SerializedName("rounds")
@Expose
private List<Round> rounds = null;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public List<Round> getRounds() {
return rounds;
}

public void setRounds(List<Round> rounds) {
this.rounds = rounds;
}

}
-----------------------------------com.example.Match.java-----------------------------------

package com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match {

@SerializedName("date")
@Expose
private String date;
@SerializedName("team1")
@Expose
private Team1 team1;
@SerializedName("team2")
@Expose
private Team2 team2;
@SerializedName("score1")
@Expose
private Integer score1;
@SerializedName("score2")
@Expose
private Integer score2;

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public Team1 getTeam1() {
return team1;
}

public void setTeam1(Team1 team1) {
this.team1 = team1;
}

public Team2 getTeam2() {
return team2;
}

public void setTeam2(Team2 team2) {
this.team2 = team2;
}

public Integer getScore1() {
return score1;
}

public void setScore1(Integer score1) {
this.score1 = score1;
}

public Integer getScore2() {
return score2;
}

public void setScore2(Integer score2) {
this.score2 = score2;
}

}
-----------------------------------com.example.Round.java-----------------------------------

package com.example;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Round {

@SerializedName("name")
@Expose
private String name;
@SerializedName("matches")
@Expose
private List<Match> matches = null;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public List<Match> getMatches() {
return matches;
}

public void setMatches(List<Match> matches) {
this.matches = matches;
}

}
-----------------------------------com.example.Team1.java-----------------------------------

package com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team1 {

@SerializedName("key")
@Expose
private String key;
@SerializedName("name")
@Expose
private String name;
@SerializedName("code")
@Expose
private String code;

public String getKey() {
return key;
}

public void setKey(String key) {
this.key = key;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

}
-----------------------------------com.example.Team2.java-----------------------------------

package com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team2 {

@SerializedName("key")
@Expose
private String key;
@SerializedName("name")
@Expose
private String name;
@SerializedName("code")
@Expose
private String code;

public String getKey() {
return key;
}

public void setKey(String key) {
this.key = key;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}
}
