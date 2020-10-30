package vasiliyloginov.weatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {
    private String temperatureValue ;
    private String cityName ;

    public String getTemperatureValue() {
        return temperatureValue ;
    }
    public String getCityName() {
        return cityName ;
    }
    public Parcel( String temperatureValue, String cityName) {
        this.temperatureValue = temperatureValue;
        this.cityName = cityName;

    }
}
