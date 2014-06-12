package windows;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MainMenu extends JFrame {

	main.MasterControlVariables mcv = null;
	private JPanel contentPane;

	JScrollPane scrollPane;
	JList jlist_bscSrchTrms;
	private JTextField txtFld_addTerm;
	private JButton btn_rmvSrchTrm;
	private JButton btn_runSkimmer;
	private JButton btn_rmv;

	String urlText = "https://dallas.craigslist.org/search/?sort=rel&areaID=21&subAreaID=&query=SEARCHTERM&catAbb=sss";
	String srchTrm = null;

	Vector<String> v_htmlLns = new Vector<String>();
	Vector<main.Listing> v_lstngs = new Vector<main.Listing>();
	private JLabel label;
	private JLabel label_1;
	private JLabel lblExludeWordsFrom;
	private JTextField txtFld_grtrThan;
	private JTextField txtFld_lessThan;
	private JTextField txtFld_excldWrds;

	String grtrThan = "NA";
	String lessThan = "NA";
	String excldWrds = "NA";
	String excldCtgrys = "NA";
	String srchTrmMdfrs = "NA|NA|NA";
	String[] lnSplt = null;
	String[] lnSplt1 = null;

	Double d_grtrThan = Double.NaN;
	Double d_lessThan = Double.NaN;	
	String excTrms = "NA";
	String excldCtgrys1 = "NA";
	
	Vector<main.Listing> v_rmvLstngs = new Vector<main.Listing>();
	Vector<main.Listing> v_dsplyLstngs = new Vector<main.Listing>();
	private JScrollPane scrollPane_1;
	private JTable tbl;

	DefaultTableModel dtm = null;
	private JButton btn_viewPage;
	private JLabel lblCategory;
	private JTextField txtFld_excCtgrys;

	private Vector<String> v_mstRcntFlLstngs = new Vector<String>();
	private Vector<String> v_prevLstngs = new Vector<String>();
	private JButton btn_runCntnsly;

	boolean newListing = false;
	
	InputStream in = null;
	AudioStream as = null;
	
	double waitTime = 0;
	
	public MainMenu(main.MasterControlVariables mcv) {

		this.mcv = mcv;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane(); scrollPane.setBounds(10, 30, 185, 136);	contentPane.add(scrollPane);
		jlist_bscSrchTrms = new JList(); scrollPane.setViewportView(jlist_bscSrchTrms);

		JLabel lblBasicSearchTerms = new JLabel("Basic Search Terms"); lblBasicSearchTerms.setBounds(10, 5, 141, 14); contentPane.add(lblBasicSearchTerms);
		JLabel lblAddSearchTerm = new JLabel("Add Search Term"); lblAddSearchTerm.setBounds(10, 166, 141, 14); contentPane.add(lblAddSearchTerm);

		txtFld_addTerm = new JTextField(); txtFld_addTerm.setBounds(10, 180, 86, 20);	contentPane.add(txtFld_addTerm);	txtFld_addTerm.setColumns(10);

		btn_rmvSrchTrm = new JButton("Remove"); btn_rmvSrchTrm.setBounds(106, 179, 89, 23); contentPane.add(btn_rmvSrchTrm);
		btn_runSkimmer = new JButton("Run Skimmer!"); btn_runSkimmer.setBounds(10, 227, 141, 23); contentPane.add(btn_runSkimmer);

		label = new JLabel(">"); label.setBounds(222, 11, 13, 14); contentPane.add(label);
		label_1 = new JLabel("<"); label_1.setBounds(267, 11, 13, 14); contentPane.add(label_1);

		lblExludeWordsFrom = new JLabel("Exclude Words From Description"); lblExludeWordsFrom.setBounds(320, 11, 236, 14); contentPane.add(lblExludeWordsFrom);

		txtFld_grtrThan = new JTextField();	txtFld_grtrThan.setBounds(215, 29, 35, 20);	contentPane.add(txtFld_grtrThan); txtFld_grtrThan.setColumns(10);
		txtFld_lessThan = new JTextField();	txtFld_lessThan.setColumns(10);	txtFld_lessThan.setBounds(260, 29, 36, 20);	contentPane.add(txtFld_lessThan);
		txtFld_excldWrds = new JTextField(); txtFld_excldWrds.setColumns(10); txtFld_excldWrds.setBounds(320, 29, 439, 20);	contentPane.add(txtFld_excldWrds);
		txtFld_excCtgrys = new JTextField(); txtFld_excCtgrys.setColumns(10); txtFld_excCtgrys.setBounds(783, 29, 157, 20);	contentPane.add(txtFld_excCtgrys);
		
		scrollPane_1 = new JScrollPane(); scrollPane_1.setBounds(205, 71, 951, 179); contentPane.add(scrollPane_1);

		tbl = new JTable();
		tbl.setAutoCreateRowSorter(true);
		tbl.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null, null, null, null, null},
				},
				new String[] {
						"New column", "New column", "New column", "New column", "New column", "New column"
				}
				));
		tbl.getColumnModel().getColumn(1).setPreferredWidth(35);
		tbl.getColumnModel().getColumn(1).setMinWidth(35);
		

		scrollPane_1.setViewportView(tbl);

		btn_viewPage = new JButton("View Page"); btn_viewPage.setBounds(205, 261, 141, 23);	contentPane.add(btn_viewPage);

		lblCategory = new JLabel("Exclude Categories");
		lblCategory.setBounds(783, 5, 236, 14);
		contentPane.add(lblCategory);


		btn_rmv = new JButton("Remove Listing Forever"); btn_rmv.setBounds(692, 261, 236, 23); contentPane.add(btn_rmv);
		btn_runCntnsly = new JButton("Keep Running"); btn_runCntnsly.setBounds(10, 261, 141, 23); contentPane.add(btn_runCntnsly);

		startUp();
	}

	public void addKeyListenerToModifierTextFieldss(JTextField txtfld){

		txtfld.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == 10){
					addSearchTermModifiersToListAndFile();	
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
		});

	}

	private void addSearchTermModifiersToListAndFile(){

		srchTrm = "NA";
		grtrThan = "NA";
		lessThan = "NA";
		excldWrds = "NA";
		excldCtgrys = "NA";
		srchTrmMdfrs = "NA|NA|NA|NA";

		if(jlist_bscSrchTrms.getSelectedIndex() != -1){

			if(txtFld_grtrThan.getText() != null){
				if(!txtFld_grtrThan.getText().equalsIgnoreCase("")){
					grtrThan = txtFld_grtrThan.getText(); 
				}
			}
			if(txtFld_lessThan.getText() != null){
				if(!txtFld_lessThan.getText().equalsIgnoreCase("")){
					lessThan = txtFld_lessThan.getText(); 
				}
			}
			if(txtFld_excldWrds.getText() != null){
				if(!txtFld_excldWrds.getText().equalsIgnoreCase("")){
					excldWrds = txtFld_excldWrds.getText();
				}
			}
			if(txtFld_excCtgrys.getText() != null){
				if(!txtFld_excCtgrys.getText().equalsIgnoreCase("")){
					excldCtgrys = txtFld_excCtgrys.getText();
				}
			}

			srchTrm = jlist_bscSrchTrms.getSelectedValue().toString();
			srchTrmMdfrs = grtrThan + "|" + lessThan + "|" + excldWrds + "|" + excldCtgrys;

			mcv.addSrchTrmAndModifiersToHashMap(srchTrm, srchTrmMdfrs);

		}
	}

	private void addSrchTermToListandFile() {

		if(txtFld_addTerm.getText() != null){
			if(!txtFld_addTerm.getText().equalsIgnoreCase("")){
				if(!mcv.v_bscSrchTrms.contains(txtFld_addTerm.getText())){
					mcv.v_bscSrchTrms.add(txtFld_addTerm.getText());
				}
				mcv.saveBasicSearchTermListToFile();
				jlist_bscSrchTrms.setListData(mcv.v_bscSrchTrms);
				txtFld_addTerm.setText("");
			}
		}
	}

	public Vector<main.Listing> getListingsFromHTMLlines(Vector<String> htmlLns){

		Vector<main.Listing> v_lstngs = new Vector<main.Listing>();
		main.Listing tmpLstng = null;

		String lstngLn = null;
		String[] lstngLnSplt = null;
		String[] lstngSplt = null;
		String[] subLnSplt = null;
		String[] subLnSplt1 = null;

		for(String s: v_htmlLns){
			if(s.contains("<p class=\"row\" data-pid")){
				lstngLn = s;
				break;
			}
		}

		lstngLnSplt = lstngLn.split("date\">");

		for(int iii = 1; iii < lstngLnSplt.length; iii++){

			v_lstngs.add(new main.Listing());
			tmpLstng = v_lstngs.lastElement(); 

			lstngSplt = lstngLnSplt[iii].split("</span>");

			tmpLstng.date = lstngSplt[0];

			for(String s: lstngSplt){

				if(s.contains("<a href=\"") && !s.contains("</p>") && !s.contains("<span class=")){
					subLnSplt = s.replace("</a>", "").split(">");
					subLnSplt1 = subLnSplt[0].split("/"); 
					tmpLstng.refNum = subLnSplt1[subLnSplt1.length-1].replace(".html\"", ""); 

					tmpLstng.dscrp = subLnSplt[1].replace("</a>", "");
				}

				if(s.contains("data-cat")){
					subLnSplt = s.split("data-cat=\"");
					subLnSplt1 = subLnSplt[1].split("\"");
					tmpLstng.ctgry = subLnSplt1[0];
				}

				if(s.contains("price") && !s.contains("data") && !s.contains("<a href=")){
					subLnSplt = s.split(";");
					tmpLstng.price = subLnSplt[1];
				}

				if(s.contains("small")){
					subLnSplt = s.split("> ");
					subLnSplt1 = subLnSplt[2].split("<");
					tmpLstng.location = subLnSplt1[0]; 					
				}

				if(s.contains("pic")){
					tmpLstng.pic = "yes";
				}

			}

		}

		return v_lstngs;
	}

	public void loadSearchTermModifierBoxes(){

		srchTrm = "NA";
		grtrThan = "NA";
		lessThan = "NA";
		excldWrds = "NA";
		excldCtgrys = "NA";
		srchTrmMdfrs = "NA|NA|NA|NA";

		if(jlist_bscSrchTrms.getSelectedIndex() != -1){
			srchTrm = jlist_bscSrchTrms.getSelectedValue().toString(); 

			if(!mcv.h_srchTrmNMdfrs.containsKey(jlist_bscSrchTrms.getSelectedValue())){
				mcv.addSrchTrmAndModifiersToHashMap(jlist_bscSrchTrms.getSelectedValue().toString(), srchTrmMdfrs);
			}

			srchTrmMdfrs = mcv.h_srchTrmNMdfrs.get(srchTrm);
			lnSplt = srchTrmMdfrs.split("\\|");

			grtrThan = lnSplt[0];
			lessThan = lnSplt[1];
			excldWrds = lnSplt[2];
			excldCtgrys = lnSplt[3];
		}

		txtFld_grtrThan.setText(grtrThan);
		txtFld_lessThan.setText(lessThan);
		txtFld_excldWrds.setText(excldWrds);
		txtFld_excCtgrys.setText(excldCtgrys);
	}

	private void removeBasicSearchTerm() {
		if(jlist_bscSrchTrms.getSelectedIndex() != -1){
			mcv.v_bscSrchTrms.remove(jlist_bscSrchTrms.getSelectedValue());
			mcv.saveBasicSearchTermListToFile();
			jlist_bscSrchTrms.setListData(mcv.v_bscSrchTrms);

			mcv.h_srchTrmNMdfrs.remove(jlist_bscSrchTrms.getSelectedValue());
			mcv.saveSearchTermModsListToFile();
		}
	}

	public void runSkimmer() {

		v_dsplyLstngs.clear();
		
		for(int iii = 0; iii < jlist_bscSrchTrms.getModel().getSize(); iii++){

			d_grtrThan = Double.NaN;
			d_lessThan = Double.NaN;
			excTrms = "NA";
			excldCtgrys1 = "NA";
			
			jlist_bscSrchTrms.setSelectedIndex(iii);
			srchTrm = jlist_bscSrchTrms.getSelectedValue().toString();

			urlText = "https://dallas.craigslist.org/search/sss/dal?sort=date&catAbb=sss&query=SEARCHTERM";			
			urlText = urlText.replace("SEARCHTERM", srchTrm.replace(" ", "%20"));

			v_rmvLstngs.clear();

			v_htmlLns.clear();
			v_htmlLns.addAll(mcv.wpr.getHTMLinLines(urlText));

			v_lstngs.clear();
			v_lstngs.addAll(getListingsFromHTMLlines(v_htmlLns));

			lnSplt = mcv.h_srchTrmNMdfrs.get(srchTrm).split("\\|");

			if(!lnSplt[0].equalsIgnoreCase("NA")){
				d_grtrThan = Double.valueOf(lnSplt[0]);				
			}
			if(!lnSplt[1].equalsIgnoreCase("NA")){
				d_lessThan = Double.valueOf(lnSplt[1]);
			}

			excTrms = lnSplt[2];
			excldCtgrys1 = lnSplt[3];
			
			for(main.Listing lst: v_lstngs){

				lst.srchTrm = srchTrm;

				if(lst.price == null){
					v_rmvLstngs.add(lst);
				}else{
					if(Double.valueOf(lst.price) <= d_grtrThan){
						v_rmvLstngs.add(lst);
					}
					if(Double.valueOf(lst.price) >= d_lessThan){
						v_rmvLstngs.add(lst);
					}
				}
				
				if(!excTrms.equalsIgnoreCase("NA")){

					lnSplt1 = excTrms.split(";");

					for(String excTrm: lnSplt1){
						if(lst.dscrp.toUpperCase().contains(excTrm.toUpperCase())){
							v_rmvLstngs.add(lst);
						}
					}
				}
				
				if(!excldCtgrys1.equalsIgnoreCase("NA")){

					lnSplt1 = excldCtgrys1.split(";");

					for(String excCtgry: lnSplt1){						
						if(excCtgry.toUpperCase().contains(lst.ctgry.toUpperCase())){
							v_rmvLstngs.add(lst);
						}
					}
				}
				
			}

			v_lstngs.removeAll(v_rmvLstngs);
			v_dsplyLstngs.addAll(v_lstngs);
		}

		loadDisplayTable();
		
	}

	public void loadDisplayTable(){

		v_rmvLstngs.clear();

		for(int iii = 0; iii < v_dsplyLstngs.size(); iii++){
			for(int jjj = 0; jjj < mcv.v_rmvdLstngs.size(); jjj++){
				if(v_dsplyLstngs.get(iii).refNum.equalsIgnoreCase(mcv.v_rmvdLstngs.get(jjj).split("\\|")[3])){
					v_rmvLstngs.add(v_dsplyLstngs.get(iii));
					break;
				}
			}
		}

		v_dsplyLstngs.removeAll(v_rmvLstngs);

		dtm = new DefaultTableModel();
		dtm.setRowCount(v_dsplyLstngs.size());
		dtm.setColumnCount(6);
		//		dtm.set

		for(int iii = 0; iii < v_dsplyLstngs.size(); iii++){
			dtm.setValueAt(v_dsplyLstngs.get(iii).srchTrm, iii, 0);
			dtm.setValueAt(v_dsplyLstngs.get(iii).price, iii, 1);
			dtm.setValueAt(v_dsplyLstngs.get(iii).date, iii, 2);
			dtm.setValueAt(v_dsplyLstngs.get(iii).dscrp, iii, 3);
			dtm.setValueAt(v_dsplyLstngs.get(iii).refNum, iii, 4);
			dtm.setValueAt(v_dsplyLstngs.get(iii).ctgry, iii, 5);
		}

		tbl.setModel(dtm);

		compareWithPreviousListings();
		
		saveMostRecentFilteredListings();

	}

	private void compareWithPreviousListings() {
		v_prevLstngs.clear();
		v_prevLstngs.addAll(mcv.fm.getFileInLines(mcv.f_mstRcntLstngs));
		
		v_mstRcntFlLstngs.clear();
		v_mstRcntFlLstngs.addAll(mcv.guih.getTablesValues(tbl));
		
		for(int iii = 0; iii < v_mstRcntFlLstngs.size(); iii++){

			lnSplt = v_mstRcntFlLstngs.get(iii).split("\\|");
			
			for(int jjj = 0; jjj < v_prevLstngs.size(); jjj++){
				newListing = true;
				lnSplt1 = v_prevLstngs.get(jjj).split("\\|");
				
				if(lnSplt[4].equalsIgnoreCase(lnSplt1[4])){
					newListing = false;
					break;
				}
			}
			
			if(newListing){
				break;
			}
		}
		
		if(newListing){
			soundAlert();
		}
	}

	public void soundAlert() {
        
        try {
             in = new FileInputStream(mcv.f_audioAlert);
        } catch (FileNotFoundException e) {
             System.out.println("Media file not present in C drive.");
             e.printStackTrace();
        }
        
        try {
             as = new AudioStream(in);
        } catch (IOException e) {
             e.printStackTrace();
        }

        AudioPlayer.player.start(as);
	}

	private void saveMostRecentFilteredListings() {
		v_mstRcntFlLstngs.clear();
		v_mstRcntFlLstngs.addAll(mcv.guih.getTablesValues(tbl));
		mcv.fm.writeOutLinesToFile(mcv.f_mstRcntLstngs, v_mstRcntFlLstngs);
	}

	private void openWebPage() {

		if(tbl.getSelectedRow() != -1){

			Desktop desktop = Desktop.getDesktop();	
			String uriText = "https://dallas.craigslist.org/dal/CATEGORY/REFERENCE.html";
			String reference = tbl.getValueAt(tbl.getSelectedRow(), 4).toString();
			String category = tbl.getValueAt(tbl.getSelectedRow(), 5).toString();

			uriText = uriText.replace("CATEGORY", category);
			uriText = uriText.replace("REFERENCE", reference);

			try {
				URI uri = new URI(uriText);
				desktop.browse(uri);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}

	private void removeListingForever() {
		int row = tbl.getSelectedRow(); 
		if(row != -1){
			String outln = tbl.getValueAt(row, 1) + "|" + tbl.getValueAt(row, 2) + "|" + tbl.getValueAt(row, 3) + "|" +  tbl.getValueAt(row, 4);
			mcv.addListingToRemoveListings(outln);
			loadDisplayTable();
		}

	}

	public void runContinously() {

		waitTime = 3600000 + Math.random() * 600000;
		
		while(true){
System.out.println("Searching Pages");			
			runSkimmer();
System.out.println("Now Waiting");
			try {
				Thread.sleep(900000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void startUp(){

		jlist_bscSrchTrms.setListData(mcv.v_bscSrchTrms);
		if(!mcv.v_bscSrchTrms.isEmpty()){
			jlist_bscSrchTrms.setSelectedIndex(0);
			loadSearchTermModifierBoxes();
		}

		txtFld_addTerm.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == 10){
					addSrchTermToListandFile();
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}

		});

		btn_rmvSrchTrm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				removeBasicSearchTerm();
			}
		});

		btn_runSkimmer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				runSkimmer();
			}
		});

		btn_viewPage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				openWebPage();
			}
		});

		addKeyListenerToModifierTextFieldss(txtFld_grtrThan);
		addKeyListenerToModifierTextFieldss(txtFld_lessThan);
		addKeyListenerToModifierTextFieldss(txtFld_excldWrds);
		addKeyListenerToModifierTextFieldss(txtFld_excCtgrys);
		
		
		jlist_bscSrchTrms.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if(jlist_bscSrchTrms.getSelectedIndex() != -1){
					loadSearchTermModifierBoxes();			
				}
			}
		});

		btn_rmv.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				removeListingForever();
			}
		});

		btn_runCntnsly.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				runContinously();
			}
		});

	}
}
