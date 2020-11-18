package com.forkan.devicecurrentlocation;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//public class Snap2Road {
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    List<LatLng> snappedPoints = new ArrayList<>();
//new GetSnappedPointsAsyncTask().execute(polylinePoints, null,snappedPoints);
//
//
//    private class GetSnappedPointsAsyncTask extends AsyncTask<List<LatLng>, Void, List<LatLng>> {
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        protected List<LatLng> doInBackground(List<LatLng>... params) {
//
//            List<LatLng> snappedPoints = new ArrayList<>();
//
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//
//            try {
//                URL url = new URL(buildRequestUrl(params[0]));
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.connect();
//
//                InputStream stream = connection.getInputStream();
//
//                reader = new BufferedReader(new InputStreamReader(stream));
//                StringBuilder jsonStringBuilder = new StringBuilder();
//
//                StringBuffer buffer = new StringBuffer();
//                String line = "";
//
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line + "\n");
//                    jsonStringBuilder.append(line);
//                    jsonStringBuilder.append("\n");
//                }
//
//                JSONObject jsonObject = new JSONObject(jsonStringBuilder.toString());
//                JSONArray snappedPointsArr = jsonObject.getJSONArray("snappedPoints");
//
//                for (int i = 0; i < snappedPointsArr.length(); i++) {
//                    JSONObject snappedPointLocation = ((JSONObject) (snappedPointsArr.get(i))).getJSONObject("location");
//                    double lattitude = snappedPointLocation.getDouble("latitude");
//                    double longitude = snappedPointLocation.getDouble("longitude");
//                    snappedPoints.add(new LatLng(lattitude, longitude));
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return snappedPoints;
//        }
//
//        @Override
//        protected void onPostExecute(List<LatLng> result) {
//            super.onPostExecute(result);
//
//            PolylineOptions polyLineOptions = new PolylineOptions();
//            polyLineOptions.addAll(result);
//            polyLineOptions.width(5);
//            polyLineOptions.color(Color.RED);
//            mGoogleMap.addPolyline(polyLineOptions);
//
//            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//            builder.include(result.get(0));
//            builder.include(result.get(result.size() - 1));
//            LatLngBounds bounds = builder.build();
//            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
//
//        }
//    }
//
//
//    private String buildRequestUrl(List<LatLng> trackPoints) {
//        StringBuilder url = new StringBuilder();
//        url.append("https://roads.googleapis.com/v1/snapToRoads?path=");
//
//        for (LatLng trackPoint : trackPoints) {
//            url.append(String.format("%8.5f", trackPoint.latitude));
//            url.append(",");
//            url.append(String.format("%8.5f", trackPoint.longitude));
//            url.append("|");
//        }
//        url.delete(url.length() - 1, url.length());
//        url.append("&interpolate=true");
//        url.append(String.format("&key=%s", < your_Google_Maps_API_key >);
//
//        return url.toString();
//    }
//}
