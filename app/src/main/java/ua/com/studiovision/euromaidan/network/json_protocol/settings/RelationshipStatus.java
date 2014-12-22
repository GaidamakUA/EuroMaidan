package ua.com.studiovision.euromaidan.network.json_protocol.settings;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import ua.com.studiovision.euromaidan.R;

public enum RelationshipStatus {

    @SerializedName("0")
    NONE(-1),
    @SerializedName("1")
    SINGLE(R.id.relationship_single_radio_button),
    @SerializedName("2")
    DATING(R.id.relationshop_dating_radio_button),
    @SerializedName("3")
    ENGAGED(R.id.relationship_engaged_radio_button),
    @SerializedName("4")
    MARRIED(R.id.relationship_married_radio_button),
    @SerializedName("5")
    IN_LOVE(R.id.relationship_in_love_radio_button),
    @SerializedName("6")
    COMPLICATED(R.id.relationship_complicated_radio_button),
    @SerializedName("7")
    IN_ACTIVE_SEARCH(R.id.relationship_active_search_radio_button);

    private static final Map<Integer, RelationshipStatus> map =
            new HashMap<Integer, RelationshipStatus>();
    static {
        map.put(-1, NONE);
        map.put(R.id.relationship_single_radio_button, SINGLE);
        map.put(R.id.relationshop_dating_radio_button, DATING);
        map.put(R.id.relationship_engaged_radio_button, ENGAGED);
        map.put(R.id.relationship_married_radio_button, MARRIED);
        map.put(R.id.relationship_in_love_radio_button, IN_LOVE);
        map.put(R.id.relationship_complicated_radio_button, COMPLICATED);
        map.put(R.id.relationship_active_search_radio_button, IN_ACTIVE_SEARCH);
    }
    private int id;
    RelationshipStatus(int id) {this.id = id;}

    public static RelationshipStatus getRelationshipStatus(int id) {
        return map.get(id);
    }
    public int getId() {
        return id;
    }
}