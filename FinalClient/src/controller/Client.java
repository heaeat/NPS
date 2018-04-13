package controller;
/*


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Client extends Thread implements Serializable{

	private Socket sock;
	private BufferedReader br;
	private PrintWriter pw;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Client(Socket sock) {
		this.sock = sock;

		try {
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(sock.getOutputStream());
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("onShutdown()");
				sendToServer("/EXIT");
			}
		}));

	}

	public void sendToServer(String msg) {
		// TODO implement here
		pw.println(msg);
		pw.flush();
		System.out.println("send >> " + msg);
	}

	public String rcvFromServer() {
		// TODO implement here
		String str = null;
		try {
			str = br.readLine();
			System.out.println("rcv < " + str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public void sendFile() {

		Path path = Paths.get("test.txt");
		byte[] byteFile = null;
		try {
			byteFile = Files.readAllBytes(path);
			Checksum crc = new CRC32();
			crc.update(byteFile, 0, byteFile.length);

			pw.println(crc.getValue());

			byte encoded[] = Base64.getEncoder().encode(byteFile);
			pw.println(new String(encoded));
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public File rcvFile() throws IOException {
		// TODO implement here
		String msg = rcvFromServer();
		long checksum = Long.parseLong(msg);

		byte decoded[] = Base64.getDecoder().decode(rcvFromServer().getBytes());
		Checksum crc = new CRC32();
		crc.update(decoded, 0, decoded.length);

		if (checksum != crc.getValue()) {
			// throw new IOException();
		}

		
		
		File lOutFile = null;
		try {
			lOutFile = new File("test222222.txt");
			FileOutputStream lFileOutputStream = new FileOutputStream(lOutFile);
			lFileOutputStream.write(decoded);
			lFileOutputStream.close();
		} catch (Throwable e) {
			e.printStackTrace(System.out);
		}

		return lOutFile;

	}

	public void sendObjToServer(Object object) {

		try {
			oos.writeObject(object);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object rcvObjFromServer() {
		// TODO implement here
		Object object = null;

		try {
			object = ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}
	
	public void sendByte(Object object) throws IOException{

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);   
		
		out.writeObject(object);
		byte[] byteFile = bos.toByteArray(); 

		oos.writeObject(byteFile);
		oos.flush();
	}

	public Object rcvByte() throws Exception {

		byte[] byteFile = null;
		Object obj = null;
		try {
			byteFile = (byte[]) ois.readObject();
			ByteArrayInputStream bis = new ByteArrayInputStream(byteFile);
			ObjectInput in = new ObjectInputStream(bis);
			Object o = in.readObject();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
}

*/
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Client extends Thread implements Serializable {

	private Socket sock;
	private BufferedReader br;
	private PrintWriter pw;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Client(Socket sock) {
		this.sock = sock;

		try {
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(sock.getOutputStream());
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("onShutdown()");
				sendToServer("/EXIT");
			}
		}));

	}

	public void sendToServer(String msg) {
		// TODO implement here
		pw.println(msg);
		pw.flush();
		System.out.println("send >> " + msg);
	}

	public String rcvFromServer() {
		// TODO implement here
		String str = null;
		try {
			str = br.readLine();
			System.out.println("rcv < " + str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public void sendObjToServer(Object object) {

		try {
			oos.writeObject(object);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object rcvObjFromServer() {
		// TODO implement here
		Object object = null;

		try {
			object = ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}
	
	
	public void sendFile1(Object obj) {

		byte[] data = null;
		
		    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
		         ObjectOutput out = new ObjectOutputStream(bos)) {
		        try {
					out.writeObject(obj);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        data = bos.toByteArray();
		    } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		

			byte encoded[] = Base64.getEncoder().encode(data);
			pw.println(new String(encoded));
			pw.flush();

	}
	
	public Object rcvFile1() throws IOException {
		
		Object obj = null;
		// TODO implement here

		byte decoded[] = Base64.getDecoder().decode(rcvFromServer().getBytes());

		try (ByteArrayInputStream bis = new ByteArrayInputStream(decoded);
		         ObjectInput in = new ObjectInputStream(bis)) {
		        try {
					obj = in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		
		return obj;
	}
	
}
