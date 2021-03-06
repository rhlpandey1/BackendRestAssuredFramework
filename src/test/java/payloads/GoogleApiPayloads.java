package payloads;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.rahul.pojos.AddPlace;
import org.rahul.pojos.Location;

import java.util.ArrayList;
import java.util.List;

public class GoogleApiPayloads {
    public static AddPlace setAddPlaceValues(String name,String language,String address){
        AddPlace addPlace=new AddPlace();
        addPlace.setAddress(address);
        addPlace.setAccuracy(100);
        addPlace.setLanguage(language);
        Location location=new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);
        if(name!=null)
          addPlace.setName(name);
        addPlace.setPhone_number("1234567890");
        addPlace.setWebsite("www.google.com");
        List<String> types=new ArrayList<>();
        types.add("shop");
        addPlace.setTypes(types);
       /* Gson gson=new GsonBuilder().serializeNulls().create();
        String resp=gson.toJson(addPlace);*/
        return addPlace;
    }
}
