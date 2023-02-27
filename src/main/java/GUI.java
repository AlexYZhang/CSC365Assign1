import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;


public class GUI implements ActionListener{

    private JButton enterButton= new JButton("Enter");
    //private JPanel panel1= new JPanel();
    private JLabel businessNameLabel= new JLabel("Enter Business Name: ");
    private JTextField businessNameInput= new JTextField(20);
    private JLabel similarBusinessLabel= new JLabel("Similar Businesses: ");
    private JTextArea similarBusiness= new JTextArea("similar businesses will display here");
    //private JLabel similarBusinessBLabel= new JLabel("similar business2 will display here");

    GUI(){
        //create a JFrame container
        JFrame jfrm= new JFrame("Business Similarity Recommender");

        //set layout
        //jfrm.getContentPane().setLayout(new MigLayout);
        jfrm.getContentPane().setLayout(new MigLayout());

        //set jfrm size
        jfrm.setSize(350,200);

        //set size of input box
        businessNameInput.setPreferredSize(new Dimension(80,20));
        //businessNameInput.setColumns();

        similarBusiness.setLineWrap(true);//text will wrap after columns set
        similarBusiness.setColumns(20);//sets the columns


        //close program when user closes
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add actionListener to enterButton
        enterButton.addActionListener(this);

        //add things to content pane
        jfrm.getContentPane().add(businessNameLabel, "wrap");
        jfrm.getContentPane().add(businessNameInput);
        jfrm.getContentPane().add(enterButton, "wrap");
        jfrm.getContentPane().add(similarBusinessLabel, "wrap");
        jfrm.getContentPane().add(similarBusiness, "wrap");

        //make frame visible
        jfrm.setVisible(true);


        }

        //actionListener for textfield
        public void actionPerformed(ActionEvent ae){//ae generated when user presses Enter button
            String businessName= businessNameInput.getText();//gets inputted businessName
            System.out.println("business Name entered was= "+businessName);

            Main runMain= new Main();
            try {
                runMain.main();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

            similarBusiness.setText(runMain.getOutputForGUI(businessName));

        }


        public static void main(String args[]){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GUI();
                }
            });
        }


}
