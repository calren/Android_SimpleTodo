package codepath.app.simpletodo;


import codepath.demos.helloworlddemo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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
		EditText etPriority = (EditText) findViewById(R.id.editText2);
		
		//put cursor at the end of text
		etNewItem.setText(oldText);
		Editable etext = etNewItem.getText();
		Selection.setSelection(etext, oldText.length());
		
//		etPriority.setText(0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_item, menu);
		return true;
	}
	
	public void changeToDoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.editText1);
		EditText etPriority = (EditText) findViewById(R.id.editText2);
		Intent data = new Intent();
		data.putExtra("newItem", etNewItem.getText().toString());
		data.putExtra("position", position);
		int priority = 0;
		try {
			priority = Integer.parseInt(etPriority.getText().toString());
		} catch (Exception e) {
			
		}
		data.putExtra("priority", priority);
		setResult(RESULT_OK, data);
		finish(); 
	}

}
