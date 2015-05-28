package com.esc_project.ClientConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.esc_project.Constants;
import com.esc_project.DatabaseConnection.DBController;
import com.esc_project.DatabaseConnection.Notice;
import com.esc_project.DatabaseConnection.Product;
import com.esc_project.DatabaseConnection.QuestionAndAnswer;
import com.esc_project.Parser.JsonHelper;
import com.esc_project.Recommender.RecommendProduct;
import com.mysql.fabric.xmlrpc.base.Array;

public class ClientController implements Runnable{
	
	
	/** 클라이언트 포트번호 설정 **/
	final int clientPort = 8081;

	private DBController mDBController;
	
	private ServerSocket welcomeSocket;
	private Socket connectionSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private Thread[] threadArr;
	
	private String clientSentence;
	private String serverSentence;

	
	/** 생성자 **/
	public ClientController(int serverCnt) {
		// TODO Auto-generated constructor stub
		mDBController = new DBController();
		
		try {
			welcomeSocket = new ServerSocket(clientPort);
			threadArr = new Thread[serverCnt];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** i개의 스레드를 이용하여 클라이언트-서버 대기! **/
	public void start() {
		for(int i=0 ; i <threadArr.length ; i++) {
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}
	
	/** 클라이언트로 부터 메세지를 받는다. **/
	public void getMessageFromClient() {
		
		try {
		connectionSocket = welcomeSocket.accept();
		
		inFromClient = new BufferedReader(
				new InputStreamReader(connectionSocket.getInputStream(), "EUC_KR"));

		outToClient = new DataOutputStream(
				connectionSocket.getOutputStream());

		clientSentence = inFromClient.readLine();
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	/** 클라이언트에게 메세지를 보낸다. **/
	public void sendMessageToClient() {
		try {
			serverSentence += '\n';
			outToClient.write(serverSentence.getBytes("EUC_KR"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 지속적으로 돌아가는 부분 **/
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			getMessageFromClient();
			System.out.println("Client 메시지 : " + clientSentence);

			JsonHelper jsonhelper = new JsonHelper();
			jsonhelper.parserJsonMessage(clientSentence);
			Object cmdObj = sendCommandToDB(jsonhelper.getType(), jsonhelper.getObject());
			serverSentence = jsonhelper.makeJsonMessage(jsonhelper.getType(), cmdObj);

			sendMessageToClient();
			System.out.println("Sever 메시지 : " + serverSentence);
		}

	}

	/** 명령별로 외부DB에 접속하여 전송할 JSON메세지의 데이터부분을 받아오는 부분 **/
	public Object sendCommandToDB(String command, Object data) {
		Object obj = null;
		String questionType;
		
		switch(command) {
		case Constants.Uid_Info :
			List<Product> products = new ArrayList<Product>();
			List<String> uid = (List<String>)data;
			
			for(int i=0 ; i <uid.size() ; i++) {
				Product product = mDBController.Uid_Info(uid.get(i).toString());
				products.add(product);
			}
			obj = products;
			break;
			
		case Constants.All_Product_Info :
			ArrayList<Product> allProducts = new ArrayList<Product>();
			allProducts = mDBController.All_Products_Info();
			
			obj = allProducts;
			break;
			
		case Constants.requestNoticeDB :
			
			ArrayList<Notice> notices = new ArrayList<Notice>( );
			notices = mDBController.RequestNoticeDB();
			obj = notices ;
			break;
			
		case Constants.requestBest5QADB :
			ArrayList<QuestionAndAnswer> questionAndAnswers = new ArrayList<QuestionAndAnswer> () ;
			questionType = "best5";
			questionAndAnswers = mDBController.RequestQuestionAndAnswer(questionType);
			obj = questionAndAnswers;
			break;
			
		case Constants.RecommendedProduct_Info :
			List<RecommendProduct> recomendedProducts = new ArrayList<RecommendProduct>();
			recomendedProducts = mDBController.RecommendedProduct_Info((int)data);
			
			obj = recomendedProducts;
			break;
			
		case Constants.CustomerCart_Info_STORE:
			ArrayList<String> numberList = (ArrayList<String>) data;
			mDBController.CustomerCart_Info_STORE(numberList);
			break;
		}
		
		return obj;
	}
}
