package codepath.demos.helloworlddemo;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class EditItemActivity extends Activity {
	
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		String oldText = getIntent().getStringExtra("oldItem");
		position = getIntent().getIntExtra("position", -1);		
		
		// set text to old text
		EditText etNewItem = (EditText) findViewById(R.id.editText1);
		
		//put cursor at the end of text
		etNewItem.setText(oldText);
		Editable etext = etNewItem.getText();
		Selection.setSelection(etext, oldText.length());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_item, menu);
		return true;
	}
	
	public void changeToDoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.editText1);
		Intent data = new Intent();
		data.putExtra("newItem", etNewItem.getText().toString());
		data.putExtra("position", position);
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); 
	}

}
