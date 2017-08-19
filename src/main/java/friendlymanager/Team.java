package friendlymanager;

import java.util.ArrayList;

/**
 * Created by Chris on 18.08.2017.
 */
public class Team {
    private int teamID;
    private String managername=null, identifier=null;


    public Team(String identifier, int teamID, String managername) {
        this.teamID = teamID;
        this.managername = managername;
        this.identifier=identifier;
    }

    public int getTeamID() {
        return teamID;
    }

    public String getManagername() {
        return managername;
    }

    public String getIdentifier(){
        return identifier;
    }




}
