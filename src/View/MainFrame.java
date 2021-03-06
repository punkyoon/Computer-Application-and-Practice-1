package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import Controller.Controller;
import Model.Animal;
import Model.BabyCat;
import Model.BabyMonkey;
import Model.Cloth;
import Model.Food;
import Model.Potion;
import Model.User;

import javax.swing.JButton;

public class MainFrame extends JFrame
{
	private Controller c;
	private JPanel presentPanel;
	
	//setting views
	//private StartPage startPage;
	//private UserSettingPage usersettingPage;
	//private PetSettingPage petsettingPage;
	public Place place;
	public LivingroomPage livingroomPage;
	public ShopPage shopPage;
	public YardPage yardPage;
	public BathroomPage bathroomPage;	
	public BattlePage battlePage;	
	private String userName;
	
	protected JPopupMenu menu;
	protected JMenuItem []items=new JMenuItem[5];
	
	private boolean bathroomClosetFlag=false;
	private boolean bathroomStatFlag=false;
	private boolean livingroomFridgeFlag=false;
	private int shopPetFlag=0;	//1 : cat / 2 : monkey
	
	private Food currentFood;
	private Cloth currentCloth;
	private Animal currentAnimal;
	private int i=0, j=0;
	/**
	 * Create the frame.
	 */
	public MainFrame()
	{
		
	}
	
	public MainFrame(Controller c)
	{
		this.c = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 918, 587);
		
		//items[0]=new JMenuItem(c.getPresentUser().getPet(0).getName());
		//menu.add(items[0]);
		/*
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane = new StartPage();
		setContentPane(contentPane);
		this.add(contentPane);*/
		
		//contentPane.add(startPage);
		
		//usersettingPage=new UserSettingPage();
		//contentPane.add(usersettingPage);
		
		//livingroomPage=new LivingroomPage();
		//contentPane.add(livingroomPage);
		
		//battlePage=new BattlePage();
		//contentPane.add(battlePage);
		
		//setCharacterPage=new SetCharecterPage();
		//contentPane.add(setCharacterPage);
		
		//yardPage=new YardPage();
		//contentPane.add(yardPage);
		
		//shopPage=new ShopPage();
		//contentPane.add(shopPage);
		
		//bathroomPage=new BathroomPage();
		//contentPane.add(bathroomPage);
		//System.out.println("hello?");
	}

	public void gotoStartPage()
	{
		StartPage startPage=new StartPage();
		
		startPage.startBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Start 버튼이 눌렸습니다.");
				c.goUserSetting();
			}
		});
		
		startPage.loadBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					FileInputStream loadFile=new FileInputStream("SaveData.sav");
					ObjectInputStream load=new ObjectInputStream(loadFile);
					Object obj=load.readObject();
					
					//c.setPresentUser((User)load.readObject());
					
					c.getPresentUser().setName((String)load.readObject());
					c.getPresentUser().setGold((int)load.readObject());
					
					c.getPresentUser().setPetList((Animal[])load.readObject());
					c.getPresentUser().setPetIndex((int)load.readObject());
					c.getPresentUser().setClothList((Cloth[])load.readObject());
					c.getPresentUser().setClothIndex((int)load.readObject());
					c.getPresentUser().setFoodList((Food[])load.readObject());
					c.getPresentUser().setFoodIndex((int)load.readObject());
					
					c.getPresentUser().setController((Controller)load.readObject());
					
					load.close();
					System.out.println("Load Done.");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
			}
		});
		
		startPage.exitBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("exit 버튼이 눌렸습니다.");
				c.gameExit();
			}
		});	
		
		this.setContentPane(startPage);
		this.setVisible(true);
	}
	
	public void gotoUserSettingPage()
	{
		UserSettingPage usersettingPage = new UserSettingPage(); 
		
		usersettingPage.okBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				userName = usersettingPage.textField.getText();
				if(userName.isEmpty() == true)
				{
					JOptionPane.showMessageDialog(null, "Please input username", "Warning", JOptionPane.WARNING_MESSAGE);
					System.out.println("유저이름을 입력하세요");
					return;
				}
				
				else
					c.goPetSetting(userName);
			}			
		});
		
		this.setContentPane(usersettingPage);
		this.setVisible(true);
	}
	
	public void gotoPetSettingPage()
	{
		// TODO Auto-generated method stub
		PetSettingPage petsettingPage = new PetSettingPage();
		
		petsettingPage.okBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String petName = petsettingPage.petNameField.getText();
				if(petName.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please input pet name", "Warning", JOptionPane.WARNING_MESSAGE);
					System.out.println("petName이 설정되지 않았습니다.");
					return;
				}
				else if(petsettingPage.getPetNum() == 0)
				{
					JOptionPane.showMessageDialog(null, "Please select pet", "Warning", JOptionPane.WARNING_MESSAGE);
					System.out.println("pet이 선택되지 않았습니다.");
					return;
				}
				else
				{ 
					//petsettingPage.setPetName(petName);
					c.goStartingPoint(petsettingPage.getPetNum(), petName);
					//calling game start method
				}
			}
		});
		
		this.setContentPane(petsettingPage);
		this.setVisible(true);
	}
	
	public void gotogameStartingPoint()
	{

		this.livingroomPage = new LivingroomPage(c);
		this.livingroomPage.btnGoBathroom.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoBathroom();
			}
		});
		
		this.livingroomPage.btnGoShop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoShop();
			}
		});
		
		this.livingroomPage.btnGoYard.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoYard();
			}
		});
		
		this.livingroomPage.fridgeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] list=c.appendFoodItemList();
				if(livingroomFridgeFlag==false)
				{
					livingroomFridgeFlag=true;
					if(list!=null)
					{						
						for(j=0;j<list.length;j++)
						{
							livingroomPage.food[j].setText(list[j]);
							Food tempFood=c.getPresentUser().getFood(j);
							livingroomPage.food[j].addActionListener(new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent e)
								{
									currentFood=tempFood;
									//currentFood=c.getPresentUser().getFood(j);
									menu.show(livingroomPage.foodItemInfo, livingroomPage.foodItemInfo.getWidth()/2, 0);
								}
							});
							livingroomPage.food[j].setVisible(true);
						}
						livingroomPage.labelEmpty.setVisible(false);
					}
					else
						livingroomPage.labelEmpty.setVisible(true);
					if(c.getPresentUser().getPotion()!=null)
					{
						livingroomPage.btnPotion.setText("???");
						livingroomPage.btnPotion.addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								menu.show(livingroomPage.foodItemInfo, livingroomPage.foodItemInfo.getWidth()/2, 0);
							}
						});
						livingroomPage.btnPotion.setVisible(true);
						livingroomPage.labelEmpty.setVisible(false);
					}
					livingroomPage.foodItemInfo.setVisible(true);
				}
				else
				{
					livingroomFridgeFlag=false;
					livingroomPage.foodItemInfo.setVisible(false);
				}
			}
		});
		
		this.bathroomPage = new BathroomPage(c);
		this.bathroomPage.btnGoLivingroom.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoLivingroom();
			}
		});
		
		this.bathroomPage.btnGoShop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoShop();
			}
		});
		
		this.bathroomPage.btnGoYard.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoYard();
			}
		});
		
		this.bathroomPage.closetBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(bathroomClosetFlag==false)
				{
					bathroomClosetFlag=true;
					String list[]=c.appendClosetItemList();
					if(list!=null)
					{
						for(j=0;j<list.length;j++)
						{
							bathroomPage.cloth[j].setText(list[j]);
							Cloth tempCloth=c.getPresentUser().getCloth(j);
							bathroomPage.cloth[j].addActionListener(new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent e)
								{
									currentCloth=tempCloth;
									//currentCloth=c.getPresentUser().getCloth(j);
									menu.show(bathroomPage.clothItemInfo, bathroomPage.clothItemInfo.getWidth()/2, 0);
								}
							});
							bathroomPage.cloth[j].setVisible(true);
						}
						bathroomPage.emptyLabel.setVisible(false);
					}
					else
						bathroomPage.emptyLabel.setVisible(true);
					bathroomPage.UsersClothItem.setVisible(true);
					bathroomPage.clothItemInfo.setVisible(true);
				}				
				else
				{
					bathroomClosetFlag=false;
					bathroomPage.UsersClothItem.setVisible(false);
					bathroomPage.clothItemInfo.setVisible(false);
				}
			}
		});
		
		this.bathroomPage.btnGamesave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					FileOutputStream saveFile=new FileOutputStream("SaveData.sav");
					ObjectOutputStream save=new ObjectOutputStream(saveFile);
					
					save.writeObject(c.getPresentUser().getName());
					save.writeObject(c.getPresentUser().getGold());
					
					save.writeObject(c.getPresentUser().getPetList());
					save.writeObject(c.getPresentUser().getUserPetSize()-1);
					save.writeObject(c.getPresentUser().getClothList());
					save.writeObject(c.getPresentUser().getClothIndex());
					save.writeObject(c.getPresentUser().getFoodList());
					save.writeObject(c.getPresentUser().getFoodIndex());
					
					save.writeObject(c.getPresentUser().getController());
										
					save.close();
					
					System.out.println("Save Done.");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		this.shopPage = new ShopPage();
		this.shopPage.btnGoLivingroom.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoLivingroom();
			}
		});
		
		this.shopPage.btnGoBathroom.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoBathroom();
			}
		});
		
		this.shopPage.btnGoYard.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoYard();
			}
		});
		
		shopPage.btnCloth[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.getPresentUser().buyItem(shopPage.clothList[0]);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnCloth[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.getPresentUser().buyItem(shopPage.clothList[1]);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnCloth[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.getPresentUser().buyItem(shopPage.clothList[2]);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnFood[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.getPresentUser().buyItem(shopPage.foodList[0]);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnFood[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.getPresentUser().buyItem(shopPage.foodList[1]);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnFood[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.getPresentUser().buyItem(shopPage.foodList[2]);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnPet[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(shopPetFlag==0)
				{
					shopPetFlag=1;
					shopPage.btnOk.setVisible(true);
					shopPage.labelInputPetName.setVisible(true);
					shopPage.textField.setVisible(true);
				}
				else
				{
					shopPetFlag=0;
					shopPage.btnOk.setVisible(false);
					shopPage.labelInputPetName.setVisible(false);
					shopPage.textField.setVisible(false);
				}
				//c.getPresentUser().buyItem(shopPage.animalList[0]);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnPet[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(shopPetFlag==0)
				{
					shopPetFlag=2;
					shopPage.btnOk.setVisible(true);
					shopPage.labelInputPetName.setVisible(true);
					shopPage.textField.setVisible(true);
				}
				else
				{
					shopPetFlag=0;
					shopPage.btnOk.setVisible(false);
					shopPage.labelInputPetName.setVisible(false);
					shopPage.textField.setVisible(false);
				}
				
				//c.getPresentUser().buyItem(shopPage.animalList[0]);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnPotion.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.getPresentUser().buyItem(shopPage.potion);
				showSellItems();
				c.viewShopStatus();
			}
		});
		
		shopPage.btnOk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(shopPage.textField.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please input pet name", "Warning", JOptionPane.WARNING_MESSAGE);
					System.out.println("petName이 설정되지 않았습니다.");
					return;
				}
				else
				{
					if(shopPetFlag==1)
					{
						shopPage.animalList[0]=new BabyCat(shopPage.textField.getText(), 200, false);
						c.getPresentUser().buyItem(shopPage.animalList[0]);
						shopPage.textField.setText("");
					}
					else if(shopPetFlag==2)
					{
						shopPage.animalList[1]=new BabyMonkey(shopPage.textField.getText(), 200, false);
						c.getPresentUser().buyItem(shopPage.animalList[1]);
						shopPage.textField.setText("");
					}
				}
				shopPetFlag=0;
				shopPage.btnOk.setVisible(false);
				shopPage.labelInputPetName.setVisible(false);
				shopPage.textField.setVisible(false);
				c.viewShopStatus();
			}
		});
		
		this.yardPage = new YardPage(c);
		this.yardPage.btnGoLivingroom.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoLivingroom();
			}	
		});
		
		this.yardPage.btnGoBathroom.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoBathroom();
			}
		});
		
		this.yardPage.btnGoShop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				gotoShop();
			}
		});
		this.setContentPane(livingroomPage);
		this.setVisible(true);	
	}
	
	public void gotoLivingroom()
	{
		menu=new JPopupMenu();
		//currentFood=null;
		for(i=0;i<c.getPresentUser().getUserPetSize();i++)
		{
			
			String petName = c.getPresentUser().getPet(i).getName();
			System.out.println(i + "번 팻 : " + c.getPresentUser().getPet(i).getClass());
			this.items[i]=new JMenuItem(petName);
			System.out.println("User Pet size : "+c.getPresentUser().getUserPetSize()+" / pet name : "+ petName);
			System.out.println("");
			Animal tempPet = c.getPresentUser().getPet(i);
			JButton tempBtn=livingroomPage.food[i];
			JButton tempBtn2=shopPage.btnUserFood[i];
			items[i].addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(c.getPresentUser().getPotion()!=null)	//SOME PROBLEM.. BUT I DON'T KNOW HOW TO FIX IT..
					{
						tempPet.useItem(shopPage.potion);
						c.getPresentUser().useItem();
						//livingroomPage.btnPotion.setVisible(false);
					}
					else
					{
						c.getPresentUser().useItem(currentFood);
						tempPet.useItem(currentFood);
						tempBtn.setVisible(false);
						tempBtn2.setVisible(false);
					}
					currentFood=null;
					//livingroomPage.btnPotion.setVisible(false);
					//c.getPresentUser().getPet(i).useItem(currentFood);
				}
			});
			menu.add(this.items[i]);
		}
		c.viewLivingroomStatus();
		this.setContentPane(livingroomPage);
		this.setVisible(true);
	}
	
	public void gotoBathroom()
	{
		menu=new JPopupMenu();
		//currentCloth=null;
		for(i=0;i<c.getPresentUser().getUserPetSize();i++)
		{
			this.items[i]=new JMenuItem(c.getPresentUser().getPet(i).getName());
			System.out.println("User Pet size : "+c.getPresentUser().getUserPetSize()+" / pet name : "+c.getPresentUser().getPet(i).getName());
			Animal tempPet = c.getPresentUser().getPet(i);
			items[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					tempPet.dress(currentCloth);
					currentCloth=null;
					//c.getPresentUser().getPet(i).useItem(currentCloth);
				}
			});
			menu.add(this.items[i]);
		}
		c.viewBathroomStatus();
		this.setContentPane(bathroomPage);
		this.setVisible(true);
	}
	public void gotoYard()
	{
		c.viewYardStatus();
		this.setContentPane(yardPage);
		this.setVisible(true);
	}
	public void gotoShop()
	{
		showSellItems();
		c.viewShopStatus();
		this.setContentPane(shopPage);
		this.setVisible(true);
	}
	
	public void showSellItems()
	{
		String clothList[]=c.getPresentUser().getClothItemNameList();
		String foodList[]=c.getPresentUser().getFoodItemList();
		
		if(clothList!=null)
		{
			for(int i=0;i<clothList.length;i++)
			{
				shopPage.btnUserCloth[i].setText(clothList[i]);
				Cloth tempCloth=c.getPresentUser().getCloth(i);
				JButton tempBtn=shopPage.btnUserCloth[i];
				JButton tempBtn2=bathroomPage.cloth[i];
				shopPage.btnUserCloth[i].addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						tempBtn.setVisible(false);
						tempBtn2.setVisible(false);
						c.getPresentUser().sellItem(tempCloth);
						c.viewShopStatus();
					}
				});
				shopPage.btnUserCloth[i].setVisible(true);
			}
		}
		if(foodList!=null)
		{
			for(int i=0;i<foodList.length;i++)
			{
				shopPage.btnUserFood[i].setText(foodList[i]);
				Food tempFood=c.getPresentUser().getFood(i);
				JButton tempBtn=shopPage.btnUserFood[i];
				JButton tempBtn2=livingroomPage.food[i];
				shopPage.btnUserFood[i].addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						tempBtn.setVisible(false);
						tempBtn2.setVisible(false);
						c.getPresentUser().sellItem(tempFood);
						c.viewShopStatus();
					}
				});
				shopPage.btnUserFood[i].setVisible(true);
			}
		}
		
		if(c.getPresentUser().getPotion()!=null)
		{
			shopPage.btnUserPotion.setText("???");
			shopPage.btnUserPotion.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					shopPage.btnUserPotion.setVisible(false);
					c.getPresentUser().sellItem(c.getPresentUser().getPotion());
					c.viewShopStatus();
				}
			});
			shopPage.btnUserPotion.setVisible(true);
		}
	}
	
	public void showAnimalStat(String []list)
	{
		if(bathroomStatFlag==false)
		{
			bathroomStatFlag=true;
			bathroomPage.statArea.setText("");
			for(int i=0;i<list.length;i++)
				bathroomPage.statArea.append(list[i]+"\n");
			
			bathroomPage.statArea.setVisible(true);
			bathroomPage.petStat.setVisible(true);
			bathroomPage.petInfo.setVisible(true);
		}
		else
		{
			bathroomStatFlag=false;
			bathroomPage.statArea.setVisible(false);
			bathroomPage.petStat.setVisible(false);
			bathroomPage.petInfo.setVisible(false);
		}
	}
	
	/*
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==startPage.startBtn)
		{
			usersettingPage=new UserSettingPage(); 
			contentPane.removeAll();
			contentPane.add(usersettingPage);
			setVisible(true);
			usersettingPage.setVisible(true);
		}
	}*/
}