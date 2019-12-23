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
	private Label seatNo05;
	@FXML
	private Label seatNo06;
	@FXML
	private Label seatNo07;
	@FXML
	private Label seatNo08;
	@FXML
	private Label seatNo09;
	@FXML
	private Label seatNo10;
	@FXML
	private Label seatNo11;
	@FXML
	private Label seatNo12;
	@FXML
	private Label seatNo13;
	@FXML
	private Label seatNo14;
	@FXML
	private Label seatNo15;
	@FXML
	private Label seatNo16;
	@FXML
	private Label seatNo17;
	@FXML
	private Label seatNo18;
	@FXML
	private Label seatNo19;
	@FXML
	private Label seatNo20;
	@FXML
	private Label lbSeatInfo01;
	@FXML
	private Label lbSeatInfo02;
	@FXML
	private Label lbSeatInfo03;
	@FXML
	private Label lbSeatInfo04;
	@FXML
	private Label lbSeatInfo05;
	@FXML
	private Label lbSeatInfo06;
	@FXML
	private Label lbSeatInfo07;
	@FXML
	private Label lbSeatInfo08;
	@FXML
	private Label lbSeatInfo09;
	@FXML
	private Label lbSeatInfo10;
	@FXML
	private Label lbSeatInfo11;
	@FXML
	private Label lbSeatInfo12;
	@FXML
	private Label lbSeatInfo13;
	@FXML
	private Label lbSeatInfo14;
	@FXML
	private Label lbSeatInfo15;
	@FXML
	private Label lbSeatInfo16;
	@FXML
	private Label lbSeatInfo17;
	@FXML
	private Label lbSeatInfo18;
	@FXML
	private Label lbSeatInfo19;
	@FXML
	private Label lbSeatInfo20;
	@FXML
	private Label lbRemainTime01;
	@FXML
	private Label lbRemainTime02;
	@FXML
	private Label lbRemainTime03;
	@FXML
	private Label lbRemainTime04;
	@FXML
	private Label lbRemainTime05;
	@FXML
	private Label lbRemainTime06;
	@FXML
	private Label lbRemainTime07;
	@FXML
	private Label lbRemainTime08;
	@FXML
	private Label lbRemainTime09;
	@FXML
	private Label lbRemainTime10;
	@FXML
	private Label lbRemainTime11;
	@FXML
	private Label lbRemainTime12;
	@FXML
	private Label lbRemainTime13;
	@FXML
	private Label lbRemainTime14;
	@FXML
	private Label lbRemainTime15;
	@FXML
	private Label lbRemainTime16;
	@FXML
	private Label lbRemainTime17;
	@FXML
	private Label lbRemainTime18;
	@FXML
	private Label lbRemainTime19;
	@FXML
	private Label lbRemainTime20;
	@FXML
	private ImageView imgSeatSetting01;
	@FXML
	private ImageView imgSeatSetting02;
	@FXML
	private ImageView imgSeatSetting03;
	@FXML
	private ImageView imgSeatSetting04;
	@FXML
	private ImageView imgSeatSetting05;
	@FXML
	private ImageView imgSeatSetting06;
	@FXML
	private ImageView imgSeatSetting07;
	@FXML
	private ImageView imgSeatSetting08;
	@FXML
	private ImageView imgSeatSetting09;
	@FXML
	private ImageView imgSeatSetting10;
	@FXML
	private ImageView imgSeatSetting11;
	@FXML
	private ImageView imgSeatSetting12;
	@FXML
	private ImageView imgSeatSetting13;
	@FXML
	private ImageView imgSeatSetting14;
	@FXML
	private ImageView imgSeatSetting15;
	@FXML
	private ImageView imgSeatSetting16;
	@FXML
	private ImageView imgSeatSetting17;
	@FXML
	private ImageView imgSeatSetting18;
	@FXML
	private ImageView imgSeatSetting19;
	@FXML
	private ImageView imgSeatSetting20;
	@FXML
	private Rectangle recSeat01;
	@FXML
	private Rectangle recSeat02;
	@FXML
	private Rectangle recSeat03;
	@FXML
	private Rectangle recSeat04;
	@FXML
	private Rectangle recSeat05;
	@FXML
	private Rectangle recSeat06;
	@FXML
	private Rectangle recSeat07;
	@FXML
	private Rectangle recSeat08;
	@FXML
	private Rectangle recSeat09;
	@FXML
	private Rectangle recSeat10;
	@FXML
	private Rectangle recSeat11;
	@FXML
	private Rectangle recSeat12;
	@FXML
	private Rectangle recSeat13;
	@FXML
	private Rectangle recSeat14;
	@FXML
	private Rectangle recSeat15;
	@FXML
	private Rectangle recSeat16;
	@FXML
	private Rectangle recSeat17;
	@FXML
	private Rectangle recSeat18;
	@FXML
	private Rectangle recSeat19;
	@FXML
	private Rectangle recSeat20;
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

	// 로그인 감지
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

		// 로그인이 되면 서버소켓이 켜지고 대기상태에 빠진다.
		serverSocket();
		////////////////////////////////////////////////////////////////
		
		// 주문 확인 버튼
		btnOrderCheck.setOnAction(event -> {
			handlerGoToOrderCheck(event);
		});
		// 회원 관리 버튼
		btnUserInfo.setOnAction(event -> {
			handlerGoToUserTable(event);
		});
		// 관리자메뉴 버튼
		btnAdminMenu.setOnAction(event -> {
			handlerGoToAdminMenuAction(event);
		});
		// 매출 보기 버튼
		btnIncome.setOnAction(event -> {
			handlerGoToIncomeTable(event);
		});
		// 로그아웃
		btnAdminLogout.setOnAction(event -> {
			handlerLogout(event);
		});
		
		// 좌석을 더블클릭하면 현재 이용자 정보를 볼 수 있다.
//		recSeat05.setOnMouseClicked(event -> {
//			handlerViewUserInfoAction(event);
//		});
		// 좌석의 설정 버튼을 더블클릭하면 해당 좌석의 시설정보를 볼 수 있다.
//		imgSeatSetting05.setOnMouseClicked(event -> {
//			handlerViewSeatInfo(event);
//		});
		//******************************************현재 시간*************************************************//
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

	// 로그인이 되면 서버소켓이 켜지고 대기상태에 빠진다.
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
						System.out.println("접속대기중...");
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

	// 접속한 사용자 내장 클래스
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

		// 생성자
		public Player(Socket socket) {
			this.socket = socket;
			receive();
		}

		void receive() {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e1) {
				System.out.println("문제발생");
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
								System.out.println("손님이 로그인했습니다.");
								System.out.println(str);
								String command = str.substring(5);
								name = command;

								ChargeDAO cdo = new  ChargeDAO();
								seatNo = Integer.parseInt(cdo.getLoginUserSeatNo(name));
								System.out.println("좌석번호 "+seatNo);		// 디버깅
								int prepaidMoney = cdo.selectLabelNameTime(name);
//								int prepaidMoney = 2000;	// 디버깅

								startTime = true;
								// 남은 시간 표시
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
											CommonFunc.alertDisplay(1, "오류", "유저 이름이 없습니다.", "다시 확인해 주세요.");
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
														///////db 불러서 시간 저장 하는 자리 /////////////
														
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
														///////db 불러서 시간 저장 하는 자리 /////////////
														
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
										lbSeatInfo01.setText("빈 좌 석");
										lbRemainTime01.setText("00:00:00");

									});
								} else if (seatNo == 2) {
									Platform.runLater(() -> {// 6
										lbSeatInfo02.setText("빈 좌 석");
										lbRemainTime02.setText("00:00:00");

									});
								}
		//						System.out.println("소켓끊김?"+socket.isClosed());
								socket.close();
								for( Player player : list) {
									if(player.name.equals(name)) {
										list.remove(player);
									}
								}
								break;

//								System.out.println("손님이 로그아웃했습니다.");

							} // else if exit
						} // while
					} catch (IOException e1) {
						e1.printStackTrace();
					} // readLine끝
				}// run
			};// runnable
//		Thread t1 = new Thread(runnable);
//		t1.start();
			executorService.submit(runnable);

		}// receive
	}// class

	

	
	// 유저 관리 테이블로 이동
	public void handlerGoToUserTable(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_table.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("사용자 조회 및 관리");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "사용자정보창 부르기 실패", "사용자정보창을 불러올 수 없습니다.", e.toString());
		}
	}

	// 주문 확인 메뉴로 가기
	public void handlerGoToOrderCheck(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/order.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("주문 확인");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "상세정보창 부르기 실패", "상세정보창을 불러올 수 없습니다.", e.toString());
		}
	}

	// 관리자메뉴 버튼
	public void handlerGoToAdminMenuAction(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/admin_menu.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("관리자 메뉴");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "메뉴창 부르기 실패", "메뉴창을 불러올 수 없습니다.", e.toString());
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
		}
	}

	// 매출보기
	public void handlerGoToIncomeTable(ActionEvent event) {
		Parent incomeView = null;
		Stage incomeStage = null;
		try {
			incomeView = FXMLLoader.load(getClass().getResource("/view/income.fxml"));
			Scene scene = new Scene(incomeView);
			incomeStage = new Stage();
			incomeStage.setTitle("매출 조회");
			incomeStage.setScene(scene);
			incomeStage.setResizable(false);
			incomeStage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "매출 조회 실패", "매출정보창을 불러올 수 없습니다.", e.toString());
		}
	}

	// 로그아웃
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
			// 현재스테이지(기존창)를 닫고 새로운 창을 연다.
			socket.close();
			serverSocket.close();
			((Stage) btnAdminLogout.getScene().getWindow()).close();
			mainStage.show();
			
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "로그아웃 실패", "로그아웃할 수 없습니다.", e.toString());
		}
		
	}
	
//	public void handlerViewUserInfoAction(MouseEvent event) {
//	try {
//		if (event.getClickCount() != 2) {
//			return;
//		}
//		Parent userInfo;
//		userInfo = FXMLLoader.load(getClass().getResource("/view/user_info.fxml"));
//
//		Stage stageDialog = new Stage(StageStyle.UTILITY);
//		stageDialog.initModality(Modality.WINDOW_MODAL);
//		stageDialog.initOwner(recSeat01.getScene().getWindow());
//		stageDialog.setTitle("현재 이용자 정보");
//
//		// -------------------------------------------
//		Label lbSeatNo = (Label) userInfo.lookup("#lbSeatNo");
//		Label lbUserId = (Label) userInfo.lookup("#lbUserId");
//		Label lbUserName = (Label) userInfo.lookup("#lbUserName");
//		Label lbPrepaidMoney = (Label) userInfo.lookup("#lbPrepaidMoney");
//		Label lbStartTime = (Label) userInfo.lookup("#lbStartTime");
//		Label lbUsedTime = (Label) userInfo.lookup("#lbUsedTime");
//		Label lbRemainTime = (Label) userInfo.lookup("#lbRemainTime");
//		Label lbPaidMoney = (Label) userInfo.lookup("#lbPaidMoney");
//		Button btnCharge = (Button) userInfo.lookup("#btnCharge");
//		Button btnOk = (Button) userInfo.lookup("#btnOk");

		// 사용자가 있을 때
//		if (!lbSeatInfo01.getText().equals("빈 좌 석")) {
//			ChargeDAO chargeDAO = new ChargeDAO();
//			Vector<Object> vector = new Vector<Object>();
//			userId = lbSeatInfo01.getText();
//			vector = chargeDAO.selectLabelNameTime(userId);
//			int prepaidMoney = (Integer) (vector.get(1));
//
//			// 남은 시간 표시
//			if (prepaidMoney == 1000) {
//				countdownSeconds = 3600;
//			} else if (prepaidMoney == 2000) {
//				countdownSeconds = 7200;
//			}
//			Thread t1 = new Thread() {
//				@Override
//				public void run() {
//					try {
//						while (logout) {
//							// int countdownSeconds = hour * 3600 + minute * 60 + second;
//							if (countdownSeconds == 0) {
//								CommonFunc.alertDisplay(1, "오류", "X", "X");
//							} else {
//								if (userId == null) {
//									CommonFunc.alertDisplay(1, "오류", "X", "X");
//								} else {
//									for (i = countdownSeconds; i >= 0; i--) {
//
//										Thread.sleep(1000);
//
//										Platform.runLater(() -> {
//											lbRemainTime.setText(String
//													.valueOf(i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
//											lbPrepaidMoney.setText(prepaidMoney + "원");
//										});
//									}
//								}
//							}
//						} // while
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			};
//			t1.start();
//		}
//		// 요금충전 버튼 눌렀을 때
//		btnCharge.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				try {
//					Parent charge;
//					charge = FXMLLoader.load(getClass().getResource("/view/admin_charge.fxml"));
//
//					Stage stageDialog = new Stage(StageStyle.UTILITY);
//					stageDialog.initModality(Modality.WINDOW_MODAL);
//					stageDialog.initOwner(recSeat01.getScene().getWindow());
//					stageDialog.setTitle("요금충전");
//
//					ComboBox cbxCharge = (ComboBox) charge.lookup("#cbxCharge");
//					Button btnOk = (Button) charge.lookup("#btnOk");
//					cbxCharge.setItems(FXCollections.observableArrayList("1시간 1,000원", "2시간 2,000원", "3시간 3,000원",
//							"5시간 5,000원", "24시간(정액) 20,000원"));
//
//					btnOk.setOnAction(new EventHandler<ActionEvent>() {
//
//						@Override
//						public void handle(ActionEvent event) {
//							String charge = cbxCharge.getValue().toString();
//							int idx = charge.indexOf(" ");
//							String paidMoney = charge.substring(idx + 1);
//
////							Vector<Object> vector = new Vector<Object>();
////							vector = chargeDAO.selectLabelNameTime(name);
////							int prepaidMoney = (Integer) (vector.get(1));
//
//							SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//							Date time = new Date();
//							String time1 = sdf.format(time);
//
//							// 사용한 시간 표시
//							Thread thread = new Thread() {
//								public void run() {
//									Timeline timeline = null;
//									t = LocalTime.parse("00:00:00");
//									DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
//
//									timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler() {
//										@Override
//										public void handle(Event event) {
//											t = t.plusSeconds(1);
//											Platform.runLater(() -> {
//												lbUsedTime.setText(t.format(dtf));
//											});
//										}
//									}));
//									timeline.setCycleCount(Animation.INDEFINITE);
//									timeline.play();
//								}
//							};
//							thread.setDaemon(true);
//							thread.start();
//
//							// 남은 시간 표시
//							if (paidMoney.equals("1,000원")) {
//								countdownSeconds = 3600;
//								prepaidMoney = 1000;
//							} else if (paidMoney.equals("2,000원")) {
//								countdownSeconds = 7200;
//								prepaidMoney = 2000;
//							}
//							Thread thread1 = new Thread(new Runnable() {
//
//								@Override
//								public void run() {
//									// Calculate total seconds to count down
////									int countdownSeconds = hour * 3600 + minute * 60 + second;
//									// System.out.println((countdownSeconds / 3600) + ":" + countdownSeconds % 3600
//									// / 60 + ":" + (countdownSeconds % 3600 % 60));
//
//									// Count down to 0 and print it on the console
//									for (i = countdownSeconds; i >= 0; i--) {
//										try {
//											Thread.sleep(1000);
//										} catch (InterruptedException e) {
//										}
//
//										Platform.runLater(() -> {
//											lbRemainTime.setText(String
//													.valueOf(i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
//											lbRemainTime01.setText(String
//													.valueOf(i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
//										});
//									}
//								}
//							});
//							// Start the Thread
//							thread1.start();
//
//							lbStartTime.setText(time1);
//							lbPrepaidMoney.setText(charge);
//							lbPaidMoney.setText(paidMoney);
//
//							int hour = Integer.parseInt(lbRemainTime01.getText().split(":")[0]);
//							int minute = Integer.parseInt(lbRemainTime01.getText().split(":")[1]);
//							int second = Integer.parseInt(lbRemainTime01.getText().split(":")[2]);
//							countdownSeconds = hour * 3600 + minute * 60 + second;
//
//							ChargeDAO chargeDAO = new ChargeDAO();
//							ChargeVO cvo = new ChargeVO(prepaidMoney, countdownSeconds);
//							chargeDAO.getChargeUpdate(cvo, enterNo);
//
//							stageDialog.close();
//						}
//					});
//
//					Scene scene = new Scene(charge);
//					stageDialog.setScene(scene);
//					stageDialog.setResizable(false);
//					stageDialog.show();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
		// 확인버튼 눌렀을 때
//		btnOk.setOnAction(e -> {
//			stageDialog.close();
//		});
//
//		Scene scene = new Scene(userInfo);
//		stageDialog.setScene(scene);
//		stageDialog.setResizable(false);
//		stageDialog.show();
//	} catch (IOException e1) {
//		CommonFunc.alertDisplay(1, "파일 열기 오류", "현재 이용자 정보", "접근할 수 없습니다." + e1.toString());
//	}
//}
	
	// 좌석 상세 정보버튼
//		public void handlerViewSeatInfo(MouseEvent event) {
//			try {
//				if (event.getClickCount() != 2) {
//					return;
//				}
//				Parent seatInfo;
//				seatInfo = FXMLLoader.load(getClass().getResource("/view/seat_info.fxml"));
	//
//				Stage stageDialog = new Stage(StageStyle.UTILITY);
//				stageDialog.initModality(Modality.WINDOW_MODAL);
//				stageDialog.initOwner(recSeat01.getScene().getWindow());
//				stageDialog.setTitle("현재 좌석 상세 정보");
	//
//				// -------------------------------------------
//				Label lbSeatNo = (Label) seatInfo.lookup("#lbSeatNo");
//				Label lbMonitorMo = (Label) seatInfo.lookup("#lbMonitorMo");
//				Label lbComMo = (Label) seatInfo.lookup("#lbComMo");
//				Label lbKeyboardMo = (Label) seatInfo.lookup("#lbKeyboardMo");
//				Label lbMouseMo = (Label) seatInfo.lookup("#lbMouseMo");
//				Label lbSpeakerMo = (Label) seatInfo.lookup("#lbSpeakerMo");
//				Label lbHeadsetMo = (Label) seatInfo.lookup("#lbHeadsetMo");
//				RadioButton rbWork = (RadioButton) seatInfo.lookup("#rbWork");
//				RadioButton rbBroken = (RadioButton) seatInfo.lookup("#rbBroken");
//				Button btnOk = (Button) seatInfo.lookup("#btnOk");
	//
//				lbSeatNo.setText(LoginController.getCurrentSeatNo);
//				rbWork.setDisable(true);
//				rbBroken.setDisable(true);
	//
//				ArrayList<String> list = new ArrayList<String>();
//				EquipDAO equipDAO = new EquipDAO();
//				list = equipDAO.getEquipSeatInfo(LoginController.getCurrentSeatNo);
//				lbMonitorMo.setText(list.get(3));
//				lbComMo.setText(list.get(0));
//				lbKeyboardMo.setText(list.get(2));
//				lbMouseMo.setText(list.get(4));
//				lbSpeakerMo.setText(list.get(5));
//				lbHeadsetMo.setText(list.get(1));
	//
//				// 확인버튼 눌렀을 때
//				btnOk.setOnAction(e -> {
//					stageDialog.close();
//				});
	//
//				Scene scene = new Scene(seatInfo);
//				stageDialog.setScene(scene);
//				stageDialog.setResizable(false);
//				stageDialog.show();
//			} catch (IOException e1) {
//				CommonFunc.alertDisplay(1, "파일 열기 오류", "좌석 상세 정보", "접근할 수 없습니다." + e1.toString());
//			}
	//
//		}
}
