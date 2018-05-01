import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Application extends JFrame implements ActionListener, TextListener{

	private TextArea myTextArea;
	private JButton myButton;
	private TextArea myResult;
	private JButton myDefinition;
	private JButton myRefresh;
	private Color defaultColor;
	private static final long serialVersionUID = 1L;
	
	WordLookup myLookupObject;
	
	public Application(){
		myLookupObject = new WordLookup();
		this.setLayout(null);
		this.setSize(520,800);
		this.setResizable(false);
		
		myButton = new JButton("Search");
		myButton.setBounds(100, 10, 100, 25);
		myButton.addActionListener(this);
		this.add(myButton);
		
		myTextArea = new TextArea();
		myTextArea.setBounds(150,50,225,100);
		myTextArea.addTextListener(this);
		this.add(myTextArea);
		this.setResizable(false);
		JLabel nameLabel = new JLabel("Word");
		nameLabel.setBounds(90,50,100,25);
		this.add(nameLabel);
		
		myResult = new TextArea();
		myResult.setBounds(10,170,500,620);
		myResult.setEditable(false);
		myResult.setFocusable(false);
		this.add(myResult);
		this.setResizable(false);
		
		myDefinition = new JButton("Define");
		myDefinition.setBounds(320,10,100,25);
		myDefinition.addActionListener(this);
		this.add(myDefinition);
		
		myRefresh = new JButton ("Refresh");
		myRefresh.setBounds(400, 50, 100, 25);
		myRefresh.addActionListener(this);
		this.add(myRefresh);
		
		defaultColor = getContentPane().getBackground();
	}
	
	public static void main(String[] args) {
		Application ap = new Application();
		ap.setVisible(true);
		ap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String word = myTextArea.getText().trim();
		if (e.getSource() == myButton){
        	String typedWord = word;
			processString(typedWord);
           	getContentPane().setBackground(Color.blue);
   	    } else if (e.getSource() == myDefinition){
			myResult.setText(myLookupObject.getDefinition(word));
   	    	getContentPane().setBackground(Color.red);
   	    } else if (e.getSource() == myRefresh){
   	    	getContentPane().setBackground(defaultColor);
   	    	myTextArea.setText("");
   	    	myResult.setText("");
   	    }
        repaint();
		
	}
	
	public void processString(String typedWord){
		String output = "";
		if(!myLookupObject.isSpelledCorrectly(typedWord)){
			output += "Suggestions\n";
			String[] suggestions = myLookupObject.getSimilarSpellings(typedWord);
			if(suggestions!=null)for(String s : suggestions)output += s + '\n';
			else output += "no suggestions\n";
			output += '\n';
		}else{
			output += "Synonyms\n";
			String[] words = myLookupObject.getCommonSynonyms(typedWord);
			if(words!=null)for(String s : words)output += s + '\n';
			else output += "no synonyms\n";
			output += '\n';
		}
		// use the word lookup to get a list of similar words
		// set the text of "myResult" to whatever you got back from the lookup
		myResult.setText(output);
	}

	@Override
	public void textValueChanged(TextEvent e) {
		String typedWord = myTextArea.getText();
		if(typedWord==null || typedWord.length()==0)return;
		if(typedWord.charAt(typedWord.length()-1)=='\n')processString(typedWord.trim());
	}

}
