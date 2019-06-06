package face.command;

import com.esotericsoftware.minlog.Log;

public class CommandBase implements ICommand {

	public void OnExecute(Context ct) {
		// TODO Auto-generated method stub
		
		Log.error("Not Handled Command:" + ct.field.Command);
		
	}

}
