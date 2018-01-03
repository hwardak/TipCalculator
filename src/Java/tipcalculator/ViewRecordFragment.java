package com.example.hwardak.tipcalculator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.johan.carbonfootprint.CarbonFootprintDBAdapter;
import com.example.johan.carbonfootprint.CarbonFootprintMainActivity;
import com.example.tgk.integrationwithfragment.R;

/**
 * Created by HWardak on 16-04-20.
 */
public class ViewRecordFragment extends ListFragment {



    TipCalculatorDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    OnTiptSelectedListener mCallback;

    /**
     * interface that has to be implemented
     */
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnTiptSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onTipSelected(int position, long id);
    }



    /**
     * on create
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;


        // Create an array adapter for the list view, using the Ipsum headlines array
        // setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));

        dbHelper = new TipCalculatorDbAdapter(getActivity());
        dbHelper.open();

        //Generate ListView from SQLite Database
        displayListView();

    }


    /**
     * Sets up a ListView with a SimpleCursorAdapter and a Cursor with all the rows
     * from the database table.  Also sets up the handler for when an item is selected.
     */
    private void displayListView() {

        // The desired columns to be bound
        String[] columns = new String[]{
               TipCalculatorDbAdapter.KEY_NAME,
                TipCalculatorDbAdapter.KEY_TIPP,
                TipCalculatorDbAdapter.KEY_TIPD,
                TipCalculatorDbAdapter.KEY_TOTAL,


        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
               // R.id.rowid,
                R.id.name,
                R.id.tipp,
                R.id.tipd,
                R.id.total,
//                R.id.note,
//                R.id.footprint
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        //cursor is null for now, but will be swapped by the following AsyncTask onPostExecute method
        dataAdapter = new SimpleCursorAdapter(
                getActivity(), R.layout.view_record_fragment,
                null,      //notice the cursor is null for now
                columns,
                to, 0);

        /**This Java statement (beginning with "new" and ending with "}.execute();") executes an new instance
        * of an anonymous class that extends AsyncTask.  The new instance is-a AsyncTask.
        * Executes an AsyncTask to acquire the cursor on a background thread
        * in onPostExecute, the real cursor will replace the null cursor
         */
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            public Cursor doInBackground(Void... v) {
                dbHelper = new TipCalculatorDbAdapter(getActivity());
                dbHelper.open();
                return dbHelper.fetchAllRecords();
            }

            @Override
            public void onPostExecute(Cursor c) {
                dataAdapter.swapCursor(c);
            }
        }.execute();
        setListAdapter(dataAdapter);

    }

    /**
     * Method to get the record details from the database
     * @param listView ListView
     * @param view View object
     * @param position integer position with the table.
     * @param id Long.
     */
    @Override
    public void onListItemClick(ListView listView, View view,
                                int position, long id) {
        // Get the cursor, positioned to the corresponding row in the result set
        Cursor cursor = (Cursor) listView.getItemAtPosition(position);

        // Get the state's capital from this row in the database.
        String countryCode =
                cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_ROWID));

       Toast.makeText(getActivity(),
               countryCode, Toast.LENGTH_SHORT).show();

        mCallback.onTipSelected(Integer.parseInt(countryCode), id);
    }

    // Important note: This method was
    @Override
    public void onResume() {
        super.onResume(); //
        getView().setFocusableInTouchMode(true);

        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            //Inner method.
            @Override
            public boolean onKey(View view, int keyId, KeyEvent keyIdEvent) {
                if (keyIdEvent.getAction() == KeyEvent.ACTION_UP && keyId == KeyEvent.KEYCODE_BACK) {
                    startActivity(new Intent(getActivity(), com.example.hwardak.tipcalculator.MainActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * onStart method.
     */
    @Override
    public void onStart() {
        super.onStart();

//
    }


    /**
     * attaching interface. This makes sure that the container activity has implemented
     // the callback interface. If not, it throws an exception.
     * @param activity Activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        try {
            mCallback = (OnTiptSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}