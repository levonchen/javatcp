package face.command;

import java.util.HashMap;

import org.appcommon.AppNetwork;

public class CommandFactory {
	
	private static CommandFactory s=null;

    public static synchronized CommandFactory getInstance()
    {
        if(s==null)
            s= new CommandFactory();
        return s;
    }
    
    private HashMap<Integer,ICommand> executor_ = new HashMap<Integer, ICommand>();
    private ICommand defaultCmd = new CommandBase();
    
    private CommandFactory()
    {
    	registerCommands();
    }
    
    
    public ICommand GetExecutor(int cmd) {
    	
    	ICommand executor = executor_.get(cmd);
    	if(executor != null)
    	{
    		return executor;
    	}
    	return defaultCmd;
    	
    }
    
    
    private void registerCommands()
    {
    	this.addCommand(AppNetwork.C_Req_Login, new LoginCommand());
    	
    }
    
    private void addCommand(int cmd, ICommand exe)
    {
    	executor_.put(cmd, exe);
    }
}
