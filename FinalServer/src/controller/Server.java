package controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
//import java.util.Base64;

public class Server {

	private Socket sock;
	private BufferedReader br;
	private PrintWriter pw;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Server(Socket sock) {
		this.sock = sock;

		try {
			pw = new PrintWriter(sock.getOutputStream());
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				br.close();
				pw.close();
				oos.close();
				ois.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("IO error in server thread");
			e.printStackTrace();
		}

	}

	public void sendACK() {
		pw.println("/ACK");
		pw.flush();
		System.out.println("send >> " + "/ACK");
	}

	public void sendREJ() {
		pw.println("/REJ");
		pw.flush();
		System.out.println("send >> " + "/REJ");
	}

	public void sendObjToClient(Object object) {

		try {
			oos.writeObject(object);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String rcvFromClient() {
		// TODO implement here
		String str = null;
		try {
			str = br.readLine();
			System.out.println("rcv < " + str);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("RCV Error");
		}
		return str;
	}

	public Object rcvObjFromClient() {
		
		 Object object = null;

	      try {
	         object = ois.readObject();
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }

	      return object;
	}

	
/*	 public void sendByte(Object object) throws IOException {

         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         ObjectOutput out = new ObjectOutputStream(bos);
         out.writeObject(object);
         byte[] byteFile = bos.toByteArray();
         sock.getOutputStream().write(byteFile);
         
      }

      public Object rcvByte()  {

        int dataSize = 0;
         byte[] byteFile = new byte[4096];
         byte[] rcvData = null;
         Object obj = null;
         
         try {
			dataSize = sock.getInputStream().read(byteFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         if(dataSize!=0){
            rcvData = new byte[dataSize];
            for(int j = 0; j<dataSize;j++){
              rcvData[j]= byteFile[j];
            }            
         }
         
         try {
            ByteArrayInputStream bis = new ByteArrayInputStream(rcvData);
            ObjectInput in = new ObjectInputStream(bis);
            obj = in.readObject();

         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return obj;
      }
	
	*/
	
	//test
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

		byte decoded[] = Base64.getDecoder().decode(rcvFromClient().getBytes());

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
