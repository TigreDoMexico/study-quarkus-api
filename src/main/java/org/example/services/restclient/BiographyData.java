package org.example.services.restclient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BiographyData {
    @JsonProperty("full-name")
    public String fullName;
    @JsonProperty("place-of-birth")
    public String placeOfBirth;
    public String publisher;
    public String alignment;
}
