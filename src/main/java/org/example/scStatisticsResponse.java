package org.example;

import java.util.Map;

/**
 * Response reader class for the Soccer API
 */
public class scStatisticsResponse {
    public Response response;

    public scStatisticsResponse(Response response) {
        this.response = response;
    }

    /**
     * Getter for the response built in the Response class
     *
     * @return Response object containing the team, fixtures, and goals data.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Contains the getters and setters for information needed
     * to Build response
     */
    public static class Response {
        private Map<String, Object> team;
        private Map<String, Object> fixtures;
        private Map<String, Object> goals;



        public Map<String, Object> getTeam() {
            return team;
        }

        public void setTeam(Map<String, Object> team) {
            this.team = team;
        }

        public Map<String, Object> getFixtures() {
            return fixtures;
        }

        public void setFixtures(Map<String, Object> fixtures) {
            this.fixtures = fixtures;
        }

        public Map<String, Object> getGoals() {
            return goals;
        }

        public void setGoals(Map<String, Object> goals) {
            this.goals = goals;
        }
    }
}
