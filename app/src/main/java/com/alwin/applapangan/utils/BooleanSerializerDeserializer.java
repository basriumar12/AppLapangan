package com.alwin.applapangan.utils;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by bradhawk on 10/29/2016.
 */

public class BooleanSerializerDeserializer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
    @Override
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src ? 1 : 0);
    }

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String parse = json.getAsString();
        return (parse.equalsIgnoreCase("true") || parse.equalsIgnoreCase("1"));

    }
}
