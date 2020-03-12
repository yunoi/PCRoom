package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminRootController implements Initializable {
	@FXML
	private Label lbCurrentTime;
	@FXML
	private Label seatNo01;
	@FXML
	private Label seatNo02;
	@FXML
	private Label seatNo03;
	@FXML
	private Label seatNo04;
	
	@FXML
	private Label lbSeatInfo01;
	@FXML
	private Label lbSeatInfo02;
	@FXML
	private Label lbSeatInfo03;
	@FXML
	private Label lbSeatInfo04;
	
	@FXML
	private Label lbRemainTime01;
	@FXML
	private Label lbRemainTime02;
	@FXML
	private Label lbRemainTime03;
	@FXML
	private Label lbRemainTime04;
	
	@FXML
	private ImageView imgSeatSetting01;
	@FXML
	private ImageView imgSeatSetting02;
	@FXML
	private ImageView imgSeatSetting03;
	@FXML
	private ImageView imgSeatSetting04;
	
	@FXML
	private Rectangle recSeat01;
	@FXML
	private Rectangle recSeat02;
	@FXML
	private Rectangle recSeat03;
	@FXML
	private Rectangle recSeat04;
	
	@FXML
	private Button btnAdminLogout;
	@FXML
	private Button btnAdminMenu;
	@FXML
	private Button btnOrderCheck;
	@FXML
	private Button btnUserInfo;
	@FXML
	private Button btnIncome;
	@FXML
	private Label lbCurrentWorkerName;

	final DateFormat format = DateFormat.getInstance();

	private LocalTime t;

	private int i;
	String remainTime;
	private ExecutorService executorService;
	private int countdownSeconds;

	// �α��� ����
	private ServerSocket serverSocket;
	private Socket socket;
	private List<Player> list = new Vector<Player>();
	private boolean logout = true;
	LoginController lc = new LoginController();
	private int no[] = new int[2];
	private int index =0;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbCurrentWorkerName.setText(AdminLoginController.currentAdminId);

		// �α����� �Ǹ� ���������� ������ �����¿� ������.
		serverSocket();
		////////////////////////////////////////////////////////////////
		
		// �ֹ� Ȯ�� ��ư
		btnOrderCheck.setOnAction(event -> {
			handlerGoToOrderCheck(event);
		});
		// ȸ�� ���� ��ư
		btnUserInfo.setOnAction(event -> {
			handlerGoToUserTable(event);
		});
		// �����ڸ޴� ��ư
		btnAdminMenu.setOnAction(event -> {
			handlerGoToAdminMenuAction(event);
		});
		// ���� ���� ��ư
		btnIncome.setOnAction(event -> {
			handlerGoToIncomeTable(event);
		});
		// �α׾ƿ�
		btnAdminLogout.setOnAction(event -> {
			handlerLogout(event);
		});
		
		// �¼��� ����Ŭ���ϸ� ���� �̿��� ������ �� �� �ִ�.
//		recSeat05.setOnMouseClicked(event -> {
//			handlerViewUserInfoAction(event);
//		});
		// �¼��� ���� ��ư�� ����Ŭ���ϸ� �ش� �¼��� �ü������� �� �� �ִ�.
//		imgSeatSetting05.setOnMouseClicked(event -> {
//			handlerViewSeatInfo(event);
//		});
		//******************************************���� �ð�*************************************************//
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler() {
			@Override
			public void handle(Event event) {
				final Calendar cal = Calendar.getInstance();
				lbCurrentTime.setText(format.format(cal.getTime()));
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		//*****************************************************************************************************//
		
	} // end of initialize

	// �α����� �Ǹ� ���������� ������ �����¿� ������.
	public void serverSocket() {
		executorService = Executors.newFixedThreadPool(4);
		try {
			serverSocket = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("���Ӵ����...");
						Socket socket = serverSocket.accept();
							Player player = new Player(socket);
							list.add(player);
//						System.out.println(LoginController.getCurrentSeatNo);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					} catch (IOException e) {
						if (serverSocket.isClosed()) {
							break;
						}
						e.printStackTrace();
					}
				}

			}
		};
		executorService.submit(runnable);
	}

	// ������ ����� ���� Ŭ����
	public class Player {
		private Socket socket = null;
		private String name = null;
		private BufferedReader in;
		private int seatNo;
		private boolean startTime = false;
		private int no1;
		private int time;
		private int num2;
		private int num[] = new int [2];

		// ������
		public Player(Socket socket) {
			this.socket = socket;
			receive();
		}

		void receive() {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e1) {
				System.out.println("�����߻�");
			}
//			System.out.println("receive"+count++);
			
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						logout = true;
						String str;
						while (logout) {
							str = in.readLine();
							if (str.startsWith("NAME")) {
								System.out.println("�մ��� �α����߽��ϴ�.");
								System.out.println(str);
								String command = str.substring(5);
								name = command;

								ChargeDAO cdo = new  ChargeDAO();
								seatNo = Integer.parseInt(cdo.getLoginUserSeatNo(name));
								System.out.println("�¼���ȣ "+seatNo);		// �����
								int prepaidMoney = cdo.selectLabelNameTime(name);
//								int prepaidMoney = 2000;	// �����

								startTime = true;
								// ���� �ð� ǥ��
								if (prepaidMoney == 1000) {
									countdownSeconds = 3600;
								} else if (prepaidMoney == 2000) {
									countdownSeconds = 7200;
								} else if (prepaidMoney == 3000) {
									countdownSeconds = 10800;
								} else if (prepaidMoney == 5000) {
									countdownSeconds = 18000;
								}else if (prepaidMoney == 20000) {
									countdownSeconds = 86400;
								}
//
								Runnable runn = new Runnable() {

									@Override
									public void run() {

										if (name == null) {// 4
											CommonFunc.alertDisplay(1, "����", "���� �̸��� �����ϴ�.", "�ٽ� Ȯ���� �ּ���.");
										} else {// 5
											if (seatNo == 1) {
												while (startTime) {
													for (i = countdownSeconds; i >= 0; i--) {
		
														try {
															Thread.sleep(1000);
														} catch (InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbSeatInfo01.setText(name);
															lbRemainTime01.setText(String.valueOf(
																	i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
														});
														if(startTime == false) {
															break;
														}
														//System.out.println("i="+i+" "+startTime);
														///////db �ҷ��� �ð� ���� �ϴ� �ڸ� /////////////
														
													} // for
		
												} // while
											} else if (seatNo == 2) {
												while (startTime) {
													for (i = countdownSeconds; i >= 0; i--) {
														try {
															Thread.sleep(1000);
														} catch (InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbSeatInfo02.setText(name);
															lbRemainTime02.setText(String.valueOf(
																	i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
														});
														if(startTime == false) {
															break;
														}
														//System.out.println("i="+i+" "+startTime);
														///////db �ҷ��� �ð� ���� �ϴ� �ڸ� /////////////
														
													} // for
		
												} // while
											} // if
											else if (seatNo == 3) {
												while (startTime) {
													for (i = countdownSeconds; i >= 0; i--) {
														try {
															Thread.sleep(1000);
														} catch (InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbSeatInfo03.setText(name);
															lbRemainTime03.setText(String.valueOf(
																	i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
														});
														if(startTime == false) {
															break;
														}
														//System.out.println("i="+i+" "+startTime);
														///////db �ҷ��� �ð� ���� �ϴ� �ڸ� /////////////
														
													} // for
		
												} // while
											} // if
											else if (seatNo == 4) {
												while (startTime) {
													for (i = countdownSeconds; i >= 0; i--) {
														try {
															Thread.sleep(1000);
														} catch (InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbSeatInfo04.setText(name);
															lbRemainTime04.setText(String.valueOf(
																	i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
														});
														if(startTime == false) {
															break;
														}
														//System.out.println("i="+i+" "+startTime);
														///////db �ҷ��� �ð� ���� �ϴ� �ڸ� /////////////
														
													} // for
		
												} // while
											} // if
		
										} // else
									}// run()
								}; // runn
								executorService.submit(runn);
//								Thread t1 = new Thread(runn);
//								t1.start();
							} else if (str.startsWith("EXIT")) {
								logout = false;
								startTime = false;
								System.out.println(str);
									//no[no1] =0;
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								if (seatNo == 1) {
									Platform.runLater(() -> {// 6
										lbSeatInfo01.setText("�� �� ��");
										lbRemainTime01.setText("00:00:00");

									});
								} else if (seatNo == 2) {
									Platform.runLater(() -> {// 6
										lbSeatInfo02.setText("�� �� ��");
										lbRemainTime02.setText("00:00:00");

									});
								} else if (seatNo == 3) {
									Platform.runLater(() -> {// 6
										lbSeatInfo03.setText("�� �� ��");
										lbRemainTime03.setText("00:00:00");

									});
								} else if (seatNo == 4) {
									Platform.runLater(() -> {// 6
										lbSeatInfo04.setText("�� �� ��");
										lbRemainTime04.setText("00:00:00");

									});
								}


		//						System.out.println("���ϲ���?"+socket.isClosed());
								socket.close();
								for( Player player : list) {
									if(player.name.equals(name)) {
										list.remove(player);
									}
								}
								break;

//								System.out.println("�մ��� �α׾ƿ��߽��ϴ�.");

							} // else if exit
						} // while
					} catch (IOException e1) {
						e1.printStackTrace();
					} // readLine��
				}// run
			};// runnable
//		Thread t1 = new Thread(runnable);
//		t1.start();
			executorService.submit(runnable);

		}// receive
	}// class

	

	
	// ���� ���� ���̺�� �̵�
	public void handlerGoToUserTable(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_table.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("����� ��ȸ �� ����");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "���������â �θ��� ����", "���������â�� �ҷ��� �� �����ϴ�.", e.toString());
		}
	}

	// �ֹ� Ȯ�� �޴��� ����
	public void handlerGoToOrderCheck(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/order.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("�ֹ� Ȯ��");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "������â �θ��� ����", "������â�� �ҷ��� �� �����ϴ�.", e.toString());
		}
	}

	// �����ڸ޴� ��ư
	public void handlerGoToAdminMenuAction(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/admin_menu.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("������ �޴�");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "�޴�â �θ��� ����", "�޴�â�� �ҷ��� �� �����ϴ�.", e.toString());
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
		}
	}

	// ���⺸��
	public void handlerGoToIncomeTable(ActionEvent event) {
		Parent incomeView = null;
		Stage incomeStage = null;
		try {
			incomeView = FXMLLoader.load(getClass().getResource("/view/income.fxml"));
			Scene scene = new Scene(incomeView);
			incomeStage = new Stage();
			incomeStage.setTitle("���� ��ȸ");
			incomeStage.setScene(scene);
			incomeStage.setResizable(false);
			incomeStage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "���� ��ȸ ����", "��������â�� �ҷ��� �� �����ϴ�.", e.toString());
		}
	}

	// �α׾ƿ�
	public void handlerLogout(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/admin_login.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("Login.");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();

			// ���罺������(����â)�� �ݰ� ���ο� â�� ����.
			((Stage) btnAdminLogout.getScene().getWindow()).close();
			socket.close();
			serverSocket.close();
			
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "�α׾ƿ� ����", "�α׾ƿ��� �� �����ϴ�.", e.toString());
		}
		
	}

}
