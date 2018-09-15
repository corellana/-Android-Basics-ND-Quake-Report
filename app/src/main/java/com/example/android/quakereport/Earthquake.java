package com.example.android.quakereport;

/**
 * {@link Earthquake} represents a Info Earthquake that the user wants to know.
 * It contains mag, place and date for that earthquake
 */

public class Earthquake {

    /*
     * STATE m indicate they are variables of the class
     */

    /** Magnitude for the earthquake */
    private double mMagnitude;

    private double mMagnitudeColor;

    /** Time of the earthquake */
    private long mTimeInMilliseconds;

    /** URL for the earthquake */
    private String mUrl;

    /**
     * CONSTRUCTOR
     * Create a new Earthquake object.
     *
     * @param magnitude is the ssss for the earthquake.
     * @param location     is the most close city/town of the earthquake.
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the earthquake
     *        happened.
     * @param url for each earthquake
     *
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;

    }

    /** METHODS */

    /** Get the magnitude  of the earthquake */
    public double getMagnitude() {
        return mMagnitude;
    }

    // TODO: Your Turn Finish implementing this design change by defining a private helper method
// called getMagnitudeColor(double magnitude) that returns the correct color value based on the
// current earthquake’s magnitude value. Use this opportunity to practice writing a switch
// statement in the helper method.

//    /** Get the magnitude color of the earthquake */
//    private int GetMagnitudeColor(){
//        return mMagnitudeColor;
//    }

    /** Location of the earthquake */
    private String mLocation;

    /** Get the date of the earthquake */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    /** Get the url  of the earthquake */
    public String getUrl() {
        return mUrl;
    }

    /** Returns the location of the earthquake */
    public String getLocation() {
        return mLocation;
    }
}

// TODO: Your Turn Finish implementing this design change by defining a private helper method
// called getMagnitudeColor(double magnitude) that returns the correct color value based on the
// current earthquake’s magnitude value. Use this opportunity to practice writing a switch
// statement in the helper method.