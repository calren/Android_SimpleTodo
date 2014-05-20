package codepath.app.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

import codepath.demos.helloworlddemo.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

public class TodoActivity extends Activity {
	
	TreeMap<String, Integer> preSorted;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		preSorted = new TreeMap<String, Integer>();
		items = sort(preSorted);
		readItems();
		itemsAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		items.add("Start adding items!");
		setupListViewListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_todo, menu);
		return true;
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
				items.remove(position);
				preSorted.remove(((TextView)view).getText());
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra("oldItem", items.get(position)); 
				i.putExtra("position", position); 
				startActivityForResult(i, REQUEST_CODE);		
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     String name = data.getExtras().getString("newItem");
	     int position = data.getExtras().getInt("position");
	     int priority = data.getExtras().getInt("priority");
	     // insert in correct place
	     preSorted.remove(name);
	     preSorted.put(name, priority);
	     items.clear();
	     for(String item: preSorted.keySet()){
				items.add(item);
		 }	     
	     saveItems();
	     itemsAdapter.notifyDataSetChanged();
	  }
	}
	
	public ArrayList<String> sort(TreeMap<String, Integer> t) {
		ArrayList<String> result = new ArrayList<String>();
		for(String item: t.keySet()){
			result.add(item);
		}
		
		return result;
	}
	
	public void addToDoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		preSorted.put(etNewItem.getText().toString(), 0);
		etNewItem.setText("");
		saveItems();
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
//		items = sort(preSorted);
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
