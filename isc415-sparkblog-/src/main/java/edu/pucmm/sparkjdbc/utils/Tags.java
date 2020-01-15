package edu.pucmm.sparkjdbc.utils;

import edu.pucmm.sparkjdbc.Models.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Tags {
    public static List<Tag> arrayToTagList(String[] tags){
        List<Tag> tagList = new ArrayList<>();
        for(String tag : tags) tagList.add(new Tag(UUID.randomUUID().toString(), tag));
        return tagList;
    }

    public static Boolean isTagInArray(Tag tag, List<Tag> tags){
        boolean exists = false;
        for(Tag myTag : tags){
            if (!myTag.getTag().equalsIgnoreCase(tag.getTag())) continue;
            exists = true;
        }
        return exists;
    }
}
