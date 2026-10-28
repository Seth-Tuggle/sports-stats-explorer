package org.example;

import java.util.Map;

/**
 * Response reader class for the Basketball API
 */
public class StatisticsResponse {
    public Response response;


    public StatisticsResponse(Response response) {
        this.response = response;

    }

    /**
     * getter for the response built in the Response class
     *
     * @return response object containing the team, fixtures, and goals data.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Contains the getters and setters for information needed
     * to build response
     */
    public class Response {

        private Map<String, Object> team;
        private Map<String, Object> games;
        private Map<String, Object> points;


        public Map<String, Object> getTeam() {
            return team;
        }

        public void setTeam(Map<String, Object> team) {
            this.team = team;
        }

        public Map<String, Object> getGames() {
            return games;
        }

        public void setGames(Map<String, Object> games) {
            this.games = games;
        }

        public Map<String, Object> getPoints() {
            return points;
        }

        public void setPoints(Map<String, Object> points) {
            this.points = points;
        }
    }
}