package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

	private List<ImageTile> tileList;
	private Hero hero;
	private int turns;
	private String NIVEL = "rooms/room0" + ".txt";
	private String newLevel;
	private int NIVELATUAL = Character.getNumericValue(NIVEL.charAt(10));
	private Point2D heroPos = new Point2D(1,1);
	private Catchables[] inventory = new Catchables[3];
	private List<String> keyIds = new ArrayList<>();
	private Map<GameElements,String> map = new LinkedHashMap<>();
	private int PONTOS;
	private String NOME = JOptionPane.showInputDialog("Introduzir username:" ,"Jogador");
	
	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	private GameEngine() {
		gui.registerObserver(this);
		gui.setSize(GRID_WIDTH, GRID_HEIGHT + 2);
		gui.go();
		tileList = new ArrayList<>();
	}

	public void start() throws FileNotFoundException {
		for (int i = 0; i < 3; i++) {
			if(inventory[i] != null) {
				inventory[i].setPosition(new Point2D(i,GRID_HEIGHT));
				addGameElement(inventory[i]);
			}
		}
		if(map.containsValue(newLevel)) {
			for(GameElements ge : map.keySet()) {
				if(map.get(ge).equals(newLevel)) {
					if(ge instanceof Door && keyIds.contains(((Door) ge).getIdKey())) {
						((Door) ge).setOpen(true);
					}
					addGameElement(ge);
				}
				hero.setPosition(heroPos);
			}
		} else {
			lerFitxero(NIVEL);
		}
		HealthBar.barraVidaVerde();
		gui.update();
	}

	@Override
	public void update(Observed source) {

		int key = ((ImageMatrixGUI) source).keyPressed();

		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
			hero.move(key);
			for (ImageTile img : tileList) {
				if (img instanceof MovableElements && !(img instanceof Hero)) {
					((MovableElements) img).move();
				}
			}
			turns++;
		}
		if (key == KeyEvent.VK_Q || key == KeyEvent.VK_W || key == KeyEvent.VK_E) {
			for (ImageTile img : tileList) {
				if (img instanceof MovableElements && !(img instanceof Hero)) {
					((MovableElements) img).move();
				}
				if (img instanceof Catchables) {
					((Catchables) img).drop(key);
				}
			}
			turns++;
		}
		PONTOS = hero.getPOINTS();
		HealthBar.barraVidaVerde();
		gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns + " Points:");
		try {
			nextLevel();
			pontuacoes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (hero.getVida() <= 0) {
			ImageMatrixGUI.getInstance().setMessage("JA TOMBASTE NABO!");
			System.exit(0);
		}
		if(getGameElement(hero.getPosition()).size() == 3 && getGameElement(hero.getPosition()).get(2) instanceof Treasure) {
			ImageMatrixGUI.getInstance().setMessage("MUITI PARABENI");
			System.exit(0);
		}
		Catchables.getInventory();
		gui.setStatusMessage("ROGUE Starter Package - Username: " + NOME +  " Turns: " + turns + " Points: " + PONTOS);
		gui.update();
	}

	public void lerFitxero(String mapa) throws FileNotFoundException {
		if(NIVELATUAL == 0) {
			hero = new Hero(heroPos);
			addGameElement(hero);
		}
		hero.setPosition(heroPos);
		int count = 0;
		Scanner sc = new Scanner(new File(mapa));
		while (sc.hasNextLine() && count < GRID_HEIGHT) {
			String x = sc.nextLine();
			for (int i = 0; i < GRID_WIDTH; i++) {
				String s = String.valueOf(x.charAt(i));
				GameElements elemento = GameElements.create(s, new Point2D(i, count), null, null, null);
				addGameElement(elemento);
			}
			count++;
		}
		sc.nextLine();
		while (sc.hasNextLine()) {
			String a = sc.nextLine();
			if (!a.equals("")) {
				String[] separacao = a.split(",");
				String element = separacao[0];
				int ab = Integer.parseInt(separacao[1]);
				int or = Integer.parseInt(separacao[2]);
				if (separacao.length == 4 && separacao[3] != null) { // Criar Key
					String st1 = separacao[3];
					addGameElement(GameElements.create(element, new Point2D(ab, or), st1, null, null));
				}
				if (separacao.length > 5  && separacao[4] != null) { // Criar Door
					String st1 = separacao[3];
					String st2 = null;
					int x = Integer.parseInt(separacao[4]);
					int y = Integer.parseInt(separacao[5]);
					if(separacao.length == 7) {
						st2 = separacao[6];
						Door door = (Door) GameElements.create(element, new Point2D(ab, or), st1, new Point2D(x, y), st2);
						if(keyIds.contains(st2)) {
							door.setOpen(true);
						}
						addGameElement(door);
					} else {
						addGameElement(GameElements.create(element, new Point2D(ab, or), st1, new Point2D(x, y), st2));
					}
				}
				if (separacao.length < 4) { // Criar todos os outros Game Elements
					addGameElement(GameElements.create(element, new Point2D(ab, or), null, null, null));
				}
			}
		}
		sc.close();
	}

	public List<String> getKeyIds() {
		return keyIds;
	}

	public void removeGameElement(ImageTile i) {
		tileList.remove(i);
		gui.removeImage(i);
	}

	public void addGameElement(ImageTile i) {
		tileList.add(i);
		gui.addImage(i);
	}

	public Hero getHero() {
		return hero;
	}

	public int getTurns() {
		return turns;
	}

	public List<GameElements> getGameElement(Point2D position) {
		List<GameElements> ge = new ArrayList<>();
		for (ImageTile img : tileList) {
			if (img.getPosition().equals(position)) {
				if (img instanceof GameElements) {
					ge.add((GameElements) img);
				}
			}
		}
		return ge;
	}
	
	public void nextLevel() throws FileNotFoundException {
		List<GameElements> ge = getGameElement(hero.getPosition());
		if(ge.size() == 3 && ge.get(2) instanceof Door) {
			if(map.containsValue(newLevel)) {
				for(GameElements g : map.keySet()) {
					if(map.get(g).equals(newLevel) && !(g instanceof Catchables)) {
						removeGameElement(g);
					}
				}
			}
			saveMap();
			newLevel = ((Door) ge.get(2)).getNewRoom();
			if(!(((Door) ge.get(2)).getIdKey() == null)) {
				keyIds.add(((Door) ge.get(2)).getIdKey());
			}
			heroPos = ((Door) ge.get(2)).getNewRoomPos();
			NIVEL = "rooms/" + newLevel + ".txt";
			for (int i = 0; i < 3; i++) {
				if(!(getGameElement(new Point2D(i,GRID_HEIGHT)).isEmpty())) {	
						inventory[i] = (Catchables) getGameElement(new Point2D(i,GRID_HEIGHT)).get(0);
				}
			}
			NIVELATUAL = Character.getNumericValue(newLevel.charAt(4));
			Iterator<ImageTile> it = tileList.iterator();
			while(it.hasNext()) {
				ImageTile img = it.next();
				if(!img.equals(hero)) {
					it.remove();
					gui.removeImage(img);
				}
			}
			start();
		}
	}

	public Map<GameElements,String> saveMap() {
			for (int i = 0; i < GRID_HEIGHT; i++) {
				for (int j = 0; j < GRID_WIDTH; j++) {
					for(GameElements g : getGameElement(new Point2D(j,i))) {
						map.put(g, "room" + Integer.toString(getNIVELATUAL()));
					}
				}
			}	
		return map;
	}

	public int getNIVELATUAL() {
		return NIVELATUAL;
	}
	
	public Catchables[] getInventory() {
		return inventory;
	}

	public void setInventory(Catchables[] inventory) {
		this.inventory = inventory;
	}
	
	public void pontuacoes() throws IOException {
		if(hero.getVida() <= 0 || ((getGameElement(hero.getPosition()).size() == 3 && getGameElement(hero.getPosition()).get(2) instanceof Treasure))) {
			File pontuacoes = new File("pontuacoes");
			if(!pontuacoes.exists()) {
				FileWriter fw = new FileWriter(pontuacoes);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("Top 5 Pontuacoes:");
				pw.println(PONTOS + ": " + NOME);
				pw.println(0 + ": username");
				pw.println(0 + ": username");
				pw.println(0 + ": username");
				pw.println(0 + ": username");
				pw.close();
			} else {
				Scanner sc = new Scanner(pontuacoes);
				String[] vals = new String[5];
				Writer output;
				output = new BufferedWriter(new FileWriter(pontuacoes, true));
				output.append(PONTOS + ": " + NOME + "\n");
				output.close();
				for (int i = 0; i < 2; i++) {
					if(sc.hasNextLine()) {
						sc.nextLine();
					}
				}
				while(sc.hasNextLine()) {
					String s = sc.nextLine();
					for (int i = 0; i < vals.length; i++) {
						vals[i] = s;
						String[] st = s.split(": ");
						String val = st[0];
						vals[i] = val;
					}	
				}
				FileReader pont = new FileReader("pontuacoes");
				BufferedReader br = new BufferedReader(pont);
				ArrayList<String> array = new ArrayList<>();
				String st = "";
				while((st = br.readLine()) != null) {
					array.add(st);
				}
				br.close();
				Collections.sort(array, Collections.reverseOrder());
				array.remove(6);
				sc.close();
				FileWriter fw = new FileWriter("pontuacoes");
				for(String s : array) {
					fw.write(s);
					fw.write("\r\n");
				}
				fw.close();
			}
		}
	}
}
