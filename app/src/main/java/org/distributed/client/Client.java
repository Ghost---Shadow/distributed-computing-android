package org.distributed.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.distributed.serializable.Task;

public class Client {

	private static Client instance = null;
	private ClientConnection clientConnection = null;

	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;

	private Client() {
		// Singleton constructor
	}

	public void connect(String ip, int port) {
		if (clientConnection != null)
			clientConnection.terminate();

		clientConnection = new ClientConnection(ip, port);
		clientConnection.start();
	}

	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return instance;
	}

	private class ClientConnection extends Thread implements Runnable {
		private boolean isTerminated = false;
		private String ip;
		private int port;
		private Socket socket;
		private int delay;

		public ClientConnection(String ip, int port) {
			this.ip = ip;
			this.port = port;
			this.delay = 1000;
		}

		@Override
		public void run() {
			while (!isTerminated) {
				try {
					socket = new Socket(ip, port);
					objectIn = new ObjectInputStream(socket.getInputStream());
					objectOut = new ObjectOutputStream(socket.getOutputStream());
					Task task = (Task) objectIn.readObject();
					TaskProcessor.processTask(task);
					task.print();
					objectOut.writeObject(task);
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//break;
			}

		}		

		public void terminate() {
			isTerminated = true;
		}

	}

}
