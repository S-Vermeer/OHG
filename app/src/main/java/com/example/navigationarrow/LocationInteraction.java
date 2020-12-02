import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

public class LocationInteraction {
    private LocationRequest locationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;

    public LocationInteraction(){

    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        mLocationSettingsRequest = builder.build();
    }
}
