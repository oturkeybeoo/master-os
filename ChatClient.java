
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.util.Vector;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ChatClient {
	public static TextArea		text;
	public static TextField		data;
	public static Frame 		frame;

	public static Vector<String> users = new Vector<String>();
	public static String myName;
	
	public static void main(String argv[]) {

		if (argv.length != 1) {
			System.out.println("java ChatClient <name>");
			return;
		}
		myName = argv[0];

		// creation of the GUI 
		frame=new Frame();
		frame.setLayout(new FlowLayout());

		text=new TextArea(10,55);
		text.setEditable(false);
		text.setForeground(Color.red);
		frame.add(text);

		data=new TextField(55);
		frame.add(data);

		Button write_button = new Button("write");
		write_button.addActionListener(new WriteListener());
		frame.add(write_button);

		Button enter_button = new Button("enter");
		enter_button.addActionListener(new EnterListener());
		frame.add(enter_button);

		Button who_button = new Button("who");
		who_button.addActionListener(new WhoListener());
		frame.add(who_button);

		Button leave_button = new Button("leave");
		leave_button.addActionListener(new LeaveListener());
		frame.add(leave_button);

		Button attach_button = new Button("attach");
		leave_button.addActionListener(new AttachListener());
		frame.add(attach_button);


		frame.setSize(470,300);
		text.setBackground(Color.black); 
		frame.setVisible(true);
	}


	public static void enter(String username) {
		// here you should invoke the RMI server
		try {
			ChatClient.text.append("You enter the room");
			ChatServer chatserver = (ChatServer) Naming.lookup("//localhost/chatserver");
			Callback callback = new CallbackImpl(ChatClient.text);
			try {
				chatserver.enter(username, callback);
            } catch (RemoteException e) {
				System.out.println("A RemoteException occurred when connecting to ChatServer: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void leave(String username) {
		// here you should invoke the RMI server
		try {
			ChatClient.text.append("You have left the room");
			ChatServer chatserver = (ChatServer) Naming.lookup("//localhost/chatserver");
			try {
				chatserver.leave(username);
            } catch (RemoteException e) {
				System.out.println("A RemoteException occurred when leaving to ChatServer: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void who() {
		// print("admin", "who() invoked");
		// here you should invoke the RMI server
		String[] people;
		try {
			ChatServer chatserver = (ChatServer) Naming.lookup("//localhost/chatserver");

			try {
				people = chatserver.who();
				if (people.length == 1) ChatClient.text.append("You are alone.\n");
				else ChatClient.text.append(people.length+" people in this chat:\n");
				for (String p : people) {
					ChatClient.text.append(p+"\n");
				}
				} catch (RemoteException e) {
				System.out.println("A RemoteException occurred when getting list of people from ChatServer: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void write(String username, String text) {
		// here you should invoke the RMI server
		ChatClient.text.append("You say: "+text+"\n");
		try {
			ChatServer chatserver = (ChatServer) Naming.lookup("//localhost/chatserver");

			try {
				chatserver.write(username, text);
            } catch (RemoteException e) {
				System.out.println("A RemoteException occurred when writing to ChatServer: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void attach() {
		// here you should invoke the RMI server
		ChatClient.text.append("You say: "+text+"\n");
		try {
			ChatServer chatserver = (ChatServer) Naming.lookup("//localhost/chatserver");

			try {
				chatserver.attach();
            } catch (RemoteException e) {
				System.out.println("A RemoteException occurred when attaching file to ChatServer: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void print(String username, String text) {
		try {
			ChatClient.text.append(username+" says : "+text+"\n");
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}
}

	// action invoked when the "write" button is clicked
	class WriteListener implements ActionListener {
		public void actionPerformed (ActionEvent ae) {
			try {
				ChatClient.write(ChatClient.myName, ChatClient.data.getText());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	// action invoked when the "connect" button is clicked
	class EnterListener implements ActionListener {
		public void actionPerformed (ActionEvent ae) {
			try {  
				ChatClient.enter(ChatClient.myName);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}  

	// action invoked when the "who" button is clicked
	class WhoListener implements ActionListener {
		public void actionPerformed (ActionEvent ae) {
			try {
				ChatClient.who();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	// action invoked when the "leave" button is clicked
	class LeaveListener implements ActionListener {
		public void actionPerformed (ActionEvent ae) {
			try {
				ChatClient.leave(ChatClient.myName);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

		// action invoked when the "attach" button is clicked
		class AttachListener implements ActionListener {
			public void actionPerformed (ActionEvent ae) {
				try {
					ChatClient.attach();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	

