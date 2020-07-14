package com.example.socialdis;

public class datavalue {
        private String name;
        private String value;
        private String distance;

        public datavalue(String name, String value, String distance) {
            this.name = name;
            this.value= value;
            this.distance=distance;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
