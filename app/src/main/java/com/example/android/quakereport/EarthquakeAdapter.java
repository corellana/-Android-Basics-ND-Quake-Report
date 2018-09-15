package com.example.android.quakereport;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;


/*
     * {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
     * based on a data source, which is a list of {@link Earthquake} objects.
     * */
    public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * The part of the location string from the USGS service that we use to determine
     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
     */
    private static final String LOCATION_SEPARATOR = " of ";

        /**
         * CONSTRUCTOR - Constructs/Create a new {@Link EarthquakeAdapter} object.
         *
         * @param context     The current context. Used to inflate the layout file.
         * @param earthquakes A List of earthquake objects to display in a list.
         */
        public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
            super(context, 0, earthquakes);
        }

        /**
         * * METHOD - GETVIEW
         * Provides a view for an AdapterView (ListView, GridView, etc.)
         *
         * Returns a list item view that displays information about the earthquake at the given position
         * in the list of earthquakes.
         *
         * @param position    The position in the list of data that should be displayed in the
         *                    list item view.
         * @param convertView The recycled view to populate.
         * @param parent      The parent ViewGroup that is used for inflation.
         * @return The View for the position in the AdapterView.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // // Check if there is an existing list item view (called convertView) that we can
            // reuse, otherwise, if convertView is null, then inflate a new list item layout.
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.earthquake_list_item, parent, false);
            }

            // Get the {@link Earthquake} object located at this position in the list
            Earthquake currentEarthquake = getItem(position);

            // // Find the TextView with view ID magnitude
            TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);

            // Format the magnitude to show 1 decimal place
            String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
            // Display the magnitude of the current earthquake in that TextView
            magnitudeView.setText(formattedMagnitude);

//          // Get the mag from the current Earthquake object and set this text on the magnitud TextView
//          magnitudTextView.setText(String.valueOf(new Double(currentEarthquake.getMagnitude())));

            // TODO: If you add the above code to your app, you’ll see an error in Android Studio
            // mentioning that it doesn’t recognize the method getMagnitudeColor() because it will
            // be your job to define this method!

            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);

            // Steps Lesson 21.
            // Get the original location string from the Earthquake object,
            // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
            String originalLocation = currentEarthquake.getLocation();

            // If the original location string (i.e. "5km N of Cairo, Egypt") contains
            // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
            // then store the primary location separately from the location offset in 2 Strings,
            // so they can be displayed in 2 TextViews.
            String primaryLocation;
            String locationOffset;

            // In the String array, the 0th element of the array is “74km NW” and the 1st element
            // of the array is “Rumoi, Japan”. Note that we also add the “ of “ text back to
            // the 0th element of the array, so the locationOffset will say “74km NW of “.

            // There is still the issue that some location Strings don’t have a location offset.
            // Hence, we should check if the original location String contains the
            // LOCATION_SEPARATOR first, before we decide to split the string with that separator.
            // If there is no LOCATION_SEPARATOR in the original location String, then we can
            // assume that we should we use the “Near the” text as the location offset, and just
            // use the original location String as the primary location.

            // Check whether the originalLocation string contains the " of " text
            if (originalLocation.contains(LOCATION_SEPARATOR)) {
                // Split the string into different parts (as an array of Strings)
                // based on the " of " text. We expect an array of 2 Strings, where
                // the first String will be "5km N" and the second String will be "Cairo, Egypt".
                String[] parts = originalLocation.split(LOCATION_SEPARATOR);
                // Location offset should be "5km N " + " of " --> "5km N of"
                locationOffset = parts[0] + LOCATION_SEPARATOR;
                // Primary location should be "Cairo, Egypt"
                primaryLocation = parts[1];
            } else {
                // Otherwise, there is no " of " text in the originalLocation string.
                // Hence, set the default location offset to say "Near the".
                locationOffset = getContext().getString(R.string.near_the);
                // The primary location will be the full location string "Pacific-Antarctic Ridge".
                primaryLocation = originalLocation;
            }
            // Once we have the 2 separate Strings, we can display them in the 2 TextViews in
            // the list item layout.

            // Find the TextView with view ID location
            TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
            primaryLocationView.setText(primaryLocation);

            // Find the TextView with view ID location offset
            TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
            // Display the location offset of the current earthquake in that TextView
            locationOffsetView.setText(locationOffset);
            // TODO: fin de los cambios.

            // Create a new Date object from the time in milliseconds of the earthquake
            Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

            // Find the TextView with view ID date
            TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
            // Format the date string (i.e. "Mar 3, 1984")
            String formattedDate = formatDate(dateObject);
            // Display the date of the current earthquake in that TextView
            dateTextView.setText(formattedDate);

            // Find the TextView with view ID time
            TextView timeView = (TextView) listItemView.findViewById(R.id.time);
            // Format the time string (i.e. "4:30PM")
            String formattedTime = formatTime(dateObject);
            // Display the time of the current earthquake in that TextView
            timeView.setText(formattedTime);

            // Return the whole list item layout so that it can be shown in the ListView
            return listItemView;

        }

    /**
     * Return the color for the magnitude circle based on the intensity of the earthquake.
     *
     * @param magnitude of the earthquake
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;

        // floor” of the decimal magnitude value. This means finding the closest integer
        // less than the decimal value. The floor of the value 1.2 would be the integer 1.
        int magnitudeFloor = (int) Math.floor(magnitude);

        // Once we have the magnitude in an integer form (stored in the magnitudeFloor
        // variable), we can write a switch statement that performs different logic based
        // on the magnitudeFloor value.
        switch (magnitudeFloor) {

            //  we set the value of the magnitudeColorResourceId variable to be one of the
            // color resources that we defined the colors.xml file.
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
                //Once we find the right color resource ID, we still have one more step to convert
                //  it into an actual color value.

                // Remember that color resource IDs just point to the resource we defined, but
                // not the value of the color. For example, R.layout.earthquake_list_item is a
                // reference to tell us where the layout is located. It’s just a number, not
                // the full XML layout.

                // For example, R.layout.earthquake_list_item is a reference to tell us where the
                // layout is located. It’s just a number, not the full XML layout.

                // You can call ContextCompat getColor() to convert the color resource ID into an
                // actual integer color value, and return the result as the return value of the
                // getMagnitudeColor() helper method.
                return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
            }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

            /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    }





