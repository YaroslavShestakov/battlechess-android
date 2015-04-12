package yaroslav.android.battlechess.gui.activities;

import android.os.Bundle;
import yaroslav.android.battlechess.R;
import yaroslav.android.battlechess.helpers.TaggedActivity;

/**
 * Activity responsible for <b>About</b> section representation
 * @author Yaroslav
 *
 */
public class AboutActivity extends TaggedActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}
}
