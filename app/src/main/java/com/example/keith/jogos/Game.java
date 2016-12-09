package com.example.keith.jogos; /**
 * Created by keith on 08/12/2016.
 */
import java.util.ArrayList;

public class Game {
    private String name;
    private String image;
    private String release_date;
    private String trailer;
    private ArrayList<String> platforms;

    public Game(){
        platforms = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public void addPlatforms(String platform) {
        this.platforms.add(platform);
    }
}
