package com.netflix_clone.movieservice.component.enums;

import lombok.Getter;

public abstract class Rabbit {
//    @Getter
//    public enum Exchange {
//        PROFILE("netflix-clone-profile");
//
//        private String value;
//        Exchange(String value) { this.value = value; }
//    }
    @Getter
    public enum RoutingKey {
        PROFILE_SAVE("profile.save"), PROFILE_CHANGE("profile.change");
        private String value;

        RoutingKey(String name){ this.value = name; }
    }

    @Getter
    public enum Queue {
        MOVIE("netflix-clone-movie");
        private String name;

        Queue(String name){ this.name = name; }

    }
    @Getter
    public enum Topic {
        PROFILE("netflix-clone-movie");

        private String name;
        Topic(String name){ this.name = name; }
    }
}
