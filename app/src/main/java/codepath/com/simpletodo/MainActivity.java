package codepath.com.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdaptor;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdaptor  = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, items );
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdaptor);

        //mock data
        //items.add("first item");
        //items.add("second item");

        setupListViewListener();
    }

    public void onAddItems (View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdaptor.add(itemText);
        etNewItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "item added to list", Toast.LENGTH_SHORT).show();

    }

    private void setupListViewListener(){
        Log.i("MainActivity", "setting up listener on list view");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("MainActivity", "item removed from the list" + position);
                items.remove(position);
                itemsAdaptor.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "todo.txt");
    }

    private  void readItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e ){
            Log.e("MainActivity", "error reading file", e);
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        }
        catch (IOException e ){
            Log.e("MainActivity", "error writting file", e);
            items = new ArrayList<>();
        }

    }
}
